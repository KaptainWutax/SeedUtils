package kaptainwutax.seedutils.util.math;

public class Vec3i {

    public static final Vec3i ZERO = new Vec3i(0, 0, 0);
    private final int x, y, z;

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public double getMagnitude() {
        return this.distanceTo(ZERO, DistanceMetric.EUCLIDEAN);
    }

    public double getMagnitudeSq() {
        return this.distanceTo(ZERO, DistanceMetric.EUCLIDEAN_SQ);
    }

    public double distanceTo(Vec3i vec, DistanceMetric distance) {
        return distance.getDistance(this.getX() - vec.getX(), this.getY() - vec.getY(), this.getZ() - vec.getZ());
    }

    public Vec3i get2DMirrored() {
        return new Vec3i(this.z, this.y, this.x);
    }

    public Vec3i invert() {
        return new Vec3i(-this.x, -this.y, -this.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vec3i)) return false;
        Vec3i pos = (Vec3i) o;
        return this.getX() == pos.getX() &&
                this.getY() == pos.getY() &&
                this.getZ() == pos.getZ();
    }

    @Override
    public int hashCode() {
        return this.getZ() * 961 + this.getY() * 31 + this.getX();
    }

    @Override
    public String toString() {
        return "Pos{" + "x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
    }

}
