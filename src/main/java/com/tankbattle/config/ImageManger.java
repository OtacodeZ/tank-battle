package com.tankbattle.config;

import javafx.scene.image.Image;

public class ImageManger {
    public static final String TANK_IMG= "/images/tankImg.png";
    public static final String BULLET_IMG="/images/bulletImg.png";
    public static final String ENEMY_IMG="/images/enemyIMG.png";

    public static final Image tankGamerA=new Image(ImageManger.class.getResource(TANK_IMG).toExternalForm(),0, 0, true, true, false);
    public static final Image bullet=new Image(ImageManger.class.getResource(BULLET_IMG).toExternalForm(),0, 0, true, true, false);
    public static final Image enemy=new Image(ImageManger.class.getResource(ENEMY_IMG).toExternalForm(),0, 0, true, true, false);
}