package com.tankbattle.model;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
}

