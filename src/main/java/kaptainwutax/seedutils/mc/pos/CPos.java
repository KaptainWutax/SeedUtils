package kaptainwutax.seedutils.mc.pos;

import kaptainwutax.seedutils.util.math.Vec3i;

public class CPos extends Vec3i {

    public CPos(int x, int z) {
        super(x, 0, z);
    }

    public CPos(Vec3i vec3i) {
        super(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }


    public CPos add(CPos pos) {
        return this.add(pos.getX(), pos.getZ());
    }

    public CPos subtract(CPos pos) {
        return this.subtract(pos.getX(), pos.getZ());
    }

    public CPos shl(int amount) {
        return this.shl(amount, amount);
    }

    public CPos shr(int amount) {
        return this.shr(amount, amount);
    }

    public CPos add(int x, int z) {
        return new CPos(this.getX() + x, this.getZ() + z);
    }

    public CPos subtract(int x, int z) {
        return new CPos(this.getX() - x, this.getZ() - z);
    }

    public CPos shl(int bx, int bz) {
        return new CPos(this.getX() << bx, this.getZ() << bz);
    }

    public CPos shr(int bx, int bz) {
        return new CPos(this.getX() >> bx, this.getZ() >> bz);
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
