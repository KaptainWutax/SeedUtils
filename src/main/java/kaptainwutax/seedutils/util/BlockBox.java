package kaptainwutax.seedutils.util;

import kaptainwutax.seedutils.util.math.Vec3i;

public class BlockBox {
	
	public int minX;
   	public int minY;
   	public int minZ;
   	public int maxX;
   	public int maxY;
   	public int maxZ;

   	public BlockBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
	   this.minX = minX;
      	this.minY = minY;
      	this.minZ = minZ;
      	this.maxX = maxX;
      	this.maxY = maxY;
      	this.maxZ = maxZ;
	}

	public BlockBox(Vec3i v1, Vec3i v2) {
		this.minX = Math.min(v1.getX(), v2.getX());
		this.minY = Math.min(v1.getY(), v2.getY());
		this.minZ = Math.min(v1.getZ(), v2.getZ());
		this.maxX = Math.max(v1.getX(), v2.getX());
		this.maxY = Math.max(v1.getY(), v2.getY());
		this.maxZ = Math.max(v1.getZ(), v2.getZ());
	}

	public static BlockBox empty() {
		return new BlockBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
	}

	public BlockBox offset(int x, int y, int z) {
   		return new BlockBox(
   				this.minX + x, this.minY + y, this.minZ + z,
			    this.maxX + x, this.maxY + y, this.maxZ + z
	    );
	}

	public static BlockBox rotated(int x, int y, int z, int offsetX, int offsetY, int offsetZ, int sizeX, int sizeY, int sizeZ, Direction facing) {
		switch(facing) {
			case NORTH:
				return new BlockBox(x + offsetX, y + offsetY, z - sizeZ + 1 + offsetZ, x + sizeX - 1 + offsetX, y + sizeY - 1 + offsetY, z + offsetZ);
			case WEST:
				return new BlockBox(x - sizeZ + 1 + offsetZ, y + offsetY, z + offsetX, x + offsetZ, y + sizeY - 1 + offsetY, z + sizeX - 1 + offsetX);
			case EAST:
				return new BlockBox(x + offsetZ, y + offsetY, z + offsetX, x + sizeZ - 1 + offsetZ, y + sizeY - 1 + offsetY, z + sizeX - 1 + offsetX);
			default:
				return new BlockBox(x + offsetX, y + offsetY, z + offsetZ, x + sizeX - 1 + offsetX, y + sizeY - 1 + offsetY, z + sizeZ - 1 + offsetZ);
		}
	}

	public boolean intersects(BlockBox box) {
	   return this.maxX >= box.minX && this.minX <= box.maxX && this.maxZ >= box.minZ && this.minZ <= box.maxZ && this.maxY >= box.minY && this.minY <= box.maxY;
	}

	public boolean intersectsXZ(int minX, int minZ, int maxX, int maxZ) {
		return this.maxX >= minX && this.minX <= maxX && this.maxZ >= minZ && this.minZ <= maxZ;
	}

	public boolean contains(Vec3i v) {
		return v.getX() >= this.minX && v.getX() <= this.maxX && v.getZ() >= this.minZ && v.getZ() <= this.maxZ && v.getY() >= this.minY && v.getY() <= this.maxY;
	}

	public Vec3i getDimensions() {
		return new Vec3i(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
	}

	public void encompass(BlockBox box) {
		this.minX = Math.min(this.minX, box.minX);
		this.minY = Math.min(this.minY, box.minY);
		this.minZ = Math.min(this.minZ, box.minZ);
		this.maxX = Math.max(this.maxX, box.maxX);
		this.maxY = Math.max(this.maxY, box.maxY);
		this.maxZ = Math.max(this.maxZ, box.maxZ);
   }

}
