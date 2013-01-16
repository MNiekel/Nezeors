package net.niekel.nezeors;

import org.andengine.util.adt.pool.GenericPool;

public class MissilePool extends GenericPool<Missile> {
	
	private static MissilePool thisInstance;
	
	public static MissilePool getInstance() {
		if (thisInstance == null) {
			return new MissilePool();
		} else {
			return thisInstance;
		}
	}

	public MissilePool() {
		thisInstance = this;
	}
	
	@Override
	protected Missile onAllocatePoolItem() {
		return new Missile();
	}
	
	protected void onHandleRecycleItem(final Missile missile) {
		missile.clearUpdateHandlers();
		missile.setVisible(false);
		missile.detachSelf();
	}
}