package main;

// Written and edited constantly by the whole group as features were developed

import java.util.ArrayList;

import main.SoundControl.GameMusicTypes;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import graphics.Menu;
import graphics.Game;
import graphics.PerformanceCounter;
import graphics.EndLevelText;
import dictionary.Dictionary;
import dictionary.RLG;


public class WordGame extends Application {

    // CONSTANTS

    // Stage size
    public static final double STAGE_WIDTH = 768;
    public static final double STAGE_HEIGHT = 1024;

    // Time between ticks
    private static final double TIME_QUANTUM = 1.0/60.0;

    // Number of levels
    private static final int MAX_LEVELS = 5;
    // Bonus points at the end of level = BONUS_MULTIPLIER * empty capacity in the bucket
    private static final int BONUS_MULTIPLIER = 10;

    // Amount of letters issued in order to win a level
    private static final int INITIAL_VICTORY_AMOUNT = 48;
    // Change in VICTORY_AMOUNT per levelup
    private static final int VICTORY_AMOUNT_LEVEL_MULTIPLIER = 2;

    // Amount of letters in the bucket in order to lose
    private static final int INITIAL_BUCKET_CAPACITY = 36;
    // Change in INITIAL_BUCKET_CAPACITY per levelup
    private static final int BUCKET_LEVEL_MULTIPLIER = -3;

    // Ticks between new letters
    private static final int INITIAL_LETTER_INTERVAL = 120;
    // Change in INITIAL_LETTER_INTERVAL per levelup
    private static final int INTERVAL_LEVEL_MULTIPLIER = -10;


    // GRAPHIC ELEMENTS
    private static Group root = new Group();

    private static PerformanceCounter performanceCounter = new PerformanceCounter(5, 5, root);

    private static Menu menu = new Menu(root);

    private static Game game = new Game(root);

    private static Image winImage = new Image("/winner.jpg");
    private static ImageView winView = new ImageView(winImage);

    private static Image loseImage = new Image("/lose.jpg");
    private static ImageView loseView = new ImageView(loseImage);

    private static EndLevelText endLevelText = new EndLevelText(root);

    // ANIMATION VARIABLES
    private static long lastFrame = 0;
    private static long frameCount = 0;
    private static float timeToProcess = 0;
    private static int tickCount = 0;
    private static int ticksToProcess = 0;

    // GAME VARIABLES

    public enum State {
        MENU, OPTIONMENU, GAME, LEVELTRANSITION, WINNINGSCREEN, LOSINGSCREEN
    }
    public static State gameState = State.MENU;

    private static ArrayList<Character> availableLetters = new ArrayList<Character>();
    private static ArrayList<Character> inputWord = new ArrayList<Character>();

    private static int score = 0;
    private static int lettersIssued = 0;

    public static int levelSetting = 1;
    //public static boolean hardcoreMode = false; // Still does nothing
    private static int currentLevel = 1;
    private static int currentInterval = INITIAL_LETTER_INTERVAL;
    private static int currentCapacity = INITIAL_BUCKET_CAPACITY;
    private static int currentVictoryAmount = INITIAL_VICTORY_AMOUNT;

    private static SoundControl sound;

