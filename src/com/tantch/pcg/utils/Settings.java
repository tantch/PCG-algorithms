package com.tantch.pcg.utils;

public class Settings {

	public static boolean ROOMSIMPLECONNECTIONS = true;
	// map generation settings
	public static int MAPSIZE = 50;
	public static int MINPARTITIONSIZE = 7;
	public static int MAXPARTITIONSIZE = 15;
	public static int MINROOMSIZE = 5;
	public static int CONNECT_ONLY_TO_MIDDLE_ROOM = 0;
	// agent settions
	public static int AGENT_TURNPROB = 10;

	// es
	public static int EA_ITERATIONS = 140;
	public static int EA_MUTATION_PROB = 10;
	// player and monster
	public static int PLAYER_ATRIBUTE_FOCUS = 1;
	public static int MONSTER_ATRIBUTE_FOCUS = 1;

	// ambient
	public static float[] RGB = { 1f, 1f, 1f };
	public static int PLAYER_SPEED_MULTIPLIER = 1;
	public static int PLAYER_LIFE_MULTIPLIER = 1;
	public static int PLAYER_ATTACK_MULTIPLIER = 1;
	public static int PLAYER_ARMOR_MULTIPLIER = 1;
	public static int PLAYER_LUCK_MULTIPLIER = 1;
	public static int PLAYER_ATKSPEED_MULTIPLIER = 1;
	

	public static float LEVEL_AGR_VALUE = 1f;
	public static float LEVEL_CONFUSE_VALUE = 1f;
	public static float LEVEL_FUN_VALUE = 1f;
	public static float LEVEL_TRICK_VALUE = 1f;
	public static float LEVEL_CALM_VALUE = 1f;
	public static float LEVEL_POSITIVE_VALUE = 1f;

}
