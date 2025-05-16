package com.tankbattle.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import resource.config.ImagePath;

import java.util.List;
import java.util.Random;

public class Enemy {
    public double x;
    public double y;
    public double speed=0.5;
    public IntegerProperty HP=new SimpleIntegerProperty(1);
    private String ENEMY_IMG= ImagePath.ENEMY_IMG;
    private Image image=new Image(ENEMY_IMG,true);
    public int imageWid=30;
    public double imageHei;
    public int dir=7;
    public long lastFireTimeE =0;

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
        gc.rotate((3- dir)*45);
        gc.drawImage(this.image, -(this.imageWid / 2.0), -(imageHei / 2),this.imageWid,imageHei);
        gc.restore();

    }

    public void move(){
        Random random=new Random();
        switch (1+random.nextInt(4)){
            case 1:x++;break;
            case 2:y--;break;
            case 3:x--;break;
            case 4:y++;break;
        }
    }

    private static long lastEnemySpawnTime=0;
    final private static long enemySpawnInterval=1_000_000_000;
    public static void decideAndSpawn(List<Enemy> enemies, long now){
        if (now - lastEnemySpawnTime > enemySpawnInterval) {
            double x = Math.random() * (800 - 40); // 屏幕宽度减去敌人宽度
            enemies.add(new Enemy(x, 0));
            lastEnemySpawnTime = now;
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
