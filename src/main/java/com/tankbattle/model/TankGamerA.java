package com.tankbattle.model;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Iterator;
import java.util.Set;

public class TankGamerA extends Tank implements Collidable{
    private int oldX,oldY;
    protected TankGamerA(int x, int y, double width, Image image, int speed) {
        super(x, y, width, image, speed);
        CollisionManager.collidables.add(this);
    }


    protected void move(Set<KeyCode> keysPressed, int sceneWid, int sceneHei) {
        oldX=x;oldY=y;
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

        x= (int) Math.max(this.width/2, Math.min(sceneWid-this.width/2, x));
        y=Math.max((int)this.height/2, Math.min((int)(sceneHei-this.height/2),y));
    }


    private int decideDir(Set<KeyCode> keysPressed) {
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





    public void update(long now,Set<KeyCode> keysPressed,int sceneWid,int sceneHei){
       if(this.HP.get()<=0){
           this.x=-10000;
           this.y=-10000;//HERE 具体值需调试，目的是把tank移到enemy检测不到的地方
           return;
       }
       move(keysPressed,sceneWid,sceneHei);

       Bullet.decideAndFireA(keysPressed,now,this,bullets);
       bullets.forEach(Bullet::move);

       //remove bullets
        Iterator<Bullet> iterator=bullets.iterator();
        while (iterator.hasNext()){
            Bullet bullet=iterator.next();
            if(bullet.isOffScreen(sceneWid,sceneHei)|| bullet.isDie()){
                iterator.remove();
                CollisionManager.collidables.remove(bullet);
            }

        }

    }

    @Override
    public Rectangle getBounds() {
        return this;
    }


    @Override
    public void onCollide(Collidable other) {
        if(other.getOwner()==this){
            return;
        }
        switch (other.getType()){
            case TANK :
            case WALL:
            case ENEMY:
                x=oldX;y=oldY;break;
            case BULLET:this.HP.set(this.HP.get()-Bullet.damage);break;
        }
    }

    @Override
    public CollisionType getType() {
        return CollisionType.TANK;
    }

    @Override
    public Collidable getOwner() {
        return null;
    }
}
