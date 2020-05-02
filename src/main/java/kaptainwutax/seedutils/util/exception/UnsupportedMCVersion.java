package kaptainwutax.seedutils.util.exception;

import kaptainwutax.seedutils.util.MCVersion;

public class UnsupportedMCVersion extends RuntimeException {

	public UnsupportedMCVersion(String type, MCVersion version) {
		super("Minecraft " + version + " does not support " + type);
	}

}
