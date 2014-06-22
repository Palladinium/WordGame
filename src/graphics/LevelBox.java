package graphics;

// Written by Patrick Chieppe

import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.text.*;
import javafx.scene.shape.Rectangle;

public class LevelBox extends GraphicElement {

	static final double BOXWIDTH = 100;
	static final double BOXHEIGHT = 100;
	static final Color BOXCOLOR = Color.GREENYELLOW;
	static final Color TEXTCOLOR = Color.BLACK;

	Rectangle box;
	Text label;
	Text text;

	LevelBox(double xPos, double yPos, Group parent) {
		super(xPos, yPos, parent);
	}

	public void show() {
		box.setVisible(true);
		label.setVisible(true);
		text.setVisible(true);
	}

	public void hide() {
		box.setVisible(false);
		label.setVisible(false);
		text.setVisible(false);
	}
}
