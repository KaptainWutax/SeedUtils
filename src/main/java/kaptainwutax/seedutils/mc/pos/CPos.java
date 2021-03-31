package kaptainwutax.seedutils.mc.pos;

import kaptainwutax.seedutils.util.math.Vec3i;

public class CPos extends Vec3i {

	public CPos(int x, int z) {
		super(x, 0, z);
	}

	public CPos(Vec3i vec3i) {
		super(vec3i.getX(),vec3i.getY(),vec3i.getZ());
	}

	public BPos toBlockPos() {
		return this.toBlockPos(0);
	}

	public BPos toBlockPos(int y) {
		return new BPos(this.getX() << 4, y, this.getZ() << 4);
	}

	public RPos toRegionPos(int regionSize) {
		int x = this.getX() < 0 ? this.getX() - regionSize + 1 : this.getX();
		int z = this.getZ() < 0 ? this.getZ() - regionSize + 1 : this.getZ();
		return new RPos(x / regionSize, z / regionSize, regionSize);
	}

}
