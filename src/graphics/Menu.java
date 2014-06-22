package graphics;

// Written by Harvey Delaney and Patrick Chieppe

import main.WordGame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
//import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class Menu extends GraphicElement {

	private static final Color TITLECOLOR = Color.RED;

	private static final int FONTSIZE = 86;

	private static final String FONT = "Consolas";

	Text titleText = new Text("WordGame:");
	Text subTitleText = new Text("The Assignment:");
	Text subSubTitleText = new Text("The Game");
	
	Button accept = new Button("Accept");
	Button cancel = new Button("Cancel");
	Button exit = new Button("EXIT");
	Button options = new Button("Options");
	public Button play = new Button("Play!");

	//final Text hardText = new Text(0, 30, "Hardcore Mode");
	final Text boxText = new Text(0, 30, "Level Selection");

	//final CheckBox cb1 = new CheckBox();

	ObservableList<Integer> options2 = FXCollections.observableArrayList(1,2,3,4,5);
	final ComboBox<Integer> comboBox = new ComboBox<Integer>(options2);

	public Menu(Group parent) {
		super(0, 0, parent);
		
		double fieldWidth;
		
		fieldWidth = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font(FONT, FONTSIZE)).computeStringWidth(titleText.getText());
		titleText.setY(100);
		titleText.setX(0.5 * (WordGame.STAGE_WIDTH - fieldWidth));
		titleText.setFont(new Font(FONT, FONTSIZE));
		titleText.setTextAlignment(TextAlignment.LEFT);
		titleText.setFill(TITLECOLOR);
		parent.getChildren().add(titleText);

		
		fieldWidth = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font(FONT, FONTSIZE)).computeStringWidth(subTitleText.getText());
		subTitleText.setY(220);
		subTitleText.setX(0.5 * (WordGame.STAGE_WIDTH - fieldWidth));
		subTitleText.setFont(new Font(FONT, FONTSIZE));
		subTitleText.setTextAlignment(TextAlignment.LEFT);
		subTitleText.setFill(TITLECOLOR);
		parent.getChildren().add(subTitleText);
		
		fieldWidth = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font(FONT, FONTSIZE)).computeStringWidth(subSubTitleText.getText());
		subSubTitleText.setY(340);
		subSubTitleText.setX(0.5 * (WordGame.STAGE_WIDTH - fieldWidth));
		subSubTitleText.setFont(new Font(FONT, FONTSIZE));
		subSubTitleText.setTextAlignment(TextAlignment.LEFT);
		subSubTitleText.setFill(TITLECOLOR);
		parent.getChildren().add(subSubTitleText);
		
		
		// DROPDOWN MENU
		comboBox.setValue(1);
		comboBox.setVisible(false);
		comboBox.setTranslateX(360);
		comboBox.setTranslateY(375);

		parent.getChildren().add(comboBox);

		//Select box
		/*
		cb1.setSelected(false);
		cb1.setVisible(false);

		cb1.setTranslateX(380);
		cb1.setTranslateY(500);
		
		parent.getChildren().add(cb1);
		*/
		
		// LEVELSELECT TEXT
		boxText.setFont(Font.font("", FontWeight.BOLD, 40));
		boxText.setFill(Color.GREEN);
		boxText.setX(225);
		boxText.setY(340);
		boxText.setVisible(false);

		parent.getChildren().add(boxText);

		// HARDCORE MODE TEXT
		/*
		hardText.setFont(Font.font("", FontWeight.BOLD, 40));
		hardText.setFill(Color.RED);
		hardText.setX(220);
		hardText.setY(475);
		hardText.setVisible(false);

		parent.getChildren().add(hardText);
		*/

		// PLAY BUTTON
		setupButt(play, 257, 400, "-fx-font: 60 consolas; -fx-base: #0066FF;",
				true);

		// OPTIONS BUTTON
		setupButt(options, 215, 575, "-fx-font: 60 consolas; -fx-base: #FFFF33;",
				true);

		// EXIT BUTTON
		setupButt(exit, 265, 750, "-fx-font: 60 consolas; -fx-base: #FF1919;",
				true);

		// CANCEL BUTTON
		setupButt(cancel, 450, 750, "-fx-font: 22 consolas; -fx-base: #FF1919;",
				false);

		// ACCEPT BUTTON
		setupButt(accept, 250, 750, "-fx-font: 22 consolas; -fx-base: #b6e7c9;",
				false);

		// ACTION LISTENERS
		// PLAY BUTTON
		play.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				main.WordGame.startGame();
			}
		});

		// OPTIONS BUTTON
		options.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				showOptions();
			}
		});

		// EXIT BUTTON
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.exit(0);
			}
		});

		// ACCEPT BUTTON
		accept.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				/*
				if (cb1.isSelected()) {
					main.WordGame.hardcoreMode=true;
				}
				*/

				main.WordGame.levelSetting = (int) comboBox.getValue();
				showMenu();
			}
		});

		// CANCEL BUTTON
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				showMenu();
				/*
				if(main.WordGame.hardcoreMode)
					cb1.setSelected(true);
				else
					cb1.setSelected(false);
				*/
				comboBox.setValue(main.WordGame.levelSetting);
			}
		});

	}

	public void setupButt(Button button, double x, double y, String style,
			boolean visible) {
		button.setTranslateX(x);
		button.setTranslateY(y);
		button.setStyle(style);
		button.setVisible(visible);
		parentRef.get().getChildren().add(button);
	}

	public void showMenu() {
		show();
	}

	public void show() {
		main.WordGame.gameState = main.WordGame.State.MENU;
		
		titleText.setVisible(true);
		subTitleText.setVisible(true);
		subSubTitleText.setVisible(true);
		
		//cb1.setVisible(false);

		//hardText.setVisible(false);
		boxText.setVisible(false);

		cancel.setVisible(false);
		accept.setVisible(false);
		comboBox.setVisible(false);

		options.setVisible(true);
		play.setVisible(true);
		exit.setVisible(true);
	}

	public void showOptions() {
		main.WordGame.gameState = main.WordGame.State.OPTIONMENU;
		titleText.setVisible(false);
		subTitleText.setVisible(false);
		subSubTitleText.setVisible(false);
		
		//cb1.setVisible(true);

		play.setVisible(false);
		exit.setVisible(false);
		options.setVisible(false);
		//hardText.setVisible(true);
		cancel.setVisible(true);
		accept.setVisible(true);
		comboBox.setVisible(true);
		boxText.setVisible(true);
	}

	public void hide() {
		titleText.setVisible(false);
		subTitleText.setVisible(false);
		subSubTitleText.setVisible(false);
		
		//cb1.setVisible(false);

		play.setVisible(false);
		exit.setVisible(false);
		options.setVisible(false);

		//hardText.setVisible(false);
		cancel.setVisible(false);
		accept.setVisible(false);
		comboBox.setVisible(false);
		boxText.setVisible(false);
	}
}