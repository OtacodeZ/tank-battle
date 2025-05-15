package com.tankbattle.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Set;

public  abstract class Tank {
    public int x,y,speed;
    public Image image;

    public int dir;//方向，逆时针数共1-8方向，默认为3 （向上）
    //缩放后尺寸
    public int imageWid;
    public double imageHei;
    public IntegerProperty HP = new SimpleIntegerProperty(100);


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

    public void draw(GraphicsContext gc){
        //原始尺寸
        double imgWid=this.image.getWidth();
        double imgHei=this.image.getHeight();

        //缩放后尺寸
        this.imageHei=this.imageWid*imgHei/imgWid;

        gc.save();
        gc.translate((x + this.imageWid / 2.0), y +imageHei / 2 );
        gc.rotate((3-this.dir)*45);
        gc.drawImage(this.image, -(this.imageWid / 2.0), -(imageHei / 2),this.imageWid,imageHei);
        gc.restore();

        //遗留：没有转向时：gc.drawImage(this.image,inputX,inputY,this.imageWid,this.imageWid*imgHei/imgWid);
    }
    public abstract void move(Set<KeyCode> keysPressed, int sceneWid, int sceneHei);
    protected abstract int decideDir(Set<KeyCode> keysPressed);



}
