package net.niekel.nezeors;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

public class ThrustButton extends Sprite {
	
	private final float screenWidth = MainActivity.CAMERA_WIDTH;
	private final float screenHeight = MainActivity.CAMERA_HEIGHT;

	private final float SCALE = 2;

	public ThrustButton() {
		super(0, 0, Resources.thrustbuttonBitmap, MainActivity.getActivity().getVertexBufferObjectManager());
		setScaleCenter(getWidth() / 2, getHeight() / 2);
		setScale(SCALE);		
		setPosition(screenWidth - getScaleX()*getWidth(), screenHeight - getScaleY()*getHeight());
	}
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			GameScene scene = (GameScene) getParent();
			scene.setThrusters(true);
			return true;
		}
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			GameScene scene = (GameScene) getParent();
			scene.setThrusters(false);
			return true;
		}
		return false;
	}
}
