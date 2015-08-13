package com.la4domain.games.biplanes;

public class Const {

    //GLOBAL CONSTANTS
    public final static int TICKS_PER_SECOND = 60;
    public final static int MAX_FRAME_SKIPS = 5;
    public final static int FRAME_PERIOD = 1000 / TICKS_PER_SECOND;

    //WORLD CONSTANTS
    public static final int W_HEIGHT_COEFF = 2;
    public static final int W_WIDTH_COEFF = 2;

    //BIPLANE CONSTANTS
    public static final int B_TILE_AMOUNT = 16;
    public static final int B_COLUMN_NUM = 4;
    public static final int B_FLYING_ANIMATION_SPEED = 1;
    public static final int B_DESTROYING_ANIMATION_SPEED = 6;
    public static final int B_SHOOTING_TIMER = 10;
    public static final float B_TILT_DEGREE = 2.5F;
    public static final int B_FLYING_SPEED = 10;
    public static final int B_FALLING_SPEED = 9;
    public static final int B_HEIGHT = 42; //When using must multiply by density constant
    public static final int B_WIDTH = 90; //When using must multiply by density constant
    public static final int B_HP = 5;

    //GROUND CONSTANTS
    public static final int G_HEIGHT = 42; //When using must multiply by density constant
    public static final int G_WIDTH = 256; //When using must multiply by density constant
    public static final int G_TILE_AMOUNT = 1;
    public static final int G_COLUMN_NUM = 1;

    //BULLETS CONSTANTS
    public static final int BUL_CYCLES = 100;
    public static final float BUL_FADE = 0.7F;
    public static final int BUL_LENGTH = 21;
    public static final int BUL_THICKNESS = 7;
    public static final int BUL_SPEED = 26;
    public static final float BUL_MAX_RAND_ANGLE = 1.4F;

    //TANK CONSTANTS
    public static final int T_TANKS_AMOUNT = 2;
    public static final int T_TILE_AMOUNT = 20;
    public static final int T_COLUMN_NUM = 5;
    public static final int T_HEIGHT = 60; //When using must multiply by density constant
    public static final int T_WIDTH = 74; //When using must multiply by density constant
    public static final int T_DESTROYING_ANIMATION_SPEED = 5;
    public static final int T_TILT_STATES = 7;
    public static final int T_SHOOTING_TIMER = 100;
    public static final int T_TILT_TIMER = 25;
    public static final int T_HP = 3;
    public static final int T_DEATH_TIMER = 700;

    //CLOUD CONSTANTS
    public static final int C_AMOUNT = 9;
    public static final float C_MIN_SPEED = 2F;
    public static final float C_MAX_SPEED = 4F;
    public static final int C_SPRITES = 4;
    public static final float C_MIN_ALTITUDE = 300F;

    //AI CONSTANTS
    public static final int AI_MIN_HEIGHT = 500;
    public static final int AI_OTHER_DEST = 700;
    public static final int AI_DEATH_TIMER = 700;
    public static final int AI_MAX_KEEPING_DIR = 600;
    public static final int AI_AMOUNT = 3;
    public static final int AI_SHOOTING_FREQUENCY = 60;

    //HEALTH BAR CONSTANTS
    public static final int HP_WIDTH = 208;
    public static final int HP_HEIGHT = 40;
    public static final int HP_COLUMN_NUM = 1;
    public static final int HP_TILE_AMOUNT = 6;

}

/*context.getResources().getDisplayMetrics().density - density constant*/