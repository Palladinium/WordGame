package graphics;

// Written by Patrick Chieppe

import java.lang.ref.SoftReference;

import javafx.scene.Group;

abstract class GraphicElement {

	// FIELDS

	// Reference to parent Group 
	protected SoftReference<Group> parentRef;
	// Indispensable graphics fields
	protected double xPos = 0.0;
	protected double yPos = 0.0;

	// METHODS

	// Constructor
	public GraphicElement(double xPos, double yPos, Group parent) {
		this.xPos = xPos;
		this.yPos = yPos;
		parentRef = new SoftReference<Group>(parent);
	}

	// Accessor functions
	public double getX() {
		return xPos;
	}

	public double getY() {
		return yPos;
	}

	public void setX(double xPos) {
		this.xPos = xPos;
	}

	public void setY(double yPos) {
		this.yPos = yPos;
	}

	public abstract void hide();

	public abstract void show();
}
