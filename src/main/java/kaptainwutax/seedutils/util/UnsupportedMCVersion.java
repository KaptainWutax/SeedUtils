package kaptainwutax.seedutils.util;

import kaptainwutax.seedutils.mc.MCVersion;

public class UnsupportedMCVersion extends RuntimeException {

	public UnsupportedMCVersion(String type, MCVersion version) {
		super("Minecraft " + version + " does not support " + type);
	}

}
