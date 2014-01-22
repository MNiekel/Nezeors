package net.niekel.nezeors;

import org.andengine.util.adt.pool.GenericPool;

public class MissilePool extends GenericPool<Missile> {
	
	@Override
	protected Missile onAllocatePoolItem() {
		return new Missile();
	}
	
	protected void onHandleRecycleItem(final Missile missile) {
		//missile.clearUpdateHandlers();
		missile.setVisible(false);
		missile.detachSelf();
	}
}