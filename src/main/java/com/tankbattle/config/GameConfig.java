package com.tankbattle.config;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GameConfig {


    //gamer

    public static IntegerProperty GAMER_HP_INIT =new SimpleIntegerProperty(100);
    public static long GAMER_BULLET_COOLDOWN =100_000_000_0L;
    public static IntegerProperty GAMER_SPEED =new SimpleIntegerProperty(10) ;
    //enemy
    public static int ENEMY_HP_INIT=3;
    public static double SEE_DISTANCE=40000;
    public static long ENEMY_BULLET_COOLDOWN=500_000_000L;
    public static int ENEMY_MOST=3;//场上最多有几个敌人
    public static long ENEMY_SPAWN_INTERVAL=1_000_000_000L;
    public static IntegerProperty ENEMY_SPEED=new SimpleIntegerProperty(1);
    //bullet
    public static double BULLET_SPEED=10;


}
