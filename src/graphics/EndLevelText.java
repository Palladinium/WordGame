package graphics;

// Written by Patrick Chieppe and Edward Desmond-Jones

import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.text.*;


public class EndLevelText extends GraphicElement {

	private static final String FONT = "Consolas";
	private static final double FONTSIZE = 10.0;
	private static final Color LEVELUPCOLOR = Color.rgb(64, 0, 0);
	private static final Color WINCOLOR = Color.GOLD;
	private static final Color LOSECOLOR = Color.WHITE;
	private static final double FINSCALE = 5.0;
	private static final double FINSCALETIME = 0.75;
	private static final double INTERACTTIME = 0.5;
	private static final double BLINKTIME = 0.75;

	double lifetime = 0.0;
	boolean interactible = false;
	boolean blink = false;
	Text text = new Text();
	Text blinkingText = new Text("Press any key to continue");

	public EndLevelText(Group parent) {
		super (0, 0, parent);

		text.setFont(new Font(FONT, FONTSIZE));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setVisible(false);

		blinkingText.setFont(new Font(FONT, FONTSIZE));
		blinkingText.setTextAlignment(TextAlignment.CENTER);
		blinkingText.setVisible(false);

		parent.getChildren().add(text);
		parent.getChildren().add(blinkingText);
	}

	public void setLevelup(int level, int bonus) {
		reset();

		text.setText("Level " + level + " complete!\nYour bonus is " + bonus + "!\n");
		text.setFill(LEVELUPCOLOR);
		text.setScaleX(0.0);
		text.setScaleY(0.0);

		blinkingText.setFill(LEVELUPCOLOR);
		blinkingText.setScaleX(0.0);
		blinkingText.setScaleY(0.0);
	}

	public void setWin(int score) {
		reset();

		text.setText("Your final score is " + score + "!\n");
		text.setFill(WINCOLOR);
		text.setScaleX(FINSCALE);
		text.setScaleY(FINSCALE);

		blinkingText.setFill(WINCOLOR);
		blinkingText.setScaleX(FINSCALE);
		blinkingText.setScaleY(FINSCALE);
	}

	public void setLose(int score) {
		reset();

		text.setText("You lose!\nYour final score is " + score + "!\n");
		text.setFill(LOSECOLOR);
		text.setScaleX(FINSCALE);
		text.setScaleY(FINSCALE);

		blinkingText.setFill(LOSECOLOR);
		blinkingText.setScaleX(FINSCALE);
		blinkingText.setScaleY(FINSCALE);
	}

	public void animate(double elapsed) {
		lifetime += elapsed;

		if (!interactible && lifetime > INTERACTTIME) {
			interactible = true;
			lifetime = 0;
		}

		if (interactible && lifetime > BLINKTIME) {
			lifetime -= BLINKTIME;
			blinkingText.setVisible(blink);
			blink = !blink;
		}

		double newScale = text.getScaleX() + (FINSCALE / FINSCALETIME * elapsed);

		if (newScale > FINSCALE)
			newScale = FINSCALE;

		text.setScaleX(newScale);
		text.setScaleY(newScale);

		blinkingText.setScaleX(newScale);
		blinkingText.setScaleY(newScale);

		updatePos();
	}

	@Override
	public void setX(double xPos) {
		super.setX(xPos);
	}

	@Override
	public void setY(double yPos) {
		super.setY(yPos);
	}

	private void reset() {
		lifetime = 0;
		interactible = false;
		blink = false;
		blinkingText.setVisible(false);
	}

	private void updatePos() {
		text.setX(xPos);
		text.setY(yPos);

		blinkingText.setX(xPos);
		blinkingText.setY(yPos + 35 * blinkingText.getScaleX());
	}

	public boolean isInteractible() {
		return interactible;
	}

	public void show() {
		text.setVisible(true);
	}

	public void hide() {
		text.setVisible(false);
		blinkingText.setVisible(false);
	}
}
