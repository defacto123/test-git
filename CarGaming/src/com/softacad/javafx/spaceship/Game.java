package com.softacad.javafx.spaceship;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Game extends BaseGame {

	private Sprite background;
	private Car car;
	private ArrayList<LaserBeam> laserBeams;
	private ArrayList<DynamicSprite> whieLines;
	private ArrayList<DynamicSprite> cars;

	private ArrayList<DynamicSprite> enemy;
	private double timePassed = 0;
	private Random randomGenerator;
	Integer shipsHit = 0;
	private double carsTimePassed = 0;

	@Override
	protected void setupGame() {
		background = new Sprite("background.jpg", 0, 0,
				canvas.getWidth(), canvas.getHeight());

		car = new Car("nissan.png", 0, 0, 336, 150);
		double defenderY = (canvas.getHeight()-car.getHeight())/2;
		car.setY(defenderY);

		laserBeams = new ArrayList<LaserBeam>();
		whieLines = new ArrayList<DynamicSprite>();
		cars = new ArrayList<DynamicSprite>();
		randomGenerator = new Random();
	}

	@Override
	protected void updateGame(double deltaTime) {
		car.update(deltaTime);
		if (car.getY() < 0) {
			car.setY(0);
		}
		if (car.getX() < 0) {
			car.setX(0);
		}

		double maxY = canvas.getHeight() - car.getHeight();
		if (car.getY() > maxY) {
			car.setY(maxY);
		}
		double maxX = canvas.getWidth() - car.getWidth();
		if (car.getX() > maxX) {
			car.setX(maxX);

		}

		//Updates laser beams
		for (int i = 0; i < laserBeams.size(); i++) {
			LaserBeam currentLaser = laserBeams.get(i);
			currentLaser.update(deltaTime);
			if (!currentLaser.getBoundery().intersects(0, 0, canvas.getWidth(), canvas.getHeight())) {
				laserBeams.remove(currentLaser);
			}
		}

		//Updates space lines
		for (int i = 0; i < whieLines.size(); i++) {
			DynamicSprite currentShip = whieLines.get(i);
			currentShip.update(deltaTime);
			boolean outOfSpace = !currentShip.getBoundery().intersects(0, 0, canvas.getWidth(), canvas.getHeight());
			boolean isHitByALaser = isHitByLaser(currentShip);
			if (isHitByALaser) {
				shipsHit++;
			}



			if (outOfSpace || isHitByALaser) {
				whieLines.remove(currentShip);
			}
		}

		for (int i = 0; i < cars.size(); i++) {
			DynamicSprite currentCar = cars.get(i);
			currentCar.update(deltaTime);
			boolean outOfSpace = !currentCar.getBoundery().intersects(0, 0, canvas.getWidth(), canvas.getHeight());
			boolean isHitByALaser = isHitByLaser(currentCar);



			if (outOfSpace || isHitByALaser) {
				cars.remove(currentCar);
			}

			if (currentCar.getBoundery().intersects(car.getBoundery())) {
				gameOver();
				cars.remove(currentCar);
			}
		}

		timePassed += deltaTime;
		if (timePassed > 0.5) {
			generateSpaceShip();
			timePassed = 0;
		}


		carsTimePassed+= deltaTime;
		if (carsTimePassed > 2) {
			generateCar();
			this.carsTimePassed = 0;
		}
	}

	private void generateCar() {
		Random random = new Random();
		Car car = new Car("enemy.png", canvas.getWidth(), 0, 350, 190);
		Double maxDouble = canvas.getHeight() - car.getHeight();
		int y = random.nextInt(maxDouble.intValue());
		car.setY(y);
		car.setVelocityX(-1200);
		cars.add(car);
	}

	private void gameOver() {
		//To be implemented by Antonio ! : )

		System.out.println("Game over");
	}

	private boolean isHitByLaser(DynamicSprite currentShip) {
		for (int i = 0; i < laserBeams.size(); i++) {
			LaserBeam currentBeam = laserBeams.get(i);
			if (currentBeam.getBoundery().intersects(currentShip.getBoundery())) {
				return true;
			}
		}

		return false;
	}

	private void generateSpaceShip() {
		DynamicSprite spaceShip = new DynamicSprite("white_line.png", 0, 0, 173, 21);

		double minY = car.getWidth() + 41;
		double spaceShipY = minY;
		double spaceShipX = canvas.getHeight();

		spaceShip.setY(spaceShipY);
		spaceShip.setX(spaceShipX);

		spaceShip.setVelocityX(-1000);
		whieLines.add(spaceShip);
	}

	@Override
	protected void drawGame() {
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		background.render(graphicsContext);

		for (int i = 0; i < laserBeams.size(); i++) {
			LaserBeam currentLaser = laserBeams.get(i);
			currentLaser.render(graphicsContext);
		}

		for (int i = 0; i < whieLines.size(); i++) {
			DynamicSprite currentShip = whieLines.get(i);
			currentShip.render(graphicsContext);
		}

		for (int i = 0; i < cars.size(); i++) {
			DynamicSprite car = cars.get(i);
			car.render(graphicsContext);
		}

		car.render(graphicsContext);

		graphicsContext.setFill(Color.WHITE);
		Font font = new Font(30);
		graphicsContext.setFont(font);
		graphicsContext.fillText(shipsHit.toString(), 30, 30);
	}

	@Override
	protected void onMousePressed(MouseEvent event) {
		System.out.println("Mouse Pressed:" + event.getX() +" " + event.getY());

	}

	@Override
	protected void onMouseReleased(MouseEvent event) {

	}

	@Override
	protected void onKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.UP)) {
			car.setVelocityY(-1200);
		} else if(event.getCode().equals(KeyCode.DOWN)) {
			car.setVelocityY(1200);
		} else if(event.getCode().equals(KeyCode.LEFT)) {
			car.setVelocityX(-1200);
		} else if(event.getCode().equals(KeyCode.RIGHT)) {
			car.setVelocityX(1200);
		} else if(event.getCode().equals(KeyCode.SPACE)) {
			LaserBeam laserBeam = car.fire();
			laserBeams.add(laserBeam);
		}
	}

	@Override
	protected void onKeyReleased(KeyEvent event) {
		if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.DOWN)) {
			car.setVelocityY(0);
		}

		if (event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.RIGHT)) {
			car.setVelocityX(0);
		}
	}

}
