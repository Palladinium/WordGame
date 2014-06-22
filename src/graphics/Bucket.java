package graphics;

// Written by Patrick Chieppe

import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Bucket extends GraphicElement {

	// CONSTANTS

	// Graphics
	private final static Color COLOR = Color.rgb(192, 192, 200); 
	// Physics
	private final static double PUSHMULT = 500.0; // Bouncing force between tiles
	private final static double WALLPUSHMULT = 50.0; // Bouncing force when tiles hit walls

	/*****************
	 * Removed feature
	 *

	private final static double PULLMULT = 0.0; // Pulling force of despawning letters, rather gimmicky at the moment
	// NOTE: Needs really big numbers to have any visible effect, in the order of a million or more
	// High numbers make the game messy by moving all letters whenever a word is formed

	 */

	final static double BUCKETRADIUS = 300.0;

	// FIELDS
	private Random rng = new Random();
	private ArrayList<LetterTile> tiles = new ArrayList<LetterTile>();
	private Circle background = new Circle();



	public Bucket(double xPos, double yPos, Group parent) {
		super(xPos, yPos, parent);

		background.setCenterX(xPos + BUCKETRADIUS);
		background.setCenterY(yPos + BUCKETRADIUS);
		background.setRadius(BUCKETRADIUS);
		background.setFill(COLOR);
		parent.getChildren().add(background);
	}

	public void add(char l) {
		double x = rng.nextInt((int) Math.floor(BUCKETRADIUS * 2));
		double y = rng.nextInt((int) Math.floor(BUCKETRADIUS * 2));
		tiles.add(new LetterTile(x + xPos, y + yPos, parentRef.get(), l));
	}

	public void remove(Character l) {
		for (LetterTile x : tiles)
			if (x.getLetter() == l && !x.isDespawning()) {
				x.setRemove();
				return;
			}
	}

	public void removeAll() {
		for (LetterTile x : tiles)
			x.setRemove();
	}

	public void removeAllNow() {
		removeAll();
		for (LetterTile x : tiles)
			x.setRemoveNow();
	}

	public void select(Character l) {
		for (LetterTile x : tiles) 
			if (x.getLetter() == l && !(x.isSelected()) ) {
				x.select();
				return;
			}
	}

	public void deselect(Character l) {
		for (LetterTile x : tiles)
			if (x.getLetter() == l && x.isSelected() ) {
				x.deselect();
				return;
			}
	}

	public void deselectAll() {
		for (LetterTile x : tiles)
			if (x.isSelected())
				x.deselect();
	}

	public void shuffle() {
		for (LetterTile x : tiles) {
			x.setX(rng.nextInt((int) Math.floor(BUCKETRADIUS * 1.75)) + xPos);
			x.setY(rng.nextInt((int) Math.floor(BUCKETRADIUS * 1.75)) + yPos);
			x.stop();
		}		
	}

	private void purge() {
		int i = 0;
		while (i != tiles.size()) {
			if (tiles.get(i).isRemovable()) {
				tiles.get(i).purge();
				tiles.remove(i);
			} else
				++i;
		}
	}

	public void animate(double elapsed) {

		// Individual animation

		for (LetterTile x : tiles)
			x.animate(elapsed);

		purge();

		// PHYSICS //

		// Collisions

		for (int i = 0; i != tiles.size(); ++i) {
			for (int j = i +1; j != tiles.size(); ++j) { 
				if (tiles.get(i).isSolid() && tiles.get(j).isSolid()) {
					double radius = (tiles.get(i).getSize() + tiles.get(j).getSize()) * 0.5;
					double xDiff = tiles.get(i).getX() - tiles.get(j).getX();
					double yDiff = tiles.get(i).getY() - tiles.get(j).getY();
					double distance = Math.hypot(xDiff, yDiff);
					if (distance < radius) {
						double totPush = PUSHMULT * elapsed * (radius - distance);
						if (xDiff != 0) {
							tiles.get(i).pushX(xDiff / distance * totPush);
							tiles.get(j).pushX(-xDiff / distance * totPush);
						}
						if (yDiff != 0) {
							tiles.get(i).pushY(yDiff / distance * totPush);
							tiles.get(j).pushY(-yDiff / distance * totPush);
						}
					}
				}
				/*************************************************************
				 * UNUSED BUT FUNCTIONING CODE - COMMENTED OUT FOR PERFORMANCE
				 *
				else if (tiles.get(i).isDespawning() || tiles.get(j).isDespawning()) {
					double xDiff = tiles.get(i).getX() - tiles.get(j).getX();
					double yDiff = tiles.get(i).getY() - tiles.get(j).getY();
					double distance = Math.hypot(xDiff,  yDiff);
					double totPush = PULLMULT * elapsed / distance;
					if (tiles.get(i).isDespawning()) {
						if (xDiff != 0) {
							tiles.get(j).pushX(xDiff / distance * totPush);
						}
						if (yDiff != 0)
							tiles.get(j).pushY(yDiff / distance * totPush);
					}
					if (tiles.get(j).isDespawning()) {
						if (xDiff != 0)
							tiles.get(i).pushX(-xDiff / distance * totPush);
						if (yDiff != 0)
							tiles.get(i).pushY(-yDiff / distance * totPush);
					}
				}
				 */
			}
		}

		// Walls

		for (int i = 0; i != tiles.size(); ++i) {
			double xDiff = xPos + BUCKETRADIUS - tiles.get(i).getX() - tiles.get(i).getSize() * 0.5;
			double yDiff = yPos + BUCKETRADIUS - tiles.get(i).getY() - tiles.get(i).getSize() * 0.5;
			double distance = Math.hypot(xDiff, yDiff);
			if (distance + tiles.get(i).getSize() * 0.7 > BUCKETRADIUS) {
				double totPush = WALLPUSHMULT * elapsed * (distance + tiles.get(i).getSize() * 0.7 - BUCKETRADIUS);
				if (xDiff != 0) 
					tiles.get(i).pushX(xDiff / distance * totPush);
				if (yDiff != 0)
					tiles.get(i).pushY(yDiff / distance * totPush);
			}
		}
	}

	public void show() {
		background.setVisible(true);
		for (LetterTile x : tiles)
			x.show();
	}

	public void hide() {
		background.setVisible(false);
		for (LetterTile x : tiles)
			x.hide();
	}
}