package kaptainwutax.seedutils.util;

import kaptainwutax.seedutils.lcg.rand.JRand;
import kaptainwutax.seedutils.util.math.Vec3i;

public enum Direction {
	
    DOWN(Axis.Y, new Vec3i(0, -1, 0)),
    UP(Axis.Y, new Vec3i(0, 1, 0)),
    NORTH(Axis.Z, new Vec3i(0, 0, -1)),
    SOUTH(Axis.Z, new Vec3i(0, 0, 1)),
    WEST(Axis.X, new Vec3i(-1, 0, 0)),
    EAST(Axis.X, new Vec3i(1, 0, 0));
	
	private static Direction[] HORIZONTALS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
	private static Direction[] BY_2D_DATA = {Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST};
	private Axis axis;
	private Vec3i vec;

	Direction(Axis axis, Vec3i vec) {
		this.axis = axis;
		this.vec = vec;
	}	
	
	public Axis getAxis() {
		return this.axis;
	}

	public Vec3i getVector() {
		return this.vec;
	}

	public static Direction randomHorizontal(JRand rand) {
		return HORIZONTALS[rand.nextInt(HORIZONTALS.length)];
	}

	public static Direction random2D(JRand rand) {
        	return BY_2D_DATA[rand.nextInt(BY_2D_DATA.length)];
	}

	public static Direction[] getHorizontal(){
		return HORIZONTALS;
	}

	public static Direction[] get2d(){
		return BY_2D_DATA;
	}
	
	public enum Axis {
		X, Y, Z
	}
	
}
