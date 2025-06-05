package com.tankbattle.model;

import com.tankbattle.app.Main;
import com.tankbattle.config.GameConfig;
import com.tankbattle.ui.Background;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.tankbattle.config.AudioPath;
import com.tankbattle.config.ImageManger;

import java.util.HashSet;
import java.util.Set;

//
public class Mode1 {
    private Scene homeScene;
    private AnimationTimer gameLoop;
    private Stage stage;
    private String ifGenerateTankB="yes";
    TankGamerA tankGamerA=new TankGamerA(150,150,50, ImageManger.tankGamerA, GameConfig.GAMER_A_SPEED);
    TankGamerB tankGamerB=new TankGamerB(150,300,50,ImageManger.tankGamerB,GameConfig.GAMER_B_SPEED);

    //bgm
    Media bgmMedia = new Media(AudioPath.BGM);
    MediaPlayer bgmPlayer = new MediaPlayer(bgmMedia);
    public Mode1(Stage stage) {
        this.stage=stage;

        //classes
//        tankGamerA=new TankGamerA(150,150,50, ImageManger.tankGamerA, GameConfig.GAMER_A_SPEED);

//        tankGamerB =new TankGamerB(150,300,50,ImageManger.tankGamerB,GameConfig.GAMER_B_SPEED);

        EnemyManager enemyManager=new EnemyManager();

        Background bg=new Background();
        Canvas canvas=new Canvas(Main.sceneWid,Main.sceneHei);
        GraphicsContext gc=canvas.getGraphicsContext2D();
        Group root = new Group(canvas);

        //hp
        Text hpA =new Text();
        hpA.textProperty().bind(
                Bindings.concat("TankA HP :", tankGamerA.HP.asString())
        );
        hpA.setX(0);
        hpA.setY(20);
        root.getChildren().add(hpA);

        Text hpB =new Text();
        hpB.textProperty().bind(
                Bindings.concat("TankB HP :", tankGamerB.HP.asString())
        );
        hpB.setX(0);
        hpB.setY(40);
        root.getChildren().add(hpB);
//

        homeScene = new Scene(root, Main.sceneWid, Main.sceneHei);

        //interactKeyboard();
        Set<KeyCode> keysPressed = new HashSet<>();
        homeScene.setOnKeyPressed(event ->
                keysPressed.add(event.getCode()));
        homeScene.setOnKeyReleased(event ->
                keysPressed.remove(event.getCode()));
        // 游戏主循环
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
                draw();

            }
            private void update(long now){
                tankGamerA.update(now,keysPressed,Main.sceneWid,Main.sceneHei);
                tankGamerB.update(now,keysPressed,Main.sceneWid,Main.sceneHei);

                CollisionManager.update();

                enemyManager.update(now,tankGamerA,tankGamerB,Main.sceneWid,Main.sceneHei);

                sizeChangeable(stage,canvas);
            }



            private void draw(){

                bg.draw(gc,Main.sceneWid,Main.sceneHei,stage);
                tankGamerA.draw(gc,stage);
                tankGamerB.draw(gc,stage);
                enemyManager.draw(gc,stage);

            }
        };
    }

    public Scene getHomeScene() {
        return homeScene;
    }

    public void start() {
        bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgmPlayer.setVolume(0.3);

        tankGamerA.HP.set(GameConfig.GAMER_HP_INIT.get());
        tankGamerB.HP.set(GameConfig.GAMER_HP_INIT.get());

        bgmPlayer.play();
        gameLoop.start();
    }

    public void stop() {
        bgmPlayer.stop();
        gameLoop.stop();
    }


    private void sizeChangeable(Stage stage, Canvas canvas){
        //可全屏化
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {

            canvas.setWidth(newVal.doubleValue());
        });
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {

            canvas.setHeight(newVal.doubleValue());
        });
    }

}

