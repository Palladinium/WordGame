package graphics;

// Written by Patrick Chieppe

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class PerformanceCounter extends GraphicElement {

	private static final Color TEXTCOLOR = Color.BLACK;
	private static final double FONTSIZE = 10.0;
	private static final String FONT = "Consolas";
	// Magic constant - ratio between point size and pixel height
	private static final double POINTTOPIXELRATIO = 0.637;

	private Text text = new Text();

	public PerformanceCounter(double xPos, double yPos, Group parent) {
		super(xPos, yPos, parent);

		text.setX(xPos);
		text.setY(yPos + FONTSIZE * POINTTOPIXELRATIO);
		text.setFont(new Font(FONT, FONTSIZE));
		text.setTextAlignment(TextAlignment.LEFT);
		text.setFill(TEXTCOLOR);
		text.setVisible(false);
		parent.getChildren().add(text);
	}

	public void setText(int GFPS, int frameSkip) {
		text.setText(""
				+ "GFPS: " + GFPS + "\nFrame Skip: " + frameSkip);
	}

	public void toggle() {
		if (text.isVisible())
			hide();
		else
			show();
	}

	public void show() {
		text.setVisible(true);
	}

	public void hide() {
		text.setVisible(false);
	}
}