    public void start(Stage primaryStage) throws Exception {

        winView.setVisible(false);
        root.getChildren().add(0, winView);
        loseView.setVisible(false);
        root.getChildren().add(1, loseView);

        sound = new SoundControl();

        game.hide();
        menu.showMenu();

        sound.setNextSong(GameMusicTypes.MENU_MUSIC);

        primaryStage.setTitle("Word Game");
        Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
        primaryStage.setScene(scene);

        updateText();


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {

                KeyCode key = keyEvent.getCode();

                if (key == KeyCode.F10) {
                    performanceCounter.toggle();
                }

                switch (gameState) {

                    case MENU:
                        if (key == KeyCode.ESCAPE) { 
                            System.exit(0);
                        }
                        break;

                    case OPTIONMENU:
                        break;

                    case GAME:

                        switch (key) {
                            /*******
                             * CHEAT
                             *
                             case F9:
                             inputWord = Dictionary.longestWord(availableLetters);
                             updateText();
                             break;
                             */
                            case ESCAPE:
                                clearGameAndScore();
                                showMenu();
                                break;
                            case BACK_SPACE:
                                if (inputWord.size() != 0) {
                                    game.bucket.deselect(java.lang.Character.toUpperCase(inputWord.get(inputWord.size() -1)));
                                    inputWord.remove(inputWord.size() - 1);
                                    updateText();
                                }
                                break;
                            case ENTER:
                                if (Dictionary.isWord(inputWord)) {
                                    int wordScore = Dictionary.score(inputWord);
                                    score += wordScore;
                                    game.typingBox.score(wordScore);
                                    for (int i = 0; i < inputWord.size(); i++) {
                                        game.bucket.remove(java.lang.Character.toUpperCase(inputWord.get(i)));
                                        availableLetters.remove(inputWord.get(i));
                                    }
                                    game.bucket.deselectAll();
                                    inputWord.clear();
                                    updateText();
                                }
                                break;
                            case CONTROL:
                                game.bucket.shuffle();
                                break;
                            case SPACE:
                                game.bucket.deselectAll();
                                inputWord.clear();
                                updateText();
                                break;
                            default:
                                break;
                        }
                        break;

                    case LEVELTRANSITION:
                        if (endLevelText.isInteractible())
                            startNextLevel();
                        break;

                    case WINNINGSCREEN:
                    case LOSINGSCREEN:
                        if (endLevelText.isInteractible()) {
                            showMenu();
                        }
                        break;
                }
            }
        });




        scene.setOnKeyTyped(new EventHandler<KeyEvent>() {                     
            @Override
            public void handle (KeyEvent keyEvent) {

                switch (gameState) {
                    case MENU:
                    case OPTIONMENU:
                        break;
                    case GAME:
                        char key = keyEvent.getCharacter().charAt(0);
                        if (availableLetters.contains(key)) {

                            int i = 0;
                            int j = 0;

                            for (char c: availableLetters) {
                                if(c == key) {
                                    ++i;
                                }
                            }
                            for (char c: inputWord) {
                                if(c == key) {
                                    ++j;
                                }
                            }
                            if (j < i && inputWord.size() < Dictionary.longestValidWord) {
                                inputWord.add(key);
                                game.bucket.select(java.lang.Character.toUpperCase(key));
                                updateText();
                            }

                        }
                        break;
                    case LEVELTRANSITION:
                        break;
                    case WINNINGSCREEN:
                        break;
                    case LOSINGSCREEN:
                        break;
                }
            }
        });


        new AnimationTimer(){
            @Override
            public void handle(long now) {
                double elapsedSec = (now - lastFrame) / 1000000000.0;
                if (frameCount > 0) {

                    if (tickCount >= currentInterval) {
                        tickCount -= currentInterval;

                        /*******************************************************************************/
                        /* Code in this block is executed once every (INTERVAL * TIME_QUANTUM) seconds */
                        /*******************************************************************************/

                        if (gameState == State.GAME) {

                            char newLetter = RLG.next();
                            availableLetters.add(newLetter);
                            game.bucket.add(java.lang.Character.toUpperCase(newLetter));
                            ++lettersIssued;

                            updateText();

                            if (availableLetters.size() > currentCapacity) {
                                lose();
                            } else if (lettersIssued >= currentVictoryAmount) {
                                goToNextLevel();
                            }
                        }

                        /*******************************************************************************/
                        /* Code in this block is executed once every (INTERVAL * TIME_QUANTUM) seconds */
                        /*******************************************************************************/
                    }

                    timeToProcess += elapsedSec;

                    while (timeToProcess > TIME_QUANTUM) {
                        ++ticksToProcess;
                        timeToProcess -= TIME_QUANTUM;
                    }

                    if (ticksToProcess > 0) {
                         /******************************************************************/
                         /* Code in this block is executed once every TIME_QUANTUM seconds */
                         /******************************************************************/

                        switch (gameState) {

                            case MENU:
                            case OPTIONMENU:
                                break;

                            case GAME:
                                game.bucket.animate(ticksToProcess * TIME_QUANTUM);
                                game.typingBox.animate(ticksToProcess * TIME_QUANTUM);
                                break;

                            case LOSINGSCREEN:
                            case WINNINGSCREEN:
                                endLevelText.animate(ticksToProcess * TIME_QUANTUM);
                                break;

                            case LEVELTRANSITION:
                                endLevelText.animate(ticksToProcess * TIME_QUANTUM);
                                game.bucket.animate(ticksToProcess * TIME_QUANTUM);
                                game.typingBox.animate(ticksToProcess * TIME_QUANTUM);
                                break;
                        }

                        sound.updateMusic(elapsedSec);

                        performanceCounter.setText((int) Math.floor(1.0 / elapsedSec), ticksToProcess -1);

                        /******************************************************************/
                        /* Code in this block is executed once every TIME_QUANTUM seconds */
                        /******************************************************************/
                        tickCount += ticksToProcess;
                        ticksToProcess = 0;
                    }
                }
                ++frameCount;
                lastFrame = now;
            }
        }.start();

        primaryStage.show();
    }

    public static void updateText() {

        String typingBoxString = new String();
        for (Character x : inputWord)
            typingBoxString = typingBoxString.concat(x.toString());

        game.typingBox.setText(typingBoxString.toUpperCase());

        game.levelBox.setText(java.lang.Integer.toString(currentLevel));

        game.nextTwoBox.setNextTwo(RLG.getLookahead());

        game.statusPanel.setText(score,
                Dictionary.choice(availableLetters),
                availableLetters.size(),
                currentCapacity,
                lettersIssued,
                currentVictoryAmount,
                Dictionary.longest(availableLetters)
                );
    }

    private void lose() {
        game.hide();

        endLevelText.setLose(score);
        endLevelText.show();

        endLevelText.setX(330);
        endLevelText.setY(710);

        loseView.setVisible(true);

        clearGame();
        clearGameAndScore();

        sound.setNextSong(GameMusicTypes.LOSING_MUSIC);

        gameState = State.LOSINGSCREEN;
    }

    private void goToNextLevel() {
        int bonus = (INITIAL_BUCKET_CAPACITY - availableLetters.size()) * BONUS_MULTIPLIER;
        score += bonus;

        clearGame();

        endLevelText.setX(330);

        if (currentLevel < MAX_LEVELS) {

            endLevelText.setY(320);

            endLevelText.setLevelup(currentLevel, bonus);

            setLevel(currentLevel + 1);

            gameState = State.LEVELTRANSITION;
        } else {
            game.hide();

            endLevelText.setY(710);
            endLevelText.setWin(score);

            clearGameAndScore();

            winView.setVisible(true);

            sound.setNextSong(GameMusicTypes.WINNING_MUSIC);

            gameState = State.WINNINGSCREEN;
        }

        endLevelText.show();
    }

    private static void startNextLevel() {
        endLevelText.hide();

        lettersIssued = 0;

        gameState = State.GAME;
    }

    private static void showMenu() {
        switch (gameState) {
            case MENU:
            case OPTIONMENU:
                break;
            case GAME:
                game.hide();
                break;
            case LEVELTRANSITION:
                endLevelText.hide();
            case LOSINGSCREEN:
                loseView.setVisible(false);
                endLevelText.hide();
                break;
            case WINNINGSCREEN:
                winView.setVisible(false);
                endLevelText.hide();
                break;
        }
        menu.showMenu();
        sound.setNextSong(GameMusicTypes.MENU_MUSIC);
        gameState = State.MENU;
    }

    public static void startGame() {
        setLevel(levelSetting);
        menu.hide();
        game.show();
        updateText();
        sound.setNextSong(GameMusicTypes.LEVEL_MUSIC);
        WordGame.gameState = WordGame.State.GAME;
    }

    private static void setLevel(int level) {
        currentLevel = level;
        currentInterval = INITIAL_LETTER_INTERVAL + ((currentLevel - 1) * INTERVAL_LEVEL_MULTIPLIER);
        currentCapacity = INITIAL_BUCKET_CAPACITY + ((currentLevel - 1) * BUCKET_LEVEL_MULTIPLIER);
        currentVictoryAmount = INITIAL_VICTORY_AMOUNT + ((currentLevel - 1) * VICTORY_AMOUNT_LEVEL_MULTIPLIER);
    }

    private static void clearGame() {
        availableLetters.clear();
        inputWord.clear();
        game.bucket.removeAll();
        updateText();
    }

    private void clearGameAndScore() {
        availableLetters.clear();
        inputWord.clear();
        game.bucket.removeAllNow();
        score = 0;
        lettersIssued = 0;
        Dictionary.clearUsedWords();
        RLG.shuffleBag();
        updateText();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
