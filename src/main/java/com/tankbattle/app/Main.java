package com.tankbattle.app;
import com.tankbattle.model.Bullet;
import com.tankbattle.model.TankGamerA;
import com.tankbattle.model.TankGamerB;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.tankbattle.ui.Background;
import com.tankbattle.model.Tank;
import resource.config.ImagePath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main extends Application {

    long lastFireTime = 0; // 纳秒时间戳
    final long fireCooldown = 100_000_000; // 500ms 冷却，单位是纳秒（ns）


    final private int sceneWid=800;
    final private int sceneHei=600;



    public static void main(String[] args) {


        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {


        //画布与场景
        Canvas canvas=new Canvas(sceneWid,sceneHei);
        GraphicsContext gc=canvas.getGraphicsContext2D();
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, sceneWid, sceneHei);

        // Background and Tank
        Background bg=new Background();

        Tank tankA=new TankGamerA(350,500,2, ImagePath.TANK_IMG,50);
        final List<Bullet> bulletsA = new ArrayList<>();

        Tank tankB=new TankGamerB(150,500,2, ImagePath.TANK_IMG,50);
        final List<Bullet> bulletsB = new ArrayList<>();
        //interactKeyboard();
        Set<KeyCode> keysPressed = new HashSet<>();
        scene.setOnKeyPressed(event ->
                keysPressed.add(event.getCode()));
        scene.setOnKeyReleased(event ->
                keysPressed.remove(event.getCode()));


        //动画
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //update
                tankA.move(keysPressed,sceneWid,sceneHei);
                bulletsA.forEach(Bullet::move);
                Bullet.decideAndFireA(keysPressed,now,tankA,bulletsA);
                bulletsA.removeIf(b -> b.isOffScreen(sceneWid,sceneHei));

                tankB.move(keysPressed,sceneWid,sceneHei);
                bulletsB.forEach(Bullet::move);
                Bullet.decideAndFireB(keysPressed,now,tankB,bulletsB);
                bulletsB.removeIf(b -> b.isOffScreen(sceneWid,sceneHei));


                //draw
                bg.draw(gc,sceneWid,sceneHei);

                tankA.draw(gc);
                tankB.draw(gc);

                for (Bullet bullet : bulletsA) {
                    bullet.draw(gc);
                }
                for (Bullet bullet : bulletsB) {
                    bullet.draw(gc);
                }


            }
        };
        timer.start();


        primaryStage.setTitle("坦克大战");
        primaryStage.setScene(scene);
        primaryStage.show();



    }


}
