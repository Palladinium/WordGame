package graphics;

// Written by Patrick Chieppe

import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.text.*;
import javafx.scene.shape.Rectangle;

public class StatusBox extends GraphicElement {

	static final double BOXHEIGHT = 80.0;
	static final double BOXWIDTH = 138.0;
	private static final double BOXARC = 20.0;

	private static final String FONT = "Consolas";
	private static final double FONTSIZE = 40.0;
	// Magic constant - ratio between point size and pixel height
	private static final double POINTTOPIXELRATIO = 0.637;

	private static final double LABELPOS = 0.3;
	private static final double TEXTPOS = 0.7;

	private Rectangle box = new Rectangle();
	private Text label = new Text();
	private Text text = new Text();


	StatusBox(double xPos, double yPos, Group parent) {
		super(xPos, yPos, parent);

		box.setX(xPos);
		box.setY(yPos);
		box.setWidth(BOXWIDTH);
		box.setHeight(BOXHEIGHT);
		box.setArcHeight(BOXARC);
		box.setArcWidth(BOXARC);
		parent.getChildren().add(box);

		label.setX(xPos);
		label.setY(yPos + 0.5 * (FONTSIZE * POINTTOPIXELRATIO) + LABELPOS * BOXHEIGHT);
		label.setFont(new Font(FONT, FONTSIZE));
		label.setTextAlignment(TextAlignment.LEFT);
		parent.getChildren().add(label);

		text.setX(xPos);
		text.setY(yPos + 0.5 * (FONTSIZE * POINTTOPIXELRATIO) + TEXTPOS * BOXHEIGHT);
		text.setFont(new Font(FONT, FONTSIZE));
		text.setTextAlignment(TextAlignment.LEFT);
		parent.getChildren().add(text);
	}

	public void setLabel(String l) {
		double newSize = FONTSIZE;
		double textWidth = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font(FONT, FONTSIZE)).computeStringWidth(l);
		while (textWidth > BOXWIDTH - 5 && newSize >= 1) {
			newSize = newSize - 1;
			textWidth = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font(FONT, newSize)).computeStringWidth(l);
		}

		label.setFont(new Font(FONT, newSize));
		label.setText(l);
		label.setX(xPos + 0.5 * (BOXWIDTH - textWidth));
		label.setY(yPos + 0.5 * (newSize * POINTTOPIXELRATIO) + LABELPOS * BOXHEIGHT);
	}

	public void setColors(Color boxColor, Color labelColor, Color textColor) {
		box.setFill(boxColor);
		label.setFill(labelColor);
		text.setFill(textColor);
	}

	public void setText(String l) {
		double newSize = FONTSIZE;
		double textWidth = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font(FONT, FONTSIZE)).computeStringWidth(l);
		while (textWidth > BOXWIDTH - 5 && newSize > 1) {
			newSize = newSize - 1;
			textWidth = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font(FONT, newSize)).computeStringWidth(l);
		}

		text.setFont(new Font(FONT, newSize));
		text.setText(l);
		text.setX(xPos + 0.5 * (BOXWIDTH - textWidth));
		text.setY(yPos + 0.5 * (newSize * POINTTOPIXELRATIO) + TEXTPOS * BOXHEIGHT);
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
