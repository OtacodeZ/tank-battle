package com.tankbattle.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Set;

public class Tank {
    public int x,y,speed;
    public Image image;

    public int dir;//方向，逆时针数共1-8方向，默认为3 （向上）
    //缩放后尺寸
    public int imageWid;
    public double imageHei;


    public Tank(){
        this.dir=3;
    }
    public Tank(int x,int y,int speed,String TANK_IMG,int imageWid){
        this.x=x;
        this.y=y;
        this.speed=speed;
        this.image=new Image(TANK_IMG);
        this.imageWid=imageWid;
        this.dir=3;
    }




}
