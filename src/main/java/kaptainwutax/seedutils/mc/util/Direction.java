package kaptainwutax.seedutils.mc.util;

import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.util.math.Vec3i;

import java.util.Arrays;
import java.util.List;

public enum Direction {

    DOWN(Axis.Y, new Vec3i(0, -1, 0)),
    UP(Axis.Y, new Vec3i(0, 1, 0)),
    NORTH(Axis.Z, new Vec3i(0, 0, -1)), // NONE
    SOUTH(Axis.Z, new Vec3i(0, 0, 1)), // CLOCKWISE_180
    WEST(Axis.X, new Vec3i(-1, 0, 0)), // COUNTERCLOCKWISE_90
    EAST(Axis.X, new Vec3i(1, 0, 0)); // CLOCKWISE_90

    private static final Direction[] HORIZONTALS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
    private final Axis axis;
    private final Vec3i vec;

    Direction(Axis axis, Vec3i vec) {
        this.axis = axis;
        this.vec = vec;
    }

    public Direction getClockWise() {
		return getDirection(EAST, WEST, NORTH, SOUTH);
    }

    public Direction getCounterClockWise() {
		return getDirection(WEST, EAST, SOUTH, NORTH);
	}

    public Direction getOpposite() {
		return getDirection(SOUTH, NORTH, EAST, WEST);
	}

	private Direction getDirection(Direction dir1, Direction dir2, Direction dir3, Direction dir4) {
		switch (this) {
			case NORTH:
				return dir1;
			case SOUTH:
				return dir2;
			case WEST:
				return dir3;
			case EAST:
				return dir4;
			default:
				throw new IllegalStateException("Unable to get facing of " + this);
		}
	}

	public Axis getAxis() {
        return this.axis;
    }

    public Vec3i getVector() {
        return this.vec;
    }

    public Rotation getRotation(){
        switch (this) {
            case NORTH:
                return Rotation.NONE;
            case SOUTH:
                return Rotation.CLOCKWISE_180;
            case WEST:
                return Rotation.COUNTERCLOCKWISE_90;
            case EAST:
                return Rotation.CLOCKWISE_90;
            default:
                throw new IllegalStateException("Unable to get direction of " + this);
        }
    }

    public static Direction randomHorizontal(JRand rand) {
        return HORIZONTALS[rand.nextInt(HORIZONTALS.length)];
    }

    public static Direction getRandom(JRand rand) {
        return values()[rand.nextInt(values().length)];
    }

    public static List<Direction> getShuffled(JRand rand) {
        List<Direction> list = Arrays.asList(values());
        JRand.shuffle(list, rand);
        return list;
    }

    public enum Axis {
        X, Y, Z;

        public Axis get2DRotated() {
            switch (this) {
                case X:
                    return Z;
                case Z:
                    return X;
                default:
                    return Y;
            }
        }

    }

    @Override
    public String toString() {
        return "Direction{" +
                "axis=" + axis +
                ", vec=" + vec +
                '}';
    }
}
