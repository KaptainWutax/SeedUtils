package kaptainwutax.seedutils.util;

import kaptainwutax.seedutils.mc.MCVersion;

public class UnsupportedVersion extends RuntimeException {

	public UnsupportedVersion(MCVersion version, String type) {
		super("Minecraft " + version + " does not support " + type);
	}

}
