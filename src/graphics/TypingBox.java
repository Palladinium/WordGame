package graphics;

// Written by Patrick Chieppe

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class TypingBox extends GraphicElement {

	// CONSTANTS
	private static final double BOXARC = 20.0;
	private static final Color BOXCOLOR = Color.rgb(173, 216, 230);
	private static final String FONT = "Consolas";
	private static final Color TEXTCOLOR = Color.BLACK;
	private static final Color SCORECOLOR = Color.RED;
	private static final double FONTSIZE = 100.0;
	private static final double HPADDING = 20.0;
	private static final double VPADDING = 20.0;
	private static final double FLOATDISTANCE = 100.0;
	private static final double FADETIME = 1.0;
	// Magic constant - ratio between point size and pixel height
	private static final double POINTTOPIXELRATIO = 0.637;

	// FIELDS
	static double BOXWIDTH; 

	private Rectangle box;
	private Text text;
	boolean visible = true;
	private ArrayList<Text> scoreText = new ArrayList<Text>();

	static {
		String testString = new String();
		for (int i = 0; i != dictionary.Dictionary.longestValidWord; ++i)
			testString = testString.concat("A");

		double fieldWidth = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font(FONT, FONTSIZE)).computeStringWidth(testString);
		BOXWIDTH = fieldWidth + HPADDING * 2;
	}

	public TypingBox(double xPos, double yPos, Group parent) {
		super(xPos, yPos, parent);

		box = new Rectangle();
		box.setX(xPos);
		box.setY(yPos);
		box.setHeight(FONTSIZE * POINTTOPIXELRATIO + VPADDING * 2);
		box.setWidth(BOXWIDTH);
		box.setArcHeight(BOXARC);
		box.setArcWidth(BOXARC);
		box.setFill(BOXCOLOR);
		parent.getChildren().add(box);

		text = new Text();
		text.setX(xPos + HPADDING);
		text.setY(yPos + FONTSIZE * POINTTOPIXELRATIO + VPADDING);
		text.setFont(new Font(FONT, FONTSIZE));
		text.setTextAlignment(TextAlignment.LEFT);
		text.setFill(TEXTCOLOR);
		parent.getChildren().add(text);
	}

	public void animate(double elapsed) {
		int i = 0;
		while (i != scoreText.size()) {
			scoreText.get(i).setOpacity(scoreText.get(i).getOpacity() - 1.0 / FADETIME * elapsed);
			scoreText.get(i).setY(scoreText.get(i).getY() - FLOATDISTANCE / FADETIME * elapsed);
			if (scoreText.get(i).getOpacity() == 0) {
				parentRef.get().getChildren().remove(scoreText.get(i));
				scoreText.remove(i);
			} else
				++i;
		}
	}

	public void score (int score) {
		scoreText.add(new Text());
		int i = scoreText.size() - 1;
		scoreText.get(i).setFont(new Font(FONT, FONTSIZE));
		scoreText.get(i).setTextAlignment(TextAlignment.CENTER);
		scoreText.get(i).setFill(SCORECOLOR);
		String newText = new String();
		if (score > 0)
			newText = "+" + score;
		else
			newText = "Try new words!";
		scoreText.get(i).setText(newText);
		double textWidth = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font(FONT, FONTSIZE)).computeStringWidth(newText);
		scoreText.get(i).setX(xPos + (BOXWIDTH - textWidth) * 0.5);
		scoreText.get(i).setY(yPos + FONTSIZE * POINTTOPIXELRATIO + VPADDING);
		scoreText.get(i).setVisible(visible);
		parentRef.get().getChildren().add(scoreText.get(i));
	}

	public void setText(String s) {
		text.setText(s);
		
		double wordWidth = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(new Font(FONT, FONTSIZE)).computeStringWidth(text.getText());
		text.setX(xPos + 0.5 * (BOXWIDTH - wordWidth));
	}

	public void show() {
		visible = true;
		box.setVisible(true);
		text.setVisible(true);
		for (Text x : scoreText)
			x.setVisible(true);
	}

	public void hide() {
		visible = false;
		box.setVisible(false);
		text.setVisible(false);
		for (Text x : scoreText)
			x.setVisible(false);
	}
}
