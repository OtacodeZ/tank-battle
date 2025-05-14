package com.tankbattle.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.Set;

public class TankGamerA extends Tank{
    public TankGamerA(int x,int y,int speed,String TANK_IMG,int imageWid){
        super(x,y,speed,TANK_IMG,imageWid);
    }
    public void draw(GraphicsContext gc, int inputX, int inputY){
        //原始尺寸
        double imgWid=this.image.getWidth();
        double imgHei=this.image.getHeight();

        //缩放后尺寸
        double imageHei=this.imageWid*imgHei/imgWid;

        gc.save();
        gc.translate((x + this.imageWid / 2.0), y +imageHei / 2 );
        gc.rotate((3-this.dir)*45);
        gc.drawImage(this.image, -(this.imageWid / 2.0), -(imageHei / 2),this.imageWid,imageHei);
        gc.restore();

        //遗留：没有转向时：gc.drawImage(this.image,inputX,inputY,this.imageWid,this.imageWid*imgHei/imgWid);
    }

    public void move(Set<KeyCode> keysPressed, int sceneWid, int sceneHei){
        if (keysPressed.contains(KeyCode.UP)) {
            y -= speed;  // 上移
        }
        if (keysPressed.contains(KeyCode.DOWN)) {
            y += speed;  // 下移
        }
        if (keysPressed.contains(KeyCode.LEFT)) {
            x -= speed;  // 左移
        }
        if (keysPressed.contains(KeyCode.RIGHT)) {
            x += speed;  // 右移
        }

        this.dir=decideDir(keysPressed);


        double imgWid=this.image.getWidth();
        double imgHei=this.image.getHeight();
        x=Math.max(0, Math.min(sceneWid-this.imageWid, x));
        y=Math.max(0, Math.min((int)(sceneHei-this.imageWid*imgHei/imgWid),y));
    }

    protected int decideDir(Set<KeyCode> keysPressed){

        int dir=this.dir;
        if(keysPressed.contains(KeyCode.UP)){
            dir=3;
            if(keysPressed.contains(KeyCode.RIGHT)){
                dir=2;
            }else {
                if(keysPressed.contains(KeyCode.LEFT)){
                    dir=4;
                }else{
                    dir=3;
                }
            }
        }else {
            if (keysPressed.contains(KeyCode.DOWN)){
                dir=7;
                if(keysPressed.contains(KeyCode.RIGHT)){
                    dir=8;
                }else {
                    if(keysPressed.contains(KeyCode.LEFT)){
                        dir=6;
                    }else{
                        dir=7;
                    }
                }
            }else {
                if(keysPressed.contains(KeyCode.RIGHT)){
                    dir=1;
                }else {
                    if(keysPressed.contains(KeyCode.LEFT)){
                        dir=5;
                    }
                }
            }
        }
        return dir;
    }
}
