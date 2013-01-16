package net.niekel.nezeors;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import android.util.Log;

public class RestartButton extends Rectangle {
	
	public enum RestartType {
		RESTART,
		PAUSE,
		NEXTLEVEL
	}
	
	private Text text = null;
	private RestartType restartType;
	
	public RestartButton(RestartType type) {
		super(0, 0, MainActivity.CAMERA_WIDTH, MainActivity.CAMERA_HEIGHT, MainActivity.getActivity().getVertexBufferObjectManager());
		setColor(Color.TRANSPARENT);
		restartType = type;
		Log.v("RestartButton", "restartType = " + restartType);
		switch (restartType) {
			case RESTART:
				text = new Text(100, 100, Resources.defaultFont, "You died!\n\nClick screen to start new game, Back to quit game", new TextOptions(HorizontalAlign.CENTER), MainActivity.getActivity().getVertexBufferObjectManager());
				break;
			case PAUSE:
				text = new Text(100, 100, Resources.defaultFont, "Game paused\n\nClick screen to continue, Back to quit game", new TextOptions(HorizontalAlign.CENTER), MainActivity.getActivity().getVertexBufferObjectManager());
				break;
			case NEXTLEVEL:
				text = new Text(100, 100, Resources.defaultFont, "Congratulations, you destroyed all the asteroids!\n\nClick screen to start the next level, Back to quit game", new TextOptions(HorizontalAlign.CENTER), MainActivity.getActivity().getVertexBufferObjectManager());
				break;
		}
		text.setColor(Color.GREEN);
		text.setHorizontalAlign(HorizontalAlign.CENTER);
		text.setPosition((MainActivity.CAMERA_WIDTH - text.getWidth())/ 2, (MainActivity.CAMERA_HEIGHT - text.getHeight())/ 2);
		attachChild(text);
	}
	
	public void remove() {
		if (text != null) {
			detachChild(text);
			text.dispose();
			text = null;
		}
		detachSelf();
	}
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			GameScene scene = (GameScene) getParent();
			scene.restartGame(restartType);
			return true;
		}
		return false;
	}	
}