package resource.config;

import javafx.scene.image.Image;

public class ImageManger {
    public static final String TANK_IMG= "/images/tankImg.png";
    public static final String BULLET_IMG="/images/bulletImg.png";
    public static final String ENEMY_IMG="/images/enemyIMG.png";

    public static final Image tankGamerA=new Image(TANK_IMG,0, 0, true, true, false);
    public static final Image bullet=new Image(BULLET_IMG,0, 0, true, true, false);
    public static final Image enemy=new Image(ENEMY_IMG,0, 0, true, true, false);
}
