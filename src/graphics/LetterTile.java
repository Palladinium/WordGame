package graphics;

// Written by Patrick Chieppe

import java.lang.Math;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class LetterTile extends GraphicElement {

	// CONSTANTS

	public static final double DEFAULTSIZE = 50.0;

	// Box
	private static final double BOXARC = 20.0;
	private static final Color RECTCOLOR = Color.rgb(255, 253, 208);
	private static final double SELECTSTROKE = 6.0;
	private static final Color SELECTCOLOR = Color.rgb(255, 191, 0, 0.7);
	// Text
	private static final String FONT = "Consolas";
	private static final Color TEXTCOLOR = Color.BLACK;
	// Magic constant - ratio between point size and pixel height
	private static final double POINTTOPIXELRATIO = 0.637;
	// Animation
	private static final double ANIMMID = 1.0;
	private static final double ANIMMIDSIZE = 75.0;
	private static final double ANIMFIN = 1.5; 
	private static final double ANIMFINSIZE = DEFAULTSIZE;
	private static final double ANIMREMOVEFIN = 0.3;
	// Physics
	private static final double SPEEDCAP = 100.0;
	private static final double ATTRITIONDRAG = 8.0;
	// Status enumeration
	private enum Status {
		SPAWNING, ALIVE, DESPAWNING, REMOVABLE
	}

	// FIELDS
	// Graphic elements
	private Rectangle box;
	private Text letter;
	private double lifetime = 0.0;
	private Status status = Status.SPAWNING;
	private boolean selected = false;
	private double size = DEFAULTSIZE;
	// Physics
	public double xSpeed = 0.0;
	public double ySpeed = 0.0;

	// Methods
	public LetterTile(double xPos, double yPos, Group parent, char l) {
		super(xPos, yPos, parent);

		box = new Rectangle();
		box.setFill(RECTCOLOR);
		box.setArcHeight(BOXARC);
		box.setArcWidth(BOXARC);
		box.setHeight(size);
		box.setWidth(size);
		box.setStrokeType(StrokeType.OUTSIDE);
		box.setStrokeWidth(SELECTSTROKE);
		parent.getChildren().add(box);

		letter = new Text(java.lang.Character.toString(l));
		letter.setFont(new Font(FONT, size * 0.5));
		letter.setTextAlignment(TextAlignment.CENTER);
		letter.setFill(TEXTCOLOR);
		parent.getChildren().add(letter);

		updatePos();
	}

	public void movement(double elapsed) {

		// PHYSICS
		// Speed has a hard cap
		// Speed is in pixels / second
		// Attrition is the amount of speed lost per second
		if (xSpeed < 0) {
			xSpeed = Math.max(xSpeed, -SPEEDCAP);
			xPos += xSpeed * elapsed;
			xSpeed = Math.min(xSpeed + (ATTRITIONDRAG * elapsed), 0);
		} else {
			xSpeed = Math.min(xSpeed, SPEEDCAP);
			xPos += xSpeed * elapsed;
			xSpeed = Math.max(xSpeed - (ATTRITIONDRAG * elapsed), 0);
		}



		if (ySpeed < 0) {
			ySpeed = Math.max(ySpeed, -SPEEDCAP);
			yPos +=  ySpeed * elapsed;
			ySpeed = Math.min(ySpeed + (ATTRITIONDRAG * elapsed), 0);
		} else {
			ySpeed = Math.min(ySpeed, SPEEDCAP);
			yPos +=  ySpeed * elapsed;
			ySpeed = Math.max(ySpeed - (ATTRITIONDRAG * elapsed), 0);
		}
	}
	public void animate(double elapsed) {
		movement(elapsed);

		// Animation

		switch (status) {
		case SPAWNING:
			lifetime += elapsed;

			if (lifetime > ANIMFIN) {
				lifetime = ANIMFIN;
				status = Status.ALIVE;
			}

			if (lifetime > ANIMMID)
				size = (ANIMFINSIZE - ANIMMIDSIZE) / (ANIMFIN - ANIMMID) * (lifetime - ANIMMID) + ANIMMIDSIZE;
			else
				size = ANIMMIDSIZE / ANIMMID * lifetime;

			updatePos();
			updateSize();
			break;
		case ALIVE:
			updatePos();
			break;
		case DESPAWNING:
			size -= (ANIMFINSIZE / ANIMREMOVEFIN) * elapsed;
			if (size < 0) {
				size = 0;
				status = Status.REMOVABLE;
			}

			updatePos();
			updateSize();
			break;
		case REMOVABLE:
			break;
		}
	}

	public void updatePos() {
		// Second longest line in the entire assignment
		double fontWidth = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(letter.getFont()).computeStringWidth(letter.getText());
		// Also line with the most dots

		box.setX(xPos + (ANIMFINSIZE - size) * 0.5);
		box.setY(yPos + (ANIMFINSIZE - size) * 0.5);
		letter.setX(xPos + (ANIMFINSIZE - fontWidth) * 0.5);
		letter.setY(yPos + (ANIMFINSIZE + size * 0.5 * POINTTOPIXELRATIO) * 0.5);
	}

	private void updateSize() {
		box.setWidth(size);
		box.setHeight(size);
		letter.setFont(new Font(FONT, size/2));
	}

	public void select() {
		selected = true;
		box.setStroke(SELECTCOLOR);
	}

	public void deselect() {
		selected = false;
		box.setStroke(null);
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isSolid() {
		return (status == Status.SPAWNING || status == Status.ALIVE);
	}

	public boolean isDespawning() {
		return (status == Status.DESPAWNING);
	}

	public void setRemove() {
		status = Status.DESPAWNING;
	}

	public boolean isRemovable() {
		return (status == Status.REMOVABLE);
	}

	public void setRemoveNow() {
		size = 0;
		updateSize();
		status = Status.REMOVABLE;
	}

	public void purge() {
		parentRef.get().getChildren().remove(letter);
		parentRef.get().getChildren().remove(box);
	}

	public void show() {
		letter.setVisible(true);
		box.setVisible(true);
	}

	public void hide() {
		letter.setVisible(false);
		box.setVisible(false);
	}

	public void setLetter(char l) {
		letter.setText(Character.toString(l));
	}

	public char getLetter() {
		return letter.getText().charAt(0);
	}

	public double getSize() {
		return size;
	}

	public double getXSpeed() {
		return xSpeed;
	}

	public double getYSpeed() {
		return ySpeed;
	}

	public void pushX (double push) {
		xSpeed += push;
	}

	public void pushY (double push) {
		ySpeed += push;
	}

	public void stop() {
		xSpeed = 0;
		ySpeed = 0;
	}
}