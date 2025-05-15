package com.tankbattle.model;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Set;

public class Bullet {
    private double x;
    private double y;
    private final double speed = 7;
    public int tankDir;


    public Bullet(double startX, double startY,int tankDir) {
        this.x = startX;
        this.y = startY;
        this.tankDir=tankDir;
    }

    public void move() {
        switch (tankDir){
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
        gc.setFill(Color.RED);
        gc.fillOval(x, y, 6, 10);  // 使用椭圆形代表子弹
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

