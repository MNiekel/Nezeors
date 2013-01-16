package net.niekel.nezeors;

import org.andengine.entity.sprite.TiledSprite;



public class Ship extends TiledSprite {
	
	private float mAngularVelocity;
	private float mVelocityX;
	private float mVelocityY;
	private final float startX = MainActivity.CAMERA_WIDTH / 2 - mWidth / 2;
	private final float startY = MainActivity.CAMERA_HEIGHT / 2 - mHeight / 2;

	private boolean thrusters = false;
	private boolean paused = false;
	
	private static Ship thisInstance;
	
	private final float defaultAngularVelocity = 2;
	private final float MAX_VELOCITY = 100;
	
	public Ship() {
		super(0, 0, SceneManager.shipBitmap, MainActivity.getActivity().getVertexBufferObjectManager());
		setPosition(startX, startY);
		setRotationCenter(mWidth / 2, mHeight / 2);
		thisInstance = this;
	}
	
	public static Ship getInstance() {
		return thisInstance;
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		if (paused) {
			return;
		}
		mX += mVelocityX;
		mY += mVelocityY;
		final float width = MainActivity.CAMERA_WIDTH;
		final float height = MainActivity.CAMERA_HEIGHT;
		mX = ((width + (mX + mWidth / 2)) % width) - mWidth / 2;
		mY = ((height + (mY + mHeight / 2)) % height) - mHeight / 2;
		float rotation = getRotation() + mAngularVelocity;
		setRotation(rotation);
		setVelocity();
	}
	
	private void setVelocity() {
		float velX = mVelocityX;
		float velY = mVelocityY;
		if (thrusters) {
			double angle = Math.toRadians((double) getRotation());
			double forwardX = Math.cos(angle);
			double forwardY = Math.sin(angle);
			velX += (float) forwardX * 0.1;
			velY += (float) forwardY * 0.1;
		}
		if (!maxVelocity(velX, velY)) {
			mVelocityX = velX;
			mVelocityY = velY;
		}
	}
	
	public float getVelocityX() {
		return mVelocityX;
	}
	
	public float getVelocityY() {
		return mVelocityY;
	}
	
	private boolean maxVelocity(float vx, float vy) {
		return ((vx * vx + vy * vy) > MAX_VELOCITY);
	}
	
	public void rotate(float direction) {
		mAngularVelocity = defaultAngularVelocity * direction;
	}
	
	public void setThrusters(boolean on) {
		thrusters = on;
		if (on) {
			setCurrentTileIndex(1);
		} else {
			setCurrentTileIndex(0);
		}
	}
	
	public void setPaused(boolean pause) {
		paused = pause;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void revive() {
		mVelocityX = 0;
		mVelocityY = 0;
		mAngularVelocity = 0;
		setRotation(0);
		thrusters = false;
		paused = false;
		setVisible(true);
		setX(startX);
		setY(startY);
	}
}
