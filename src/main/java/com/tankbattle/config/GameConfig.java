package com.tankbattle.config;

public class GameConfig {


    //gamer
    public static int GAMER_HP_INIT =100;
    public static long GAMER_BULLET_COOLDOWN =100_000_000_0L;
    public static int GAMER_A_SPEED=5;
    public static int GAMER_B_SPEED=5;
    //enemy
    public static int ENEMY_HP_INIT=3;
    public static double SEE_DISTANCE=40000;
    public static long ENEMY_BULLET_COOLDOWN=500_000_000L;
    public static int ENEMY_MOST=3;//场上最多有几个敌人
    public static long ENEMY_SPAWN_INTERVAL=1_000_000_000L;
    public static int ENEMY_SPEED=1;
    //bullet
    public static double BULLET_SPEED=10;


}
