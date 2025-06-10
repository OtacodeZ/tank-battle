package com.tankbattle.model;

import com.tankbattle.app.GameScene;
import com.tankbattle.app.Main;
import com.tankbattle.config.GameConfig;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import com.tankbattle.config.AudioPath;
import com.tankbattle.config.ImageManger;
import javafx.stage.Stage;

public class HealthPack extends Rectangle implements Collidable {
    private Image image;
    public static final int hp = 10; // 每个血包恢复的生命值

    @Override
    public Collidable getOwner() {
        return null;
    }

    public HealthPack(int x, int y, Image image, Collidable owner) {
        super(x, y, 100, 100 * image.getHeight() / image.getWidth());
        this.image = image;


        CollisionManager.collidables.add(this);
    }

    public void draw(GraphicsContext gc, Stage stage) {
        int windowWid = (int) stage.getWidth(); // 获取当前宽度
        int windowHei = (int) stage.getHeight(); // 获取当前高度
        double scaleX = windowWid / (double) Main.sceneWid;
        double scaleY = windowHei / (double) Main.sceneHei;

        gc.save();
        gc.scale(scaleX, scaleY);
        gc.translate(x, y);
        gc.drawImage(this.image, -(this.width / 2.0), -(height / 2), this.width, this.height);
        gc.restore();
    }

    @Override
    public void onCollide(Collidable other) {
        switch (other.getType()){
            case TANK :this.markAsUsed();break;  // 标记血包为“已使用”，等待 GameScene 中统一移除
            case WALL:
            case ENEMY:
            case BULLET:
            case HEALTHPACK:
        }

    }

    @Override
    public Rectangle getBounds() {
        return this;
    }

    @Override
    public CollisionType getType() {
        return CollisionType.HEALTHPACK;
    }

    // 标记血包为“已使用”
    private boolean used = false;

    private void markAsUsed() {
        used = true;
    }

    public boolean isUsed() {
        return used;
    }
}