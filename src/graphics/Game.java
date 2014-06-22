package graphics;

// Written by Patrick Chieppe

import javafx.scene.Group;
import javafx.scene.paint.Color;
import graphics.Bucket;
import graphics.TypingBox;
import graphics.StatusPanel;

public class Game extends GraphicElement {

	public Bucket bucket;
	public TypingBox typingBox;
	public StatusPanel statusPanel;
	public StatusBox levelBox;
	public NextTwoBox nextTwoBox;

	public Game(Group parent) {
		super(0, 0, parent);

		bucket = new Bucket(0.5 * main.WordGame.STAGE_WIDTH - Bucket.BUCKETRADIUS, 75, parent);

		levelBox = new StatusBox(15, 15 + 0.5 * (NextTwoBox.BOXHEIGHT - LevelBox.BOXHEIGHT), parent);
		levelBox.setColors(Color.GREENYELLOW, Color.BLACK, Color.BLACK);
		levelBox.setLabel("LEVEL");

		nextTwoBox = new NextTwoBox(main.WordGame.STAGE_WIDTH - (NextTwoBox.BOXWIDTH + 15), 15, parent);

		statusPanel = new StatusPanel(StatusPanel.SPACING, 725, parent);

		typingBox = new TypingBox(0.5 * (main.WordGame.STAGE_WIDTH - TypingBox.BOXWIDTH), 860, parent);
	}

	public void show() {
		bucket.show();
		typingBox.show();
		statusPanel.show();
		levelBox.show();
		nextTwoBox.show();
	}

	public void hide() {
		bucket.hide();
		typingBox.hide();
		statusPanel.hide();
		levelBox.hide();
		nextTwoBox.hide();
	}
}
