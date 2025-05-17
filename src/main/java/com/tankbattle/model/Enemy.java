package com.tankbattle.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import resource.config.ImagePath;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Enemy {
    public int x;
    public int y;
    public int speed=1;
    public IntegerProperty HP=new SimpleIntegerProperty(1);
    private String ENEMY_IMG= ImagePath.ENEMY_IMG;
    private Image image=new Image(ENEMY_IMG,true);
    public int imageWid=30;
    public double imageHei;
    public Set<String> dirSet=new HashSet<>();
    public int dir=7;
    public long lastFireTimeE =0;

    private int enemySeetank=0;

    private static double seeDistance=40000;//视野范围
    private String ifOnpenViewCycle="on";//是否可视化enemy的索敌圈，若是，改为“on”

    public Enemy(int x,int y ){
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

        if(ifOnpenViewCycle.equalsIgnoreCase("on")){
            gc.setLineDashes(10.0, 10.0);
            gc.setStroke(Color.RED);
            gc.setLineWidth(1);
            gc.strokeOval(-200, -200, 400, 400);
        }


        gc.restore();

    }

    private void whenSeeTank(Tank tank){
        if(tank.x>this.x&&tank.y<this.y){
            this.dir=2;
        }else if(tank.x<this.x&&tank.y<this.y){
            this.dir=4;
        }else if(tank.x<this.x&&tank.y>this.y){
            this.dir=6;
        }else if(tank.x>this.x&&tank.y>this.y){
            this.dir=8;
        }else if(tank.x==this.x&&tank.y<this.y){
            this.dir=3;
        }else if(tank.x==this.x&&tank.y>this.y){
            this.dir=7;
        }else if(tank.x<this.x&&tank.y==this.y){
            this.dir=5;
        }else if(tank.x>this.x&&tank.y==this.y){
            this.dir=1;
        }
    }
    public void move(Tank tank1,Tank tank2){
        if(enemySeetank==1){
            whenSeeTank(tank1);
        }else if(enemySeetank==2){
            whenSeeTank(tank2);
        }
        switch (this.dir){
            case 1:x+=speed;break;
            case 2:x+=speed;y-=speed;break;
            case 3:y-=speed;break;
            case 4:y-=speed;x-=speed;break;
            case 5:x-=speed;break;
            case 6:x-=speed;y+=speed;break;
            case 7:y+=speed;break;
            case 8:y+=speed;x+=speed;break;
            default:break;
        }
    }

    public void changeDir(Tank tank1,Tank tank2){

        if( (Math.pow(tank1.x-this.x,2)
                +
                Math.pow(tank1.y-this.y,2))<seeDistance){
            enemySeetank=1;

        }else if( (Math.pow(tank2.x-this.x,2)
                +
                Math.pow(tank2.y-this.y,2) )<seeDistance){
            enemySeetank=2;

        } else {
            enemySeetank=0;
            Random random=new Random();
            this.dir=1+random.nextInt(4);
        }
    }

    private static long lastEnemySpawnTime=0;
    final private static long enemySpawnInterval=1_000_000_000;
    public static void decideAndSpawn(List<Enemy> enemies, long now){
        if (now - lastEnemySpawnTime > enemySpawnInterval) {
            int x = (int)Math.random() * (800 - 40); // 屏幕宽度减去敌人宽度
            enemies.add(new Enemy(x, 100));
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
