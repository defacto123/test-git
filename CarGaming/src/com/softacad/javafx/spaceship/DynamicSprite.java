package com.softacad.javafx.spaceship;

public class DynamicSprite extends Sprite {
	//pixels / seconds
	private double velocityX;
	private final double MAX_VELOCITY = 2400;
	private double velocityY;

	public DynamicSprite(String imagePath, double x, double y, double width, double height) {
		super(imagePath, x, y, width, height);
	}

	public void increaseVelocity(double velocity) {
		//setVelocity(this.velocityX+velocity);
	}

	public double getVelocityX() {
		return velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;

		if (this.velocityY > MAX_VELOCITY) {
			this.velocityY = MAX_VELOCITY;
		}

		if (this.velocityY < -MAX_VELOCITY) {
			this.velocityY = -MAX_VELOCITY;
		}
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;

		if (this.velocityX > MAX_VELOCITY) {
			this.velocityX = MAX_VELOCITY;
		}

		if (this.velocityX < -MAX_VELOCITY) {
			this.velocityX = -MAX_VELOCITY;
		}
	}

	public void update(double deltaTime) {
		double xOffset = getVelocityX() * deltaTime;
		moveX(xOffset);

		double yOffset = getVelocityY() * deltaTime;
		moveY(yOffset);

	}

	public void moveY(double amount) {
		double newY = getY() + amount;
		setY(newY);
	}

	public void moveX(double amount) {
		double newX = getX() + amount;
		setX(newX);
	}
}
