package net.niekel.nezeors;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;

import net.niekel.nezeors.SceneManager.SceneType;

import android.util.Log;

public class TitleScene extends Scene {
		
	private final float screenWidth = MainActivity.CAMERA_WIDTH;;
	private final float screenHeight = MainActivity.CAMERA_HEIGHT;
	
	public TitleScene() {
		setBackground(new Background(0, 0, 0));
		Sprite menu = new Sprite(0, 0, Resources.titleBitmap, MainActivity.getActivity().getVertexBufferObjectManager()) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		
		setOnSceneTouchListener(new IOnSceneTouchListener() {

			@Override
	        public boolean onSceneTouchEvent(Scene pScene,TouchEvent pSceneTouchEvent) {
	    		SceneManager manager = SceneManager.getInstance();
	    		if (manager == null) {
		    		Log.v("MenuScene", "manager == null");
		    		return false;
	    		}
	    		manager.setCurrentScene(SceneType.GAME);
	    		return true;
	        }
	    });
		
		menu.setScaleCenter(0, 0);
		float scaleX = 2 * screenWidth / menu.getWidth() / 3;
		float scaleY = screenHeight / menu.getHeight() / 2;
		menu.setScale(scaleX, scaleY);
		menu.setPosition((screenWidth - menu.getWidthScaled()) / 2, (screenHeight - menu.getHeightScaled()) / 2);
		attachChild(menu);
	}
}
