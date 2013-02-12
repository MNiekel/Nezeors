package net.niekel.nezeors;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

public class SplashScene extends Scene {
		
	private final float screenWidth = MainActivity.CAMERA_WIDTH;
	private final float screenHeight = MainActivity.CAMERA_HEIGHT;
	
	public SplashScene() {
		setBackground(new Background(0, 0, 0));
		Sprite logo = new Sprite(0, 0, Resources.logoBitmap, MainActivity.getActivity().getVertexBufferObjectManager()) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		
		logo.setScaleCenter(0, 0);
		float scaleX = screenWidth / logo.getWidth();
		float scaleY = screenHeight / logo.getHeight();
		float scale = Math.min(scaleX, scaleY);
		logo.setScale(scale);

		logo.setPosition((screenWidth - logo.getWidthScaled()) / 2, (screenHeight - logo.getHeightScaled()) / 2);
		attachChild(logo);
	}
}
