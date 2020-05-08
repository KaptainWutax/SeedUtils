package kaptainwutax.seedutils.util.pos;

public class BPos extends Pos {

	public BPos(int x, int y, int z) {
		super(x, y, z, BPos::new);
	}

	public BPos toChunkCorner() {
		return new BPos(this.getX() & -16, this.getY(), this.getZ() & -16);
	}

	public CPos toChunkPos() {
		return new CPos(this.getX() >> 4, this.getZ() >> 4);
	}

}
