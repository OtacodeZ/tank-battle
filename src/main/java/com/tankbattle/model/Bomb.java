package com.tankbattle.model;

import com.tankbattle.app.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Bomb extends Rectangle implements Collidable {
    private Image image;
    public static final int hurt = 5; // 每个炸弹的伤害

    @Override
    public Collidable getOwner() {
        return null;
    }

    public Bomb(int x, int y, Image image, Collidable owner) {
        super(x, y, 30, 30 * image.getHeight() / image.getWidth());
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
            case TANK :this.markAsDestroyed();break;  // 标记炸弹为“已使用”，等待 GameScene中统一移除
            case WALL:
            case ENEMY:
            case BULLET:
            case HEALTHPACK:
            case BOMB:
        }

    }

    @Override
    public Rectangle getBounds() {
        return this;
    }

    @Override
    public CollisionType getType() {
        return CollisionType.BOMB;
    }

    // 标记炸弹为“已使用”
    private boolean used = false;

    private void markAsDestroyed() {
        used = true;
    }

    public boolean isDestroyed() {
        return used;
    }
}
