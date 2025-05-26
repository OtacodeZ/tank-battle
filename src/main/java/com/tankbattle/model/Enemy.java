package com.tankbattle.model;

import com.tankbattle.app.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.Random;

public class Enemy extends Tank implements Collidable{
    private long lastFireTimeE =0;
    private int enemySeetank=0;
    private int enemyInitHP=100;
    private static double seeDistance=40000;//视野范围
    private String ifOnpenViewCycle="on";//是否可视化enemy的索敌圈，若是，改为“on”

    protected Enemy(int x, int y, double width, Image image, int speed) {
        super(x, y, width, image, speed);
        this.HP.set(enemyInitHP);
        CollisionManager.collidables.add(this);
    }

    @Override
    public void draw(GraphicsContext gc, Stage stage) {
        int windowWid = (int) stage.getWidth(); // 获取当前宽度
        int windowHei = (int) stage.getHeight(); // 获取当前高度
        double scaleX = windowWid / (double) Main.sceneWid;
        double scaleY = windowHei / (double) Main.sceneHei;

        gc.save();
        gc.scale(scaleX,scaleY);
        gc.translate(x,y);
        gc.rotate((3-this.dir)*45);

        gc.drawImage(this.image, -(this.width / 2.0), -(height / 2),this.width,height);
        if(ifOnpenViewCycle.equalsIgnoreCase("on")){
            gc.setLineDashes(10.0, 10.0);
            gc.setStroke(Color.RED);
            gc.setLineWidth(1);
            gc.strokeOval(-200, -200, 400, 400);
        }
        gc.restore();

        for (Bullet bullet : bullets) {
            bullet.draw(gc,stage);
        }
    }

    private void whenSeeTank(Tank tank){
        if(tank.getX()>this.x&&tank.getY()<this.y){
            this.dir=2;
        }else if(tank.getX()<this.x&&tank.getY()<this.y){
            this.dir=4;
        }else if(tank.getX()<this.x&&tank.getY()>this.y){
            this.dir=6;
        }else if(tank.getX()>this.x&&tank.getY()>this.y){
            this.dir=8;
        }else if(tank.getX()==this.x&&tank.getY()<this.y){
            this.dir=3;
        }else if(tank.getX()==this.x&&tank.getY()>this.y){
            this.dir=7;
        }else if(tank.getX()<this.x&&tank.getY()==this.y){
            this.dir=5;
        }else if(tank.getX()>this.x&&tank.getY()==this.y){
            this.dir=1;
        }
    }

    private int oldX,oldY;
    private long changeDirLastTime=0;//HERE 具体值需调试
    protected void move(Tank tank1,Tank tank2,int sceneWid, int sceneHei){
        oldX=x;oldY=y;
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
        x= (int) Math.max(this.width/2, Math.min(sceneWid-this.width/2, x));
        y=Math.max((int)this.height/2, Math.min((int)(sceneHei-this.height/2),y));
    }
    private void changeDir(Tank tank1,Tank tank2){

        if( (Math.pow(tank1.getX()-this.x,2)
                +
                Math.pow(tank1.getY()-this.y,2))<seeDistance){
            enemySeetank=1;

        }else if( (Math.pow(tank2.getX()-this.x,2)
                +
                Math.pow(tank2.getY()-this.y,2) )<seeDistance){
            enemySeetank=2;

        } else {
            enemySeetank=0;
            Random random=new Random();
            this.dir=1+random.nextInt(4);
        }
    }
    private long lastChangeDir=0;
    final private long changeDirCooldown=3_000_000_000L;
    public void update(long now, Tank tank1, Tank tank2,int sceneWid, int sceneHei) {
        move(tank1,tank2,sceneWid,sceneHei);
        //changeDir
        if(now-lastChangeDir>=changeDirCooldown){
            changeDir(tank1,tank2);
            lastChangeDir=now;
        }

        Bullet.decideAndFireE(now,this,bullets);
        bullets.forEach(Bullet::move);

        //remove bullets
        Iterator<Bullet> iterator=bullets.iterator();
        while (iterator.hasNext()){
            Bullet bullet=iterator.next();
            if(bullet.isOffScreen(Main.sceneWid,Main.sceneHei)|| bullet.isDie()){
                iterator.remove();
                CollisionManager.collidables.remove(bullet);
            }
        }
    }




     public boolean ifDie(){
        if(
                this.HP.get()<=0
        ){
            return true;
        }else {
            return false;
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
        switch (other.getType()) {

            case TANK:
            case WALL:
            case ENEMY:
                x=oldX;y=oldY;break;
            case BULLET:
                this.HP.set(this.HP.get() - Bullet.damage);
                System.out.println(this+"be shooted");
                break;
        }
    }

    @Override
    public CollisionType getType() {
        return CollisionType.ENEMY;
    }

    @Override
    public Collidable getOwner() {
        return null;
    }
}
