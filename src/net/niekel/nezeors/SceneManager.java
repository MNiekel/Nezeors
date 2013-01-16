package net.niekel.nezeors;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import net.niekel.nezeors.RestartButton.RestartType;

import android.graphics.Typeface;
import android.util.Log;

public class SceneManager { 
	private SceneType currentScene;
	private BaseGameActivity activity;
	private Engine engine;
	
	public static TextureRegion splashBitmap;
	public static TextureRegion titleBitmap;
	public static TextureRegion bgBitmap;
	
	public static TiledTextureRegion shipBitmap;
	public static TextureRegion asteroidBitmap;
	public static TextureRegion missileBitmap;
	
	public static TextureRegion firebuttonBitmap;
	public static TextureRegion thrustbuttonBitmap;
	public static TextureRegion restartbuttonBitmap;
	
	public static Font defaultFont;
	
	private Scene splashScene;
	private Scene titleScene;
	private GameScene gameScene;
	
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
		thisInstance = this;
	}


	//Method loads all of the splash scene resources
	public void loadSplashSceneResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas splashMemory = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
		splashBitmap = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashMemory, activity, "splash.png", 0, 0);
		splashMemory.load();
	}
		
	//Method loads all of the resources for the game scenes
	public void loadGameResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas titleMemory = new BitmapTextureAtlas(activity.getTextureManager(), 264, 97, TextureOptions.BILINEAR);
		titleBitmap = BitmapTextureAtlasTextureRegionFactory.createFromAsset(titleMemory, activity, "title.png", 0, 0);
		titleMemory.load();
		BitmapTextureAtlas bgMemory = new BitmapTextureAtlas(activity.getTextureManager(), 640, 368, TextureOptions.BILINEAR);
		bgBitmap = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bgMemory, activity, "black_star.png", 0, 0);
		bgMemory.load();
		BitmapTextureAtlas shipMemory = new BitmapTextureAtlas(activity.getTextureManager(), 120, 80, TextureOptions.BILINEAR);
		shipBitmap = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(shipMemory, activity, "astrofighter.png", 0, 0, 2, 1);
		shipMemory.load();		
		BitmapTextureAtlas asteroidMemory = new BitmapTextureAtlas(activity.getTextureManager(), 80, 80, TextureOptions.BILINEAR);
		asteroidBitmap = BitmapTextureAtlasTextureRegionFactory.createFromAsset(asteroidMemory, activity, "asteroid.png", 0, 0);
		asteroidMemory.load();
		BitmapTextureAtlas firebuttonMemory = new BitmapTextureAtlas(activity.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		firebuttonBitmap = BitmapTextureAtlasTextureRegionFactory.createFromAsset(firebuttonMemory, activity, "firebutton.png", 0, 0);
		firebuttonMemory.load();
		BitmapTextureAtlas thrustbuttonMemory = new BitmapTextureAtlas(activity.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		thrustbuttonBitmap = BitmapTextureAtlasTextureRegionFactory.createFromAsset(thrustbuttonMemory, activity, "thrustbutton.png", 0, 0);
		thrustbuttonMemory.load();
		BitmapTextureAtlas missileMemory = new BitmapTextureAtlas(activity.getTextureManager(), 10, 10, TextureOptions.BILINEAR);
		missileBitmap = BitmapTextureAtlasTextureRegionFactory.createFromAsset(missileMemory, activity, "shot.png", 0, 0);
		missileMemory.load();
		
        BitmapTextureAtlas fontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        fontTexture.load();
        defaultFont = new Font(activity.getFontManager(), fontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true, Color.WHITE);
        defaultFont.load();
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
