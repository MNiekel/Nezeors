package net.niekel.nezeors;

import org.andengine.util.adt.pool.GenericPool;

public class AsteroidPool extends GenericPool<Asteroid> {
	
	private static AsteroidPool thisInstance;
	
	public static AsteroidPool getInstance() {
		if (thisInstance == null) {
			return new AsteroidPool();
		} else {
			return thisInstance;
		}
	}
	
	public AsteroidPool() {
		thisInstance = this;
	}

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
