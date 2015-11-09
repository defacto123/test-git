package com.softacad.javafx.spaceship;

public class Car extends DynamicSprite {

	public Car(String imagePath, double x, double y, double width, double height) {
		super(imagePath, x, y, width, height);
	}

	public LaserBeam fire() {
		LaserBeam laserBeam = new LaserBeam("greenLaserRay.png", 0, 0, 290, 74, 2400);

		double laserBeamX = (getWidth() - laserBeam.getWidth())/2 + getX();
		double laserBeamY = (getHeight() - laserBeam.getHeight())/2 +getY();
		laserBeam.setX(laserBeamX);
		laserBeam.setY(laserBeamY);

		return laserBeam;
	}
}
