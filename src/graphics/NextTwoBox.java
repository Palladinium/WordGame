package graphics;

// Written by Patrick Chieppe

import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.text.*;
import javafx.scene.shape.Rectangle;

public class NextTwoBox extends GraphicElement {

	private static final double VPADDING = 10;
	private static final double INTERLINE = 4;
	private static final double SPACING = 10;

	private static final String FONT = "Consolas";
	private static final double FONTSIZE = 40.0;
	private static final Color TEXTCOLOR = Color.BLACK;
	// Magic constant - ratio between point size and pixel height
	private static final double POINTTOPIXELRATIO = 0.637;

	static final double BOXHEIGHT = 2 * VPADDING + 2 * (FONTSIZE * POINTTOPIXELRATIO) + INTERLINE + 2 * SPACING + 2 * LetterTile.DEFAULTSIZE;
	static final double BOXWIDTH = 100.0;
	private static final double BOXARC = 20.0;
	private static final Color BOXCOLOR = Color.rgb(173, 216, 230);

	Rectangle box = new Rectangle();
	Text label = new Text();
	Text label2 = new Text();
	LetterTile[] nextTwo = new LetterTile[2];

	public NextTwoBox(double xPos, double yPos, Group parent) {
		super(xPos, yPos, parent);

		box.setX(xPos);
		box.setY(yPos);
		box.setHeight(BOXHEIGHT);
		box.setWidth(BOXWIDTH);
		box.setArcHeight(BOXARC);
		box.setArcWidth(BOXARC);
		box.setFill(BOXCOLOR);
		parent.getChildren().add(box);

		double offset = 0; 

		double fieldWidth = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font(FONT, FONTSIZE)).computeStringWidth("NEXT");

		offset += FONTSIZE * POINTTOPIXELRATIO + VPADDING;
		label.setX(xPos + 0.5 * (BOXWIDTH - fieldWidth));
		label.setY(yPos + offset);
		label.setFill(TEXTCOLOR);
		label.setFont(new Font(FONT, FONTSIZE));
		label.setTextAlignment(TextAlignment.LEFT);
		label.setText("NEXT");
		parent.getChildren().add(label);

		fieldWidth = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font(FONT, FONTSIZE)).computeStringWidth("TWO");

		offset += FONTSIZE * POINTTOPIXELRATIO + INTERLINE;
		label2.setX(xPos + 0.5 * (BOXWIDTH - fieldWidth));
		label2.setY(yPos + offset);
		label2.setFill(TEXTCOLOR);
		label2.setFont(new Font(FONT, FONTSIZE));
		label2.setTextAlignment(TextAlignment.LEFT);
		label2.setText("TWO");
		parent.getChildren().add(label2);

		offset += SPACING;
		nextTwo[0] = new LetterTile(xPos + 0.5 * (BOXWIDTH - LetterTile.DEFAULTSIZE), yPos + offset, parent, 'A');
		offset += SPACING + LetterTile.DEFAULTSIZE;
		nextTwo[1] = new LetterTile(xPos + 0.5 * (BOXWIDTH - LetterTile.DEFAULTSIZE), yPos + offset, parent, 'A');
	}


	public void setNextTwo(char[] lookahead) {
		nextTwo[0].setLetter(java.lang.Character.toUpperCase(lookahead[0]));
		nextTwo[1].setLetter(java.lang.Character.toUpperCase(lookahead[1]));

	}

	@Override
	public void hide() {
		box.setVisible(false);
		label.setVisible(false);
		label2.setVisible(false);
		nextTwo[0].hide();
		nextTwo[1].hide();
	}

	@Override
	public void show() {
		box.setVisible(true);
		label.setVisible(true);
		label2.setVisible(true);
		nextTwo[0].show();
		nextTwo[1].show();
	}

}
