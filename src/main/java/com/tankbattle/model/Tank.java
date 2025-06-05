package com.tankbattle.model;

import com.tankbattle.app.Main;
import com.tankbattle.config.GameConfig;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public abstract class Tank extends Rectangle {
    protected int speed;
    protected Image image;
    protected int dir=8;//方向，逆时针数共1-8方向，默认为3
    public IntegerProperty HP = new SimpleIntegerProperty(GameConfig.GAMER_HP_INIT.get());
    protected long lastFireTime=0;

    protected Tank(int x, int y, double width, Image image,int speed) {
        super(x, y, width, width*image.getHeight()/image.getWidth());
        System.out.println(height);
        this.image=image;
        this.speed=speed;

    }
    public void draw(GraphicsContext gc,Stage stage){
        int windowWid = (int) stage.getWidth(); // 获取当前宽度
        int windowHei = (int) stage.getHeight(); // 获取当前高度
        double scaleX = windowWid / (double) Main.sceneWid;
        double scaleY = windowHei / (double) Main.sceneHei;

        gc.save();
        gc.scale(scaleX,scaleY);
        gc.translate(x,y);
        gc.rotate((3-this.dir)*45);
        gc.setGlobalAlpha((float)this.HP.get()/GameConfig.GAMER_HP_INIT.get());
        gc.drawImage(this.image, -(this.width / 2.0), -(height / 2),this.width,height);
        gc.restore();

        for (Bullet bullet : bullets) {
            bullet.draw(gc,stage);
        }
    }

    final List<Bullet> bullets = new ArrayList<>();



}
