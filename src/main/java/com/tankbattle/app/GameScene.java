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

import java.util.ArrayList;
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

    private  Set<KeyCode> keysPressed;

    //bgm
    Media bgmMedia = new Media(AudioPath.BGM);
    MediaPlayer bgmPlayer = new MediaPlayer(bgmMedia);

    // Blood packs
    private final ArrayList<HealthPack> healthPacks = new ArrayList<>();
    private long lastHealthPackSpawnTime = 0;
    private final long HEALTH_PACK_INTERVAL = 8_000_000_000L; // 每8秒生成一个
    private final int MAX_HEALTH_PACKS = 1;

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

        keysPressed = new HashSet<>();
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

                // 生成血包
                if (now - lastHealthPackSpawnTime > HEALTH_PACK_INTERVAL) {
                    if (healthPacks.size() < MAX_HEALTH_PACKS) {
                        int x = (int) (Math.random() * Main.sceneWid);
                        int y = (int) (Math.random() * Main.sceneHei);
                        healthPacks.add(new HealthPack(x, y,
                                ImageManger.hp, null));
                         // 添加到碰撞管理器
                        lastHealthPackSpawnTime = now;
                    }
                }

                // 移除已使用的血包
                Iterator<HealthPack> iterator = healthPacks.iterator();
                while (iterator.hasNext()) {

                    HealthPack healthPack = iterator.next();
                    if (healthPack.isUsed()) {
                        System.out.println("x");
                        iterator.remove(); // 使用迭代器的 remove 方法
                        System.out.println("bef:"+
                        CollisionManager.collidables);
                        CollisionManager.collidables.remove(healthPack);
                        System.out.println("af:"+CollisionManager.collidables);
                    }
                }

                //pause Scene
                if(keysPressed.contains(KeyCode.ESCAPE)){
                    keysPressed.remove(KeyCode.ESCAPE);
                    bgmPlayer.pause();
                    keysPressed.clear();
                    gameLoop.stop();
                    pauseScene.change();
                    stage.setScene(pauseScene.getSene());
                }
                //end Scene
                if(tankGamerA.HP.get()<=0&&tankGamerB.HP.get()<=0){
                    bgmPlayer.stop();
                    keysPressed.clear();
                    gameLoop.stop();
                    YouDieScene youDieScene=new YouDieScene(stage,startScene);
                    stage.setScene(youDieScene.getScene());
                }
            }



            private void draw(){

                bg.draw(gc,Main.sceneWid,Main.sceneHei,stage);
                tankGamerA.draw(gc,stage);
                tankGamerB.draw(gc,stage);
                enemyManager.draw(gc,stage);
                //healthPack
                for (HealthPack hp : healthPacks) {
                    hp.draw(gc, stage);
                }
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

        // 清理旧的血包
        healthPacks.clear();
        CollisionManager.collidables.removeAll(healthPacks);

        // 重置血包生成时间
        lastHealthPackSpawnTime = 0;

        bgmPlayer.play();
        gameLoop.start();
    }

    public void stop() {
        bgmPlayer.stop();
        keysPressed.clear();
        System.out.println("key:"+keysPressed);
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
        tankGamerA.clearBullet();
        tankGamerB.clearBullet();
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
            enemy.clearBullet();
            enemy.HP.set(0);
            iterator.remove();
            CollisionManager.collidables.remove(enemy);
        }

        // Clear old health packs
        healthPacks.clear();
        CollisionManager.collidables.removeAll(healthPacks);

        // Reset last health pack spawn time
        lastHealthPackSpawnTime = 0;

        System.out.println("after clear:"+EnemyManager.enemies);
        bgmPlayer.play();
        gameLoop.start();
    }

}

