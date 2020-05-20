package kaptainwutax.seedutils.util;

import kaptainwutax.seedutils.mc.MCVersion;

public class UnsupportedMCVersion extends RuntimeException {

	public UnsupportedMCVersion(MCVersion version, String type) {
		super("Minecraft " + version + " does not support " + type);
	}

}
