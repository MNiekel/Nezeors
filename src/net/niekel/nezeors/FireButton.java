package net.niekel.nezeors;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

public class FireButton extends Sprite {
	
	private final float screenHeight = MainActivity.CAMERA_HEIGHT;

	private final float SCALE = 2;

	public FireButton() {
		super(0, 0, Resources.firebuttonBitmap, MainActivity.getActivity().getVertexBufferObjectManager());
		setScaleCenter(getWidth() / 2, getHeight() / 2);
		setScale(SCALE);		
		setPosition(getWidth(), screenHeight - getScaleY()*getHeight());
	}
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			GameScene scene = (GameScene) getParent();
			scene.fireMissile();
			return true;
		}
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			return true;
		}
		return true;
	}
}
