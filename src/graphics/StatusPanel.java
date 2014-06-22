package graphics;

// Written by Patrick Chieppe

import javafx.scene.paint.Color;
import javafx.scene.Group;

public class StatusPanel extends GraphicElement {

	static final double SPACING = 11.0; 

	private StatusBox scoreBox;
	private StatusBox choiceBox;
	private StatusBox lettersBox;
	private StatusBox issuedBox;
	private StatusBox longestBox;

	public StatusPanel (double xPos, double yPos, Group parent) {
		super(xPos, yPos, parent);

		scoreBox = new StatusBox(xPos, yPos, parent);
		scoreBox.setColors(Color.rgb(165, 255, 76), Color.BLACK, Color.BLACK);
		scoreBox.setLabel("SCORE");

		choiceBox = new StatusBox(xPos + (StatusBox.BOXWIDTH + SPACING), yPos, parent);
		choiceBox.setColors(Color.rgb(165, 255, 76), Color.BLACK, Color.BLACK);
		choiceBox.setLabel("COMBINATIONS");

		lettersBox = new StatusBox(xPos + 2 * (StatusBox.BOXWIDTH + SPACING), yPos, parent);
		lettersBox.setColors(Color.rgb(165, 255, 76), Color.BLACK, Color.BLACK);
		lettersBox.setLabel("BUCKET");

		issuedBox = new StatusBox(xPos + 3 * (StatusBox.BOXWIDTH + SPACING), yPos, parent);
		issuedBox.setColors(Color.rgb(165, 255, 76), Color.BLACK, Color.BLACK);
		issuedBox.setLabel("PROGRESS");

		longestBox = new StatusBox(xPos + 4 * (StatusBox.BOXWIDTH + SPACING), yPos, parent);
		longestBox.setColors(Color.rgb(165, 255, 76), Color.BLACK, Color.BLACK);
		longestBox.setLabel("LONGEST");
	}

	public void setText(int score, int combinations, int letters, int capacity, int issued, int winamount, int longest) {
		scoreBox.setText(java.lang.Integer.toString(score));
		choiceBox.setText(java.lang.Integer.toString(combinations));
		lettersBox.setText(letters + "/" + capacity);
		issuedBox.setText(issued + "/" + winamount);
		longestBox.setText(java.lang.Integer.toString(longest));
	}

	public void show() {
		scoreBox.show();
		choiceBox.show();
		lettersBox.show();
		issuedBox.show();
		longestBox.show();
	}

	public void hide() {
		scoreBox.hide();
		choiceBox.hide();
		lettersBox.hide();
		issuedBox.hide();
		longestBox.hide();
	}
}
