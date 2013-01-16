package net.niekel.nezeors;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import android.graphics.Typeface;
import android.util.Log;

public class Resources {
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
	
	public static Sound shotSound;
	public static Sound explosionSound;
	public static Sound asteroidhitSound;
	public static Sound thrustSound;
	
	private boolean splashLoaded = false;
	private boolean gameLoaded = false;
	
	public void loadSplashResources(BaseGameActivity activity) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas splashMemory = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
		splashBitmap = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashMemory, activity, "splash.png", 0, 0);
		splashMemory.load();
		splashLoaded = true;
	}
	
	public void loadGameResources(BaseGameActivity activity) {
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
        
		SoundFactory.setAssetBasePath("sfx/");
		try {
			shotSound = SoundFactory.createSoundFromAsset(activity.getEngine().getSoundManager(), activity, "shot.ogg");
		} catch (IOException e) {
			Log.e("SceneManager", "Error creating shotSound");
		}
		try {
			explosionSound = SoundFactory.createSoundFromAsset(activity.getEngine().getSoundManager(), activity, "explosion.ogg");
		} catch (IOException e) {
			Log.e("SceneManager", "Error creating explosionSound");
		}
		try {
			asteroidhitSound = SoundFactory.createSoundFromAsset(activity.getEngine().getSoundManager(), activity, "asteroidhit.ogg");
		} catch (IOException e) {
			Log.e("SceneManager", "Error creating asteroidhitSound");
		}
		try {
			thrustSound = SoundFactory.createSoundFromAsset(activity.getEngine().getSoundManager(), activity, "thrust.ogg");
			while (!thrustSound.isLoaded()) {
				//waiting....
			}
			thrustSound.setLooping(true);
		} catch (IOException e) {
			Log.e("SceneManager", "Error creating thrustSound");
		}
		gameLoaded = true;
	}
	
	public boolean splashLoaded() {
		return splashLoaded;
	}
	
	public boolean gameLoaded() {
		return gameLoaded;
	}
}
