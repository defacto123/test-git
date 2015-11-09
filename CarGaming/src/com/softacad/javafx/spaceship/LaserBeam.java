package com.softacad.javafx.spaceship;

public class LaserBeam extends Sprite {
	private final double velocity;

	public LaserBeam(String imagePath, double x, double y, double width, double height, double velocity) {
		super(imagePath, x, y, width, height);
		this.velocity = velocity;
	}

	public void update(double deltaTime) {
		double xOffset = deltaTime * velocity;
		move(xOffset);
	}

	public void move(double amount) {
		double newX = getX() + amount;
		setX(newX);
	}
}
