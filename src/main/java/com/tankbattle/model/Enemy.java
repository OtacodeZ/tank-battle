package com.tankbattle.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import resource.config.ImagePath;

import java.util.List;

public class Enemy {
    public double x;
    public double y;
    public double speed=0.5;
    public IntegerProperty HP=new SimpleIntegerProperty(1);
    private String ENEMY_IMG= ImagePath.ENEMY_IMG;
    private Image image=new Image(ENEMY_IMG,true);
    public int imageWid=30;
    public double imageHei;
    private int enemyDir=7;

    public Enemy(double x,double y ){
        this.x=x;
        this.y=y;
    }

    public void draw(GraphicsContext gc) {
        //原始尺寸
        double imgWid=this.image.getWidth();
        double imgHei=this.image.getHeight();
        //缩放后尺寸
        this.imageHei=this.imageWid*imgHei/imgWid;
        gc.save();
        gc.translate(x,y);
        gc.rotate((3- enemyDir)*45);
        gc.drawImage(this.image, -(this.imageWid / 2.0), -(imageHei / 2),this.imageWid,imageHei);
        gc.restore();

    }

    public void move(){
            this.y++;
    }

    public static int temp=0;
    public static void decideAndHenerate(List<Enemy> enemies){
        if(temp<2){
            double enemyX=30;
            double enemyY=20;
            enemies.add(new Enemy(enemyX,enemyY));
            temp++;
        }


    }
    public boolean ifLive(){
        if(
                this.HP.get()<=0
        ){
            return true;
        }else {
            return false;
        }
    }
    public boolean intersects(Bullet rec){
        double a1=this.x-imageWid/2,
                b1=this.x+imageWid/2,
                c1=this.y-imageHei/2,
                d1=this.y+imageHei/2;
        double a2=rec.x-rec.imageWid/2,
                b2=rec.x+rec.imageWid/2,
                c2=rec.y-rec.imageHei/2,
                d2=rec.y+rec.imageHei/2;
        if (Math.max(a1,a2)<=Math.min(b1,b2)&&
                Math.max(c1,c2)<=Math.min(d1,d2)) {
            return true;
        }else {
            return false;
        }
    }
}
