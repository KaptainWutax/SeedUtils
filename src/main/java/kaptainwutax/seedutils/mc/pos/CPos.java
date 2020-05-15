package kaptainwutax.seedutils.mc.pos;

public class CPos extends Pos {

	public CPos(int x, int z) {
		super(x, 0, z);
	}

	public BPos toBlockPos() {
		return this.toBlockPos(0);
	}

	public BPos toBlockPos(int y) {
		return new BPos(this.getX() << 4, y, this.getZ() << 4);
	}

}
