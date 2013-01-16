package net.niekel.nezeors;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;
import net.niekel.nezeors.RestartButton.RestartType;

import android.util.Log;

public class SceneManager { 
	private BaseGameActivity activity;
	private Engine engine;
	private Resources resources;
	
	private Scene splashScene;
	private Scene titleScene;
	private GameScene gameScene;
	
	private SceneType currentScene;
	
	private static SceneManager thisInstance;

	public enum SceneType {
		SPLASH,
		TITLE,
		GAME
	}

	public static SceneManager getInstance() {
		if (thisInstance == null) {
			return new SceneManager(MainActivity.getActivity(), MainActivity.getActivity().getEngine());
		} else {
			return thisInstance;
		}
	}
	
	public SceneManager(BaseGameActivity pActivity, Engine pEngine) {
		activity = pActivity;
		engine = pEngine;
		resources = new Resources();
		thisInstance = this;
	}

	//Method loads all of the splash scene resources
	public void loadSplashSceneResources() {
		resources.loadSplashResources(activity);
	}
		
	//Method loads all of the resources for the game scenes
	public void loadGameResources() {
		resources.loadGameResources(activity);

	}

	//Method creates the Splash Scene
	public Scene createSplashScene() {
		splashScene = new SplashScene();
		currentScene = SceneType.SPLASH;
		return splashScene;
	}
	
	//Method creates the Title Scene
	public Scene createTitleScene() {
		titleScene = new TitleScene();
		return titleScene;
	}
	
	//Method creates all of the Game Scenes
	public Scene createGameScene() {
		gameScene = new GameScene();
		gameScene.buildScene();
		return gameScene;
	}
	
	public Scene getCurrentScene() {
		switch (currentScene) {
			case SPLASH:
				return splashScene;
			case TITLE:
				return titleScene;
			case GAME:
				return gameScene;
		}
		return null;
	}
	
	//Method allows you to get the currently active scene
	public SceneType getCurrentSceneType() {
		return currentScene;
	}

	//Method allows you to set the currently active scene
	public void setCurrentScene(SceneType scene) {
		currentScene = scene;
		Scene attachedScene = engine.getScene();
		if (attachedScene != null) {
			attachedScene.detachSelf();
		}
		switch (scene) {
			case SPLASH:
				break;
			case TITLE:
				if (titleScene == null) {
					Log.v("SceneManager", "titleScene == null => creating new");
					createTitleScene();
				}
				engine.setScene(titleScene);
				break;
			case GAME:
				if (gameScene == null) {
					Log.v("SceneManager", "gameScene == null => creating new");
					createGameScene();
				} else {
					gameScene.restartGame(RestartType.RESTART);
				}
				engine.setScene(gameScene);
				break;
		}
	}
}
