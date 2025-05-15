package com.tankbattle.app;
import com.tankbattle.model.Bullet;
import com.tankbattle.model.TankGamerA;
import com.tankbattle.model.TankGamerB;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.tankbattle.ui.Background;
import com.tankbattle.model.Tank;
import resource.config.ImagePath;

import java.util.*;

public class Main extends Application {



     private int sceneWid=800;
     private int sceneHei=600;



    public static void main(String[] args) {


        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        // Background and Tank
        Background bg=new Background();

        Tank tankA=new TankGamerA(350,500,2, ImagePath.TANK_IMG,50);
        final List<Bullet> bulletsA = new ArrayList<>();
        Tank tankB=new TankGamerB(150,500,2, ImagePath.TANK_IMG,50);
        final List<Bullet> bulletsB = new ArrayList<>();


        //画布与场景
        Canvas canvas=new Canvas(sceneWid,sceneHei);
        GraphicsContext gc=canvas.getGraphicsContext2D();
        Group root = new Group(canvas);
        //HP display
        Text hpA =new Text();
        hpA.textProperty().bind(
                Bindings.concat("TankA HP :", tankA.HP.asString())
        );
        hpA.setX(0);
        hpA.setY(20);
        root.getChildren().add(hpA);

        Text hpB =new Text();
        hpB.textProperty().bind(
                Bindings.concat("TankB HP :", tankB.HP.asString())
        );
        hpB.setX(0);
        hpB.setY(40);
        root.getChildren().add(hpB);

        //Scene
        Scene scene = new Scene(root, sceneWid, sceneHei);


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

                update(now);
                draw();
            }
            private void update(long now){
                updateTankA(now);
                updateTankB(now);
            }
            private void updateTankA(long now){
                if(tankA.HP.get()<=0){
                    return;
                }

                tankA.move(keysPressed,sceneWid,sceneHei);
                bulletsA.forEach(Bullet::move);
                Bullet.decideAndFireA(keysPressed,now,tankA,bulletsA);
                bulletsA.removeIf(b -> b.isOffScreen(sceneWid,sceneHei));
                //碰撞检测
                Iterator<Bullet> iterator2 = bulletsB.iterator();
                while (iterator2.hasNext()) {
                    Bullet bullet = iterator2.next();
                    if (tankA.intersects(bullet)) {
                        iterator2.remove();
                        tankA.HP.set(tankA.HP.get()-bullet.damage);
                    }
                }
            }
            private void updateTankB(long now){
                if(tankB.HP.get()<=0){
                    return;
                }

                tankB.move(keysPressed,sceneWid,sceneHei);
                bulletsB.forEach(Bullet::move);
                Bullet.decideAndFireB(keysPressed,now,tankB,bulletsB);
                bulletsB.removeIf(b -> b.isOffScreen(sceneWid,sceneHei));
                //碰撞检测
                Iterator<Bullet> iterator1 = bulletsA.iterator();
                while (iterator1.hasNext()) {
                    Bullet bullet = iterator1.next();
                    if (tankB.intersects(bullet)) {
                        iterator1.remove();
                        tankB.HP.set(tankB.HP.get()-bullet.damage);
                    }
                }
            }

            private void draw(){
                bg.draw(gc,sceneWid,sceneHei);
                for (Bullet bullet : bulletsA) {
                    bullet.draw(gc);
                }
                for (Bullet bullet : bulletsB) {
                    bullet.draw(gc);
                }
                drawTankA();
                drawTankB();
            }
            private void drawTankA(){
                if(tankA.HP.get()<=0){
                    return;
                }
                tankA.draw(gc);
            }
            private void drawTankB(){
                if(tankB.HP.get()<=0){
                    return;
                }
                tankB.draw(gc);
            }


        };
        timer.start();


        primaryStage.setTitle("坦克大战");
        primaryStage.setScene(scene);
        primaryStage.show();

        sizeChangeable(primaryStage,canvas);


    }

    private void sizeChangeable(Stage stage, Canvas canvas){
        //可全屏化
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            sceneWid=newVal.intValue();
            canvas.setWidth(newVal.doubleValue());

        });
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            sceneHei=newVal.intValue();
            canvas.setHeight(newVal.doubleValue());
        });
    }


}
