package com.tankbattle.app;

import com.tankbattle.config.GameConfig;
import com.tankbattle.model.*;
import com.tankbattle.ui.Background;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
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
import java.util.Iterator;
import java.util.Set;

//
public class GameScene {
    private Scene homeScene;
    private AnimationTimer gameLoop;
    private Stage stage;
    private String ifGenerateTankB="yes";
    TankGamerA tankGamerA=new TankGamerA(150,400,50, ImageManger.tankGamerA, GameConfig.GAMER_SPEED.get());
    TankGamerB tankGamerB=new TankGamerB(850,400,50,ImageManger.tankGamerB,GameConfig.GAMER_SPEED.get());

    //bgm
    Media bgmMedia = new Media(AudioPath.BGM);
    MediaPlayer bgmPlayer = new MediaPlayer(bgmMedia);
    public GameScene(Stage stage,Scene startScene) {
        this.stage=stage;

        //classes
//        tankGamerA=new TankGamerA(150,150,50, ImageManger.tankGamerA, GameConfig.GAMER_SPEED);

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

        if(GameConfig.GAMER_COUNT.equalsIgnoreCase("two")){
            Text hpB =new Text();
            hpB.textProperty().bind(
                    Bindings.concat("TankB HP :", tankGamerB.HP.asString())
            );
            hpB.setX(0);
            hpB.setY(40);
            root.getChildren().add(hpB);
        }

//

        homeScene = new Scene(root, Main.sceneWid, Main.sceneHei);

        //interactKeyboard();

        Set<KeyCode> keysPressed = new HashSet<>();
        homeScene.setOnKeyPressed(event ->
                keysPressed.add(event.getCode()));
        homeScene.setOnKeyReleased(event ->
                keysPressed.remove(event.getCode()));
        //pause page
        PauseScene pauseScene=new PauseScene(stage,this,startScene);


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


                //pause Scene
                if(keysPressed.contains(KeyCode.ESCAPE)){
                    keysPressed.remove(KeyCode.ESCAPE);
                    bgmPlayer.pause();
                    gameLoop.stop();
                    pauseScene.change();
                    stage.setScene(pauseScene.getSene());
                }
                //end Scene
                if(tankGamerA.HP.get()<=0&&tankGamerB.HP.get()<=0){
                    this.stop();
                    YouDieScene youDieScene=new YouDieScene(stage,startScene);
                    stage.setScene(youDieScene.getScene());
                }
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

    public int gameCount=0;//记录是第几次开始游戏
    public void start() {
        gameCount++;
        bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgmPlayer.setVolume(0.3);

        tankGamerA.HP.set(GameConfig.GAMER_HP_INIT.get());
        tankGamerB.HP.set(GameConfig.GAMER_HP_INIT.get());
        tankGamerA.speed=GameConfig.GAMER_SPEED.get();
        tankGamerB.speed=GameConfig.GAMER_SPEED.get();

        if(GameConfig.GAMER_COUNT.equalsIgnoreCase("one")){
            tankGamerB.HP.set(-1);
        }

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

    public void restartGame(){
        tankGamerA.HP.set(GameConfig.GAMER_HP_INIT.get());
        tankGamerB.HP.set(GameConfig.GAMER_HP_INIT.get());
        tankGamerA.speed=GameConfig.GAMER_SPEED.get();
        tankGamerB.speed=GameConfig.GAMER_SPEED.get();
        tankGamerA.setXY(150,400);
        tankGamerB.setXY(850,400);
        if(GameConfig.GAMER_COUNT.equalsIgnoreCase("one")){
            tankGamerB.HP.set(-1);
        }

        Iterator<Enemy> iterator=EnemyManager.enemies.iterator();
        while (iterator.hasNext()){
            Enemy enemy=iterator.next();
            enemy.HP.set(0);
            iterator.remove();
            CollisionManager.collidables.remove(enemy);
        }
        bgmPlayer.play();
        gameLoop.start();
    }

}

