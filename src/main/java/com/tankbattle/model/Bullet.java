package com.tankbattle.model;

import com.tankbattle.app.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import resource.config.ImageManger;
import javafx.stage.Stage;
import java.util.List;
import java.util.Set;

public class Bullet extends Rectangle implements Collidable{
    private final double speed = 10;
    public int dir;
    private Image image;
    public static final int damage=1;

    private Collidable owner;
    @Override
    public Collidable getOwner(){
        return owner;
    }


    public Bullet(int x, int y,Image image,int dir,Collidable owner) {
        super(x, y, 10, 10*image.getHeight()/image.getWidth());
        this.dir=dir;
        this.image=image;
        this.owner=owner;

        CollisionManager.collidables.add(this);
    }
    public void move() {
        switch (dir){
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
    public void draw(GraphicsContext gc,Stage stage) {
        int windowWid = (int) stage.getWidth(); // 获取当前宽度
        int windowHei = (int) stage.getHeight(); // 获取当前高度
        double scaleX = windowWid / (double) Main.sceneWid;
        double scaleY = windowHei / (double) Main.sceneHei;
        gc.save();
        gc.scale(scaleX,scaleY);
        //原始尺寸
        double imgWid=this.image.getWidth();
        double imgHei=this.image.getHeight();
        //缩放后尺寸
        this.height=this.width*imgHei/imgWid;

        gc.translate(x,y);
        gc.rotate((3- dir)*45);
        gc.drawImage(this.image, -(this.width / 2.0), -(height / 2),this.width,height);
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


    final private static long fireCooldown = 100_000_000;; // 100ms 冷却，单位是纳秒（ns）
    static public void decideAndFireA(Set<KeyCode> keysPressed, long now, TankGamerA tank, List<Bullet> bullets){
        if (keysPressed.contains(KeyCode.SPACE)) {
            if (now - tank.lastFireTime >= fireCooldown) {
                bullets.add(new Bullet(tank.getX(), tank.getY(),ImageManger.bullet,tank.dir,tank));// 发射子弹
                tank.lastFireTime = now;       // 记录本次时间
                System.out.println("fire");
            }
        }
    }
    static public void decideAndFireB(Set<KeyCode> keysPressed, long now, TankGamerB tank, List<Bullet> bullets){
        if (keysPressed.contains(KeyCode.E)) {
            if (now - tank.lastFireTime >= fireCooldown) {
                bullets.add(new Bullet(tank.getX(), tank.getY(),ImageManger.bullet,tank.dir,tank));// 发射子弹
                tank.lastFireTime = now;       // 记录本次时间
            }
        }
    }

    final private static long fireCooldownE = 500_000_000;
    static public void decideAndFireE(long now, Enemy enemy, List<Bullet> bullets){
        if (now - enemy.lastFireTime >= fireCooldownE) {
            bullets.add(new Bullet(enemy.getX(), enemy.getY(),ImageManger.bullet,enemy.dir,enemy));// 发射子弹
            enemy.lastFireTime = now;       // 记录本次时间
        }
    }

    @Override
    public Rectangle getBounds() {
        return this;
    }

    private String liveStatus="alive";//
    @Override
    public void onCollide(Collidable other) {
        if(other==owner){
            return;
        }
        switch (other.getType()){
            case TANK :
            case WALL:
            case ENEMY:
                liveStatus="die";break;
            case BULLET:break;
        }
    }
    public boolean isDie(){
        return liveStatus.equals("die");
    }

    @Override
    public CollisionType getType() {
        return CollisionType.BULLET;
    }
}
