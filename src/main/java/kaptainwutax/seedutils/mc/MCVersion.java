package kaptainwutax.seedutils.mc;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum MCVersion {

	v1_16_1("1.16.1", 16, 1), //June 24, 2020
	v1_16("1.16",16, 0), //June 23, 2020

	v1_15_2("1.15.2",15, 2), //January 21, 2020
	v1_15_1("1.15.1",15, 1), //December 17, 2019
	v1_15("1.15",15, 0), //December 10, 2019

	v1_14_4("1.14.4", 14, 4), //July 19, 2019
	v1_14_3("1.14.3", 14, 3), //June 24, 2019
	v1_14_2("1.14.2", 14, 2), //May 27, 2019
	v1_14_1("1.14.1", 14, 1), //May 13, 2019
	v1_14("1.14", 14, 0), //April 23, 2019

	v1_13_2("1.13.2", 13, 2), //October 22, 2018
	v1_13_1("1.13.1", 13, 1), //August 22, 2018
	v1_13("1.13", 13, 0), //July 18, 2018

	v1_12_2("1.12.2", 12, 2), //September 18, 2017
	v1_12_1("1.12.1", 12, 1), //August 3, 2017
	v1_12("1.12", 12, 0), //June 7, 2017

	v1_11_2("1.11.2", 11, 2), //December 21, 2016
	v1_11_1("1.11.1", 11, 1), //December 20, 2016
	v1_11("1.11", 11, 0), //November 14, 2016

	v1_10_2("1.10.2", 10, 2), //June 23, 2016
	v1_10_1("1.10.1", 10, 1), //June 22, 2016
	v1_10("1.10", 10, 0), //June 8, 2016

	v1_9_4("1.9.4", 9, 4), //May 10, 2016
	v1_9_3("1.9.3", 9, 3), //May 10, 2016
	v1_9_2("1.9.2", 9, 2), //March 30, 2016
	v1_9_1("1.9.1", 9, 1), //March 30, 2016
	v1_9("1.9", 9, 0), //February 29, 2016

	v1_8_9("1.8.9", 8, 9), //December 9, 2015
	v1_8_8("1.8.8", 8, 8), //July 27, 2015
	v1_8_7("1.8.7", 8, 7), //June 5, 2015
	v1_8_6("1.8.6", 8, 6), //May 25, 2015
	v1_8_5("1.8.5", 8, 5), //May 22, 2015
	v1_8_4("1.8.4", 8, 4), //April 17, 2015
	v1_8_3("1.8.3", 8, 3), //February 20, 2015
	v1_8_2("1.8.2", 8, 2), //February 19, 2015
	v1_8_1("1.8.1", 8, 1), //November 24, 2014
	v1_8("1.8", 8, 0); //September 2, 2014

	private static Map<String, MCVersion> STRING_TO_VERSION = Arrays.stream(values()).collect(Collectors.toMap(MCVersion::toString, o -> o));

	public final String name;
	public final int release;
	public final int subVersion;

	MCVersion(String name, int release, int subVersion) {
		this.name = name;
		this.release = release;
		this.subVersion = subVersion;
	}

	public int getRelease() {
		return this.release;
	}

	public int getSubVersion() {
		return this.subVersion;
	}

	public static MCVersion fromString(String name) {
		return STRING_TO_VERSION.get(name);
	}

	public boolean isNewerThan(MCVersion v) {
		return this.compareTo(v) < 0;
	}

	public boolean isNewerOrEqualTo(MCVersion v) {
		return this.compareTo(v) <= 0;
	}

	public boolean isOlderThan(MCVersion v) {
		return this.compareTo(v) > 0;
	}

	public boolean isOlderOrEqualTo(MCVersion v) {
		return this.compareTo(v) >= 0;
	}

	public boolean isBetween(MCVersion a, MCVersion b) {
		return this.compareTo(a) <= 0 && this.compareTo(b) >= 0;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
