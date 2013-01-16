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
		Sprite splash = new Sprite(0, 0, SceneManager.splashBitmap, MainActivity.getActivity().getVertexBufferObjectManager()) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		
		splash.setScaleCenter(0, 0);
		float scaleX = screenWidth / splash.getWidth() / 2;
		float scaleY = screenHeight / splash.getHeight() / 2;
		float scale = Math.min(scaleX, scaleY);
		splash.setScale(scale);

		splash.setPosition((screenWidth - splash.getWidthScaled()) / 2, (screenHeight - splash.getHeightScaled()) / 2);
		attachChild(splash);
	}
}
