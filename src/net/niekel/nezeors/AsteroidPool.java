package net.niekel.nezeors;

import org.andengine.util.adt.pool.GenericPool;

public class AsteroidPool extends GenericPool<Asteroid> {

	@Override
	protected Asteroid onAllocatePoolItem() {
		return new Asteroid();
	}
	
	protected void onHandleRecycleItem(final Asteroid asteroid) {
		asteroid.clearUpdateHandlers();
		asteroid.setVisible(false);
		asteroid.detachSelf();
	}
}
