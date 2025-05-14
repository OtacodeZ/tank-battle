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
    final private int sceneWid=800;
    final private int sceneHei=600;

    private final List<Bullet> bullets = new ArrayList<>();

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

        TankGamerA tankA=new TankGamerA(350,500,2, ImagePath.TANK_IMG,50);
        TankGamerB tankB=new TankGamerB(150,500,2, ImagePath.TANK_IMG,50);

        //interactKeyboard();
        Set<KeyCode> keysPressed = new HashSet<>();
        scene.setOnKeyPressed(event ->
                keysPressed.add(event.getCode()));
        scene.setOnKeyReleased(event ->
                keysPressed.remove(event.getCode()));

        scene.setOnKeyPressed(event -> {
            keysPressed.add(event.getCode());
            // 按下空格键发射子弹
            if (event.getCode() == KeyCode.SPACE) {
                fireBullet(tankA);
            }
        });
        //动画
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                bg.draw(gc,sceneWid,sceneHei);

                tankA.move(keysPressed,sceneWid,sceneHei);
                tankA.draw(gc);

                gc.setFill(Color.RED);
                gc.fillOval(tankA.x, tankA.y, 6, 10);

                tankB.move(keysPressed,sceneWid,sceneHei);
                tankB.draw(gc);

                bullets.forEach(Bullet::move);
                for (Bullet bullet : bullets) {
                    bullet.draw(gc);
                }
                bullets.removeIf(b -> b.isOffScreen(sceneWid,sceneHei));
            }
        };
        timer.start();


        primaryStage.setTitle("坦克大战");
        primaryStage.setScene(scene);
        primaryStage.show();



    }
    private void fireBullet(TankGamerA tankA) {
        // 创建一颗新子弹
        double bulletX = tankA.x + tankA.imageWid/2.0;  
        double bulletY = tankA.y +tankA.imageHei/2.0;
        bullets.add(new Bullet(bulletX, bulletY,tankA.dir));
    }

}
