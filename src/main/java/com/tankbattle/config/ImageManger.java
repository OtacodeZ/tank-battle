package com.tankbattle.config;

import javafx.scene.image.Image;

public class ImageManger {
    public static final String TANK_IMG_A = "/images/tankGamerA.png";
    public static final String TANK_IMG_B = "/images/tankGamerB.png";

    public static final String BULLET_IMG="/images/bulletImg.png";
    public static final String ENEMY_IMG="/images/enemyIMG.png";

    public static final Image tankGamerA=new Image(ImageManger.class.getResource(TANK_IMG_A).toExternalForm(),0, 0, true, true, false);
    public static final Image tankGamerB=new Image(ImageManger.class.getResource(TANK_IMG_B).toExternalForm(),0, 0, true, true, false);

    public static final Image bullet=new Image(ImageManger.class.getResource(BULLET_IMG).toExternalForm(),0, 0, true, true, false);
    public static final Image enemy=new Image(ImageManger.class.getResource(ENEMY_IMG).toExternalForm(),0, 0, true, true, false);

    public static final Image tutorial=new Image(ImageManger.class.getResource("/images/tutorial.png").toExternalForm());
    public static final Image pauseBackground=new Image(ImageManger.class.getResource("/images/pausePageBackground.jpg").toExternalForm());
    public static final Image dieBG=new Image(ImageManger.class.getResource("/images/dieBG.png").toExternalForm());

    //healthPack
    public static final Image hp =new Image(ImageManger.class.getResource(
            "/images/hp.png").toExternalForm(),0, 0, true, true, false);
}