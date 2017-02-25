package com.tantch.pcg.utils;

public class Debug {

	private static boolean verbose = true;

	public static void log(String type, String msg) {
		if (verbose) {
			System.out.println(type + " | " + msg);
		}
	}

	public static void setVerbose(boolean b) {
		verbose = b;
	}

	public static void logError(String type, String msg) {
		System.out.println("Error:" + type + " | " + msg);
		
	}

}
