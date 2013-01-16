package net.niekel.nezeors;

import java.util.Iterator;
import java.util.LinkedList;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.sensor.orientation.IOrientationListener;
import org.andengine.input.sensor.orientation.OrientationData;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import net.niekel.nezeors.RestartButton.RestartType;

import android.util.Log;

public class GameScene extends Scene implements IOrientationListener {
		
	private Ship ship;
	private LinkedList<Asteroid> asteroids;
	private LinkedList<Missile> missiles;
	private AsteroidPool mAsteroidPool;
	private MissilePool mMissilePool;
	
	private final float screenWidth = MainActivity.CAMERA_WIDTH;;
	private final float screenHeight = MainActivity.CAMERA_HEIGHT;
	private MainActivity activity = MainActivity.getActivity();
	
	private RestartButton restartButton = null;
	private Text scoreText;
	
	private int score = 0;
	private int shots = 0;
	private int level = 0;
		
	private boolean gameRunning = false;

	public void buildScene() {
		
		initBackground();
		
		ship = new Ship();
		attachChild(ship);
		
		asteroids = new LinkedList<Asteroid>();
		missiles = new LinkedList<Missile>();
		mAsteroidPool = new AsteroidPool();
		mMissilePool = new MissilePool();
		
		initAsteroids();
		
		ThrustButton thrustButton = new ThrustButton();
		attachChild(thrustButton);
		registerTouchArea(thrustButton);
		
		FireButton fireButton = new FireButton();
		attachChild(fireButton);
		registerTouchArea(fireButton);
		
		scoreText = new Text(0, 0, Resources.defaultFont, "Score: "+score, 12, new TextOptions(HorizontalAlign.CENTER), MainActivity.getActivity().getVertexBufferObjectManager());
		scoreText.setColor(Color.WHITE);
		scoreText.setHorizontalAlign(HorizontalAlign.LEFT);
		attachChild(scoreText);

		setTouchAreaBindingOnActionDownEnabled(true);
		(activity.getEngine()).enableOrientationSensor(activity, (IOrientationListener) this);

		gameRunning = true;
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		if (!gameRunning) {
			return;
		}
		Iterator<Asteroid> asteroidIterator = asteroids.iterator();
		Asteroid a = null;
		Missile m;
		Asteroid splitAsteroid = null;
		Boolean hitAsteroid = false;
		while (asteroidIterator.hasNext()) {
			a = asteroidIterator.next();

			if (a.checkCollision(ship)) {
				Resources.explosionSound.play();
				ship.detachSelf();
				ship.setVisible(false);
				ship.clearUpdateHandlers();
				ship.setThrusters(false);
				Log.v("GameScene", "asteroid collides with ship");
				break;
				
			}
			
			Iterator<Missile> missileIterator = missiles.iterator();
			while (missileIterator.hasNext()) {
				m = missileIterator.next();
				if (a.checkCollision(m)) {
					mMissilePool.recyclePoolItem(m);
					missileIterator.remove();
					hitAsteroid = true;
					Resources.asteroidhitSound.play();
					Log.v("GameScene", "asteroid hit by missile");
					break;
				}
			}

			if (hitAsteroid) {
				float scale = a.getScaleX();
				if (scale > 0.25) {
					a.setScale(scale/2);
					splitAsteroid = mAsteroidPool.obtainPoolItem();
					splitAsteroid.initialize();
					splitAsteroid.setScale(scale/2);
					splitAsteroid.setPosition(a.getX(), a.getY());
				} else {
					mAsteroidPool.recyclePoolItem(a);
					asteroidIterator.remove();
				}
				score += 10 / scale;
				scoreText.setText("Score: " + score);
				Log.v("GameScene", "Your score = " + score);
				Log.v("GameScene", "Number of shots = " + shots);
				if (asteroids.size() == 0) {
					Log.v("GameScene", "No more asteroids left");
					endGame(RestartType.NEXTLEVEL);
				}
				break;
			}
		}

		if (splitAsteroid != null) {
			asteroids.add(splitAsteroid);
			attachChild(splitAsteroid);
			splitAsteroid = null;
		}

		if (!ship.isVisible()) {
			endGame(RestartType.RESTART);
		}
		
		Iterator<Missile> missileIterator = missiles.iterator();
		while (missileIterator.hasNext()) {
			m = missileIterator.next();
			if (!m.isAlive()) {
				mMissilePool.recyclePoolItem(m);
				missileIterator.remove();
			}
		}
	}

