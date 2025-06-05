package com.tankbattle.app;

import com.tankbattle.config.ImageManger;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PauseScene {
    private Scene scene;
    private Stage stage;
    private Group root;
    public PauseScene(Stage stage,GameScene gameScene,Scene startScene){
        this.stage=stage;
        Text text=new Text("暂停");
        text.setLayoutX(450);
        text.setLayoutY(160);
        text.setFont(Font.font(30));

        Button btn1=new Button("继续");
        btn1.setLayoutX(420);  // 横向位置：距离左边100像素
        btn1.setLayoutY(200);  // 纵向位置：距离顶部150像素
        btn1.setPrefWidth(120);
        btn1.setPrefHeight(30);
        btn1.setOnAction(e -> {
            stage.setScene(gameScene.getHomeScene());
            gameScene.start();
        });
        Button btn2=new Button("返回标题");
        btn2.setLayoutX(420);  // 横向位置：距离左边100像素
        btn2.setLayoutY(240);  // 纵向位置：距离顶部150像素
        btn2.setPrefWidth(120);
        btn2.setPrefHeight(30);
        btn2.setOnAction(e -> {
            gameScene.stop();
            stage.setScene(startScene);

        });
        ImageView imageView = new ImageView(ImageManger.tutorial);
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);
        imageView.setLayoutX(280);
        imageView.setLayoutY(275);

        ImageView bgImageView = new ImageView(ImageManger.pauseBackground);
        bgImageView.setFitWidth(Main.sceneWid);
        bgImageView.setFitHeight(Main.sceneHei);
        bgImageView.setPreserveRatio(false); // 拉伸填充

        root=new Group(bgImageView,btn1,btn2,text,imageView);

        // 外层容器，用于缩放
        StackPane scalableRoot = new StackPane(root);
        scene=new Scene(scalableRoot,Main.sceneWid,Main.sceneHei);
//        // 当窗口大小变化时，更新缩放比例
//        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
//            double scaleX = newVal.doubleValue() / Main.sceneWid;
//            double scaleY = scene.getHeight() / Main.sceneHei;
//            root.setScaleX(scaleX);
//            root.setScaleY(scaleY);
//        });
//
//        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
//            double scaleX = scene.getWidth() / Main.sceneWid;
//            double scaleY = newVal.doubleValue() / Main.sceneHei;
//            root.setScaleX(scaleX);
//            root.setScaleY(scaleY);
//        });

    }

    public Scene getSene(){
        return this.scene;
    }
    public void change(){
//        double scaleX = scene.getWidth() / Main.sceneWid;
//        double scaleY = scene.getHeight() / Main.sceneHei;
//        root.setScaleX(scaleX);
//        root.setScaleY(scaleY);
    }

}
