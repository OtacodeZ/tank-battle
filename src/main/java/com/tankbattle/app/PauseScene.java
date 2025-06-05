package com.tankbattle.app;

import com.tankbattle.config.ImageManger;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PauseScene {
    private Scene scene;
    private Stage stage;
    public PauseScene(Stage stage,GameScene gameScene){
        this.stage=stage;
        Text text=new Text("暂停");
        text.setLayoutX(450);
        text.setLayoutY(200);
        text.setFont(Font.font(30));

        Button btn1=new Button("继续");
        btn1.setLayoutX(420);  // 横向位置：距离左边100像素
        btn1.setLayoutY(220);  // 纵向位置：距离顶部150像素
        btn1.setPrefWidth(120);
        btn1.setPrefHeight(40);
        btn1.setOnAction(e -> {
            stage.setScene(gameScene.getHomeScene());
            gameScene.start();
        });
        ImageView imageView = new ImageView(ImageManger.tutorial);
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);
        imageView.setLayoutX(280);
        imageView.setLayoutY(280);

        Group root=new Group(btn1,text,imageView);
        scene=new Scene(root,Main.sceneWid,Main.sceneHei);
    }

    public Scene getSene(){
        return this.scene;
    }

}
