package kaptainwutax.seedutils.mc;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum MCVersion {
	v1_16_4("1.16.4", 16, 4), //October 10, 2020
	v1_16_3("1.16.3", 16, 3), //September 9, 2020
	v1_16_2("1.16.2", 16, 2), //August 8, 2020
	v1_16_1("1.16.1", 16, 1), //June 6, 2020
	v1_16("1.16", 16, 0), //June 6, 2020

	v1_15_2("1.15.2", 15, 2), //January 1, 2020
	v1_15_1("1.15.1", 15, 1), //December 12, 2019
	v1_15("1.15", 15, 0), //December 12, 2019

	v1_14_4("1.14.4", 14, 4), //July 7, 2019
	v1_14_3("1.14.3", 14, 3), //June 6, 2019
	v1_14_2("1.14.2", 14, 2), //May 5, 2019
	v1_14_1("1.14.1", 14, 1), //May 5, 2019
	v1_14("1.14", 14, 0), //April 4, 2019

	v1_13_2("1.13.2", 13, 2), //October 10, 2018
	v1_13_1("1.13.1", 13, 1), //August 8, 2018
	v1_13("1.13", 13, 0), //July 7, 2018

	v1_12_2("1.12.2", 12, 2), //September 9, 2017
	v1_12_1("1.12.1", 12, 1), //August 8, 2017
	v1_12("1.12", 12, 0), //June 6, 2017

	v1_11_2("1.11.2", 11, 2), //December 12, 2016
	v1_11_1("1.11.1", 11, 1), //December 12, 2016
	v1_11("1.11", 11, 0), //November 11, 2016

	v1_10_2("1.10.2", 10, 2), //June 6, 2016
	v1_10_1("1.10.1", 10, 1), //June 6, 2016
	v1_10("1.10", 10, 0), //June 6, 2016

	v1_9_4("1.9.4", 9, 4), //May 5, 2016
	v1_9_3("1.9.3", 9, 3), //May 5, 2016
	v1_9_2("1.9.2", 9, 2), //March 3, 2016
	v1_9_1("1.9.1", 9, 1), //March 3, 2016
	v1_9("1.9", 9, 0), //February 2, 2016

	v1_8_9("1.8.9", 8, 9), //December 12, 2015
	v1_8_8("1.8.8", 8, 8), //July 7, 2015
	v1_8_7("1.8.7", 8, 7), //June 6, 2015
	v1_8_6("1.8.6", 8, 6), //May 5, 2015
	v1_8_5("1.8.5", 8, 5), //May 5, 2015
	v1_8_4("1.8.4", 8, 4), //April 4, 2015
	v1_8_3("1.8.3", 8, 3), //February 2, 2015
	v1_8_2("1.8.2", 8, 2), //February 2, 2015
	v1_8_1("1.8.1", 8, 1), //November 11, 2014
	v1_8("1.8", 8, 0), //September 9, 2014

	v1_7_10("1.7.10", 7, 10), //May 5, 2014
	v1_7_9("1.7.9", 7, 9), //April 4, 2014
	v1_7_8("1.7.8", 7, 8), //April 4, 2014
	v1_7_7("1.7.7", 7, 7), //April 4, 2014
	v1_7_6("1.7.6", 7, 6), //April 4, 2014
	v1_7_5("1.7.5", 7, 5), //February 2, 2014
	v1_7_4("1.7.4", 7, 4), //December 12, 2013
	v1_7_3("1.7.3", 7, 3), //December 12, 2013
	v1_7_2("1.7.2", 7, 2), //October 10, 2013

	v1_6_4("1.6.4", 6, 4), //September 9, 2013
	v1_6_2("1.6.2", 6, 2), //July 7, 2013
	v1_6_1("1.6.1", 6, 1), //June 6, 2013

	v1_5_2("1.5.2", 5, 2), //April 4, 2013
	v1_5_1("1.5.1", 5, 1), //March 3, 2013

	v1_4_7("1.4.7", 4, 7), //December 12, 2012
	v1_4_6("1.4.6", 4, 6), //December 12, 2012
	v1_4_5("1.4.5", 4, 5), //December 12, 2012
	v1_4_4("1.4.4", 4, 4), //December 12, 2012
	v1_4_2("1.4.2", 4, 2), //November 11, 2012

	v1_3_2("1.3.2", 3, 2), //August 8, 2012
	v1_3_1("1.3.1", 3, 1), //July 7, 2012

	v1_2_5("1.2.5", 2, 5), //March 3, 2012
	v1_2_4("1.2.4", 2, 4), //March 3, 2012
	v1_2_3("1.2.3", 2, 3), //March 3, 2012
	v1_2_2("1.2.2", 2, 2), //February 2, 2012
	v1_2_1("1.2.1", 2, 1), //February 2, 2012

	v1_1("1.1", 1, 0), //January 1, 2012

	v1_0("1.0", 0, 0), //November 11, 2011
	;

	private static Map<String, MCVersion> STRING_TO_VERSION = Arrays.stream(values())
			.collect(Collectors.toMap(MCVersion::toString, o -> o));

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
