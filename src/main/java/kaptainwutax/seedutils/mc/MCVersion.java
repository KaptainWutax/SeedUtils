package kaptainwutax.seedutils.mc;

public enum MCVersion {

	v1_15("1.15",15, 0, 0),
	v1_14("1.14", 14, 0, 0),
	v1_13("1.13", 13, 0, 0),
	v1_12("1.12", 12, 0, 0),
	v1_11("1.11", 11, 0, 0),
	v1_10("1.10", 10, 0, 0),
	v1_9("1.9", 9, 0, 0),
	v1_8("1.8", 8, 0, 0),
	v_1_7("1.7", 7, 0, 0);

	public final String name;
	public final int release;
	public final int subVersion;
	public final int snapshot;

	MCVersion(String name, int release, int subVersion, int snapshot) {
		this.name = name;
		this.release = release;
		this.subVersion = subVersion;
		this.snapshot = snapshot;
	}

	public boolean isNewerThan(MCVersion v) {
		return this.compareTo(v) < 0;
	}

	public boolean isOlderThan(MCVersion v) {
		return this.compareTo(v) > 0;
	}

	public boolean isBetween(MCVersion a, MCVersion b) {
		return this.compareTo(a) <= 0 && this.compareTo(b) >= 0;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
