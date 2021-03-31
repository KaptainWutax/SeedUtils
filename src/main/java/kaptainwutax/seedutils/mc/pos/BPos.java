package kaptainwutax.seedutils.mc.pos;

import kaptainwutax.seedutils.util.math.Vec3i;

public class BPos extends Vec3i {

	public BPos(int x, int y, int z) {
		super(x, y, z);
	}

	public BPos(Vec3i vec3i) {
		super(vec3i.getX(),vec3i.getY(),vec3i.getZ());
	}

	public BPos add(BPos pos) {
		return this.add(pos.getX(), pos.getY(), pos.getZ());
	}

	public BPos subtract(BPos pos) {
		return this.subtract(pos.getX(), pos.getY(), pos.getZ());
	}

	public BPos add(int x, int y, int z) {
		return new BPos(this.getX() + x, this.getY() + y, this.getZ() + z);
	}

	public BPos subtract(int x, int y, int z) {
		return new BPos(this.getX() - x, this.getY() - y, this.getZ() - z);
	}

	public BPos toChunkCorner() {
		return new BPos(this.getX() & -16, this.getY(), this.getZ() & -16);
	}

	public CPos toChunkPos() {
		return new CPos(this.getX() >> 4, this.getZ() >> 4);
	}

	public RPos toRegionPos(int regionSize) {
		int x = this.getX() < 0 ? this.getX() - regionSize + 1 : this.getX();
		int z = this.getZ() < 0 ? this.getZ() - regionSize + 1 : this.getZ();
		return new RPos(x / regionSize, z / regionSize, regionSize);
	}

}
