package com.tankbattle.app;

import com.tankbattle.config.ImageManger;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class YouDieScene {
    private Scene scene;
    private Stage stage;
    public YouDieScene(Stage stage ,Scene startScene){
        this.stage=stage;

//        Text text=new Text("DIE");
//        text.setLayoutX(450);
//        text.setLayoutY(160);
//        text.setFont(Font.font(30));
//        text.setFill(Color.RED);

//        Button btn2=new Button("返回标题");
//        btn2.setLayoutX(420);  // 横向位置：距离左边100像素
//        btn2.setLayoutY(240);  // 纵向位置：距离顶部150像素
//        btn2.setPrefWidth(120);
//        btn2.setPrefHeight(30);
//        btn2.setOnAction(e -> {
//            stage.setScene(startScene);
//        });

        ImageView bgImageView = new ImageView(ImageManger.dieBG);
        bgImageView.setFitWidth(Main.sceneWid);
        bgImageView.setFitHeight(Main.sceneHei);
        bgImageView.setPreserveRatio(false); // 拉伸填充

        Group root=new Group(bgImageView);
        this.scene=new Scene(root,Main.sceneWid,Main.sceneHei);

        scene.setOnKeyPressed(e -> {
            stage.setScene(startScene);});
    }
    public Scene getScene(){
        return this.scene;
    }

}
