package kaptainwutax.seedutils.mc.pos;

import kaptainwutax.seedutils.util.math.Vec3i;

public class RPos extends Vec3i {

	private final int size;

	public RPos(int x, int z, int size) {
		super(x, 0, z);
		this.size = size;
	}

	public int getSize() {
		return this.size;
	}

	public BPos toBlockPos() {
		return new BPos(this.getX() * this.getSize(), 0, this.getZ() * this.getSize());
	}

}
