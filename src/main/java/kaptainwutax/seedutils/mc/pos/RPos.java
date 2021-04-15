package kaptainwutax.seedutils.mc.pos;

import kaptainwutax.seedutils.util.math.Vec3i;

public class RPos extends Vec3i {

    private final int regionSize;

    public RPos(int x, int z, int regionSize) {
        super(x, 0, z);
        this.regionSize = regionSize;
    }

    public int getRegionSize() {
        return this.regionSize;
    }

    public BPos toBlockPos() {
        return new BPos(this.getX() * this.getRegionSize(), 0, this.getZ() * this.getRegionSize());
    }

    public CPos toChunkPos() {
        return new CPos(this.getX() * this.getRegionSize(), this.getZ() * this.getRegionSize());
    }

}
