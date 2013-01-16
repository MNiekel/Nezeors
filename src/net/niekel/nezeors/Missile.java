package net.niekel.nezeors;

import org.andengine.entity.sprite.Sprite;

public class Missile extends Sprite {
		
	private float mVelocityX;
	private float mVelocityY;

	private float mSecondsElapsed;
	private boolean mAlive;
	private boolean mPaused = false;

	private final float LIFESPAN = 0.5f;

    public Missile() {
    	super(0, 0, SceneManager.missileBitmap, MainActivity.getActivity().getVertexBufferObjectManager());
    }
    
    public boolean isAlive() {
    	return mAlive;
    }
    
    public void initialize() {
    	mSecondsElapsed = 0;
    	mAlive = true;
    	setScale(1);
    	setVisible(true);
    	setRotationCenter(mWidth / 2, mHeight / 2);
    	mPaused = false;
    }
    
    public void setVelocity(float pVx, float pVy) {
    	mVelocityX = pVx;
    	mVelocityY = pVy;
    }
    
    @Override
    protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		if (mPaused) {
			return;
		}
		mSecondsElapsed += pSecondsElapsed;
		if (mSecondsElapsed > LIFESPAN) {
			mSecondsElapsed = 0;
			mAlive = false;
		}
		mX += mVelocityX;
		mY += mVelocityY;
		final float width = MainActivity.CAMERA_WIDTH;
		final float height = MainActivity.CAMERA_HEIGHT;
		mX = ((width + (mX + mWidth / 2)) % width) - mWidth / 2;
		mY = ((height + (mY + mHeight / 2)) % height) - mHeight / 2;
		setRotation(getRotation());
    }
    
    public void setPaused(boolean pause) {
    	mPaused = pause;
    }
    
    public boolean isPaused() {
    	return mPaused;
    }
}
