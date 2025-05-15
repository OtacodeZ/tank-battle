package com.tankbattle.model;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import resource.config.ImagePath;

import java.util.List;
import java.util.Set;

public class Bullet {
    public double x;
    public double y;
    private final double speed = 10;
    public int bulletDir;
    private String BULLET_IMG= ImagePath.BULLET_IMG;
    private Image image=new Image(BULLET_IMG,true);
    public int imageWid=10;
    public double imageHei;
    public int damage=1;



    public Bullet(double startX, double startY,int tankDir) {
        this.x = startX;
        this.y = startY;
        this.bulletDir =tankDir;
    }

    public void move() {
        switch (bulletDir){
            case 1:x+=speed;break;
            case 2:x+=speed/1.4;y-=speed/1.4;break;
            case 3:y-=speed;break;
            case 4:y-=speed/1.4;x-=speed/1.4;break;
            case 5:x-=speed;break;
            case 6:x-=speed/1.4;y+=speed/1.4;break;
            case 7:y+=speed;break;
            case 8:x+=speed/1.4;y+=speed/1.4;
            default:break;
        }
    }

    public void draw(GraphicsContext gc) {
        //原始尺寸
        double imgWid=this.image.getWidth();
        double imgHei=this.image.getHeight();
        //缩放后尺寸
        this.imageHei=this.imageWid*imgHei/imgWid;
        gc.save();
        gc.translate(x,y);
        gc.rotate((3- bulletDir)*45);
        gc.drawImage(this.image, -(this.imageWid / 2.0), -(imageHei / 2),this.imageWid,imageHei);
        gc.restore();

    }

    public boolean isOffScreen(int screenX,int screenY) {
        if( x<0||
            y<0||
            x>screenX||
            y>screenY){

            return true;
        }else {
            return false;
        }

    }
    
    static private long lastFireTime = 0; // 纳秒时间戳
    final private static long fireCooldown = 100_000_000; // 100ms 冷却，单位是纳秒（ns）

    static public void decideAndFireA(Set<KeyCode> keysPressed, long now, Tank tank, List<Bullet> bullets){
        if (keysPressed.contains(KeyCode.SPACE)) {

            if (now - lastFireTime >= fireCooldown) {

                double bulletX = tank.x ;
                double bulletY = tank.y ;
                bullets.add(new Bullet(bulletX, bulletY,tank.dir));// 发射子弹
                lastFireTime = now;       // 记录本次时间
            }
        }
    }
    static public void decideAndFireB(Set<KeyCode> keysPressed, long now, Tank tank, List<Bullet> bullets){
        if (keysPressed.contains(KeyCode.E)) {

            if (now - lastFireTime >= fireCooldown) {

                double bulletX = tank.x ;
                double bulletY = tank.y ;
                bullets.add(new Bullet(bulletX, bulletY,tank.dir));// 发射子弹
                lastFireTime = now;       // 记录本次时间
            }
        }
    }
}