	@Override
	public void onOrientationAccuracyChanged(OrientationData pOrientationData) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void onOrientationChanged(OrientationData pOrientationData) {
		if (!gameRunning) {
			return;
		}
		float roll = pOrientationData.getRoll();
		if (Math.abs(roll) > 3) {
			ship.rotate(roll / Math.abs(roll));
		} else {
			ship.rotate(0);
		}
	}	
	
	private void initAsteroids() {
		Asteroid asteroid;
		for (int i = 0; i <= level; i++) {
			asteroid = mAsteroidPool.obtainPoolItem();
			asteroid.initialize();
			attachChild(asteroid);
			asteroids.add(asteroid);
		}
	}
	
	private void pauseObjects(boolean pause) {
		ship.setPaused(pause);
		Iterator<Asteroid> iterator = asteroids.iterator();
		while (iterator.hasNext()) {
			Asteroid a = iterator.next();
			a.setPaused(pause);
		}
		Iterator<Missile> missileIterator = missiles.iterator();
		while (missileIterator.hasNext()) {
			Missile m = missileIterator.next();
			m.setPaused(pause);
		}
	}
	
	public boolean pauseGame() {
		if (!gameRunning) {
			return false;
		}
		endGame(RestartType.PAUSE);
		pauseObjects(true);
		return true;
	}
	
	private void endGame(RestartType type) {
		gameRunning = false;
		Resources.thrustSound.stop();
		clearMissiles();
		restartButton = new RestartButton(type);
		attachChild(restartButton);
		registerTouchArea(restartButton);
		Log.v("GameScene", "Your score = " + score);
		Log.v("GameScene", "Number of shots = " + shots);
	}
	
	private void initBackground() {
		Sprite bg = new Sprite(0, 0, Resources.bgBitmap, MainActivity.getActivity().getVertexBufferObjectManager());
		
		bg.setScaleCenter(0, 0);
		bg.setScaleX(screenWidth / bg.getWidth());
		bg.setScaleY(screenHeight / bg.getHeight());
		setBackground(new SpriteBackground(bg));
	}
	
	public void restartGame(RestartType type) {
		switch (type) {
			case PAUSE:
				pauseObjects(false);
				break;
			case RESTART:
				level = 0;
				score = 0;
				shots = 0;
				clearAsteroids();
				initAsteroids();
				ship.revive();
				break;
			case NEXTLEVEL:
				level++;
				initAsteroids();
				ship.revive();
				break;
		}
		
		if (!ship.hasParent()) {
			attachChild(ship);
		}

		if (restartButton != null) {
			restartButton.remove();
			unregisterTouchArea(restartButton);
			restartButton.dispose();
			restartButton = null;
		}
		scoreText.setText("Score: " + score);
		gameRunning = true;
	}
	
	private void clearMissiles() {
		Iterator<Missile> iterator = missiles.iterator();
		while (iterator.hasNext()) {
			Missile m = iterator.next();
			mMissilePool.recyclePoolItem(m);
			iterator.remove();
		}
	}
	
	private void clearAsteroids() {
		Iterator<Asteroid> iterator = asteroids.iterator();
		while (iterator.hasNext()) {
			Asteroid a = iterator.next();
			mAsteroidPool.recyclePoolItem(a);
			iterator.remove();
		}
	}
	
	public void setThrusters(boolean on) {
		if ((!ship.isVisible()) || (!gameRunning)) {
			return;
		}
		ship.setThrusters(on);
		if (on) {
			Resources.thrustSound.play();
		} else {
			Resources.thrustSound.stop();
		}
	}
	
	public void fireMissile() {
		if ((!ship.isVisible()) || (!gameRunning)) {
			return;
		}
		
		shots+= 1;
		Resources.shotSound.play();
		
		double angle = Math.toRadians(ship.getRotation());
		float velX = (float) (Math.cos(angle) * 10);
		float velY = (float) (Math.sin(angle) * 10);
		
		float localX = ship.getRotationCenterX() + ship.getWidth() / 2;
		float localY = ship.getRotationCenterY();
		float[] global = ship.convertLocalToSceneCoordinates(localX, localY);
		
		Missile missile = mMissilePool.obtainPoolItem();
		missile.initialize();
		missile.setPosition(global[0] - missile.getWidth() / 2, global[1] - missile.getHeight() / 2);
		missile.setVelocity(velX, velY);
		attachChild(missile);
		missiles.add(missile);
	}
}
