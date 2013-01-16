package net.niekel.nezeors;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.input.sensor.orientation.IOrientationListener;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

import net.niekel.nezeors.SceneManager.SceneType;

public class MainActivity extends BaseGameActivity {

	public static int CAMERA_WIDTH;
	public static int CAMERA_HEIGHT;

	private static Camera mCamera;
	private SceneManager sceneManager;
	
	private static MainActivity BaseActivity;
	private boolean gameCreated = false;
	
	public static MainActivity getActivity() {
		return BaseActivity;
	}
	
	public Engine getEngine() {
		return mEngine;
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		BaseActivity = this;
	    final DisplayMetrics metrics = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(metrics);
	    CAMERA_WIDTH = metrics.widthPixels;
	    CAMERA_HEIGHT = metrics.heightPixels;
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions =  new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		sceneManager = new SceneManager(this, mEngine);
		sceneManager.loadSplashSceneResources();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		pOnCreateSceneCallback.onCreateSceneFinished(sceneManager.createSplashScene());
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws Exception {
		mEngine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				sceneManager.loadGameResources();
				sceneManager.setCurrentScene(SceneType.TITLE);
				gameCreated = true;
			}
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	public void activateOrientationSensor(IOrientationListener pOrientationListener) {
		mEngine.enableOrientationSensor(this, pOrientationListener);
	}
	
	@Override
	public void onGameDestroyed() {
		super.onGameDestroyed();
		if (gameCreated) {
			Log.v("MainActivity", "Game should be destroyed");
			finish();
		}
	}
	
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if (pKeyCode == KeyEvent.KEYCODE_BACK && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			SceneType current = sceneManager.getCurrentSceneType();
			switch (current) {
				case SPLASH:
					return true;
				case TITLE:
					break;
				case GAME:
					GameScene scene = (GameScene) sceneManager.getCurrentScene();
					if (scene != null) {
						if (!scene.pauseGame()) {
							sceneManager.setCurrentScene(SceneType.TITLE);
						}
						return true;
					}
					break;
			}
		}
		return super.onKeyDown(pKeyCode, pEvent);
	}
}
