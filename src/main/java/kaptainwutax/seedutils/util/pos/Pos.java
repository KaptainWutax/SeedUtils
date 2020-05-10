package kaptainwutax.seedutils.util.pos;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class Pos {

	private static Factory<Pos> DEFAULT = Pos::new;

	private final int x, y, z;
	private final Factory<? extends Pos> factory;

	protected Pos(int x, int y, int z, Factory<? extends Pos> factory) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.factory = factory;
	}

	public Pos(int x, int y, int z) {
		this(x, y, z, DEFAULT);
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

	public <T extends Pos> T add(Pos pos) {
		return (T)this.factory.create(this.getX() + pos.getX(), this.getY() + pos.getY(), this.getZ() + pos.getZ());
	}

	public <T extends Pos> T subtract(Pos pos) {
		return (T)this.factory.create(this.getX() - pos.getX(), this.getY() - pos.getY(), this.getZ() - pos.getZ());
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)return true;
		if(!(o instanceof Pos))return false;
		Pos pos = (Pos) o;
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

	@FunctionalInterface
	public interface Factory<T extends Pos> {
		T create(int x, int y, int z);
	}

}
