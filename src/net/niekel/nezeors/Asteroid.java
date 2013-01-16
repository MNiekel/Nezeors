package net.niekel.nezeors;

import org.andengine.entity.sprite.Sprite;

public class Asteroid extends Sprite {
		
	protected float mAngularVelocity;
	protected float mVelocityX;
	protected float mVelocityY;
	
	private boolean mPaused = false;
    
    public Asteroid() {
        super(0, 0, Resources.asteroidBitmap, MainActivity.getActivity().getVertexBufferObjectManager());
    }
    
    public void initialize() {
    	final float screenWidth = MainActivity.CAMERA_WIDTH;
    	final float screenHeight = MainActivity.CAMERA_HEIGHT;
		setScale(1);
		setPosition((float) Math.random() * screenWidth/2 + 3*screenWidth/4, (float) Math.random() * screenHeight/2 + 3*screenHeight/4);
		setRotationCenter(mWidth / 2, mHeight/2);
		mVelocityX = ((float) Math.random() - 0.5f) * 2;
		mVelocityY = ((float) Math.random() - 0.5f) * 2;
		mAngularVelocity = ((float) Math.random() - 0.5f) * 10;
		setVisible(true);
		setPaused(false);
    }
    
    @Override
    protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		if (mPaused) {
			return;
		}
		mX += mVelocityX;
		mY += mVelocityY;
		final float width = MainActivity.CAMERA_WIDTH;
		final float height = MainActivity.CAMERA_HEIGHT;
		mX = ((width + (mX + mWidth / 2)) % width) - mWidth / 2;
		mY = ((height + (mY + mHeight / 2)) % height) - mHeight / 2;
		setRotation(getRotation() + mAngularVelocity);
    }
    
    public boolean checkCollision(Sprite other) {
    	float otherCoordinates[] = other.convertLocalToSceneCoordinates(other.getRotationCenterX(), other.getRotationCenterY());
    	float x1 = otherCoordinates[0];
    	float y1 = otherCoordinates[1];
    	float r1 = Math.min(other.getHeightScaled() / 2, other.getWidthScaled() / 2);
    	float coordinates[] = convertLocalToSceneCoordinates(getRotationCenterX(), getRotationCenterY());
    	float x2 = coordinates[0];
    	float y2 = coordinates[1];
    	float r2 = Math.min(getHeightScaled() / 2, getWidthScaled() / 2);
    	float d = (float) Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2)); 
    	return (d < (r1 + r2));
    }
    
    public void setPaused(boolean paused) {
    	mPaused = paused;
    }
    
    public boolean isPaused() {
    	return mPaused;
    }
}
