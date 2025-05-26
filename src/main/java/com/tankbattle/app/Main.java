package com.tankbattle.app;
import com.tankbattle.model.Enemy;
import com.tankbattle.model.Mode1;
import com.tankbattle.model.Mode2;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import resource.config.VedioUrl;

public class Main extends Application {



     public static int sceneWid=960;
     public static int sceneHei=540;

    private Stage primaryStage;
    private Scene startScene;
    private Mode1 mode1;
    private Mode2 mode2;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        // --- 开始界面 ---

        CheckBox checkBox = new CheckBox("可视化敌人视野");
        checkBox.setLayoutX(420);
        checkBox.setLayoutY(400);
        checkBox.setOnAction(e -> {
            if (checkBox.isSelected()) {
                Enemy.ifOnpenViewCycle="on";
            } else {
                Enemy.ifOnpenViewCycle="no";
            }
        });

        Media mediaStartBg=new Media(VedioUrl.mediaUrl);
        MediaPlayer mediaStartBgPlayer = new MediaPlayer(mediaStartBg);
        // 3. 创建MediaView显示视频
        MediaView mediaView = new MediaView(mediaStartBgPlayer);

        Text text=new Text("坦克大战");
        text.setLayoutX(420);
        text.setLayoutY(200);
        text.setFont(Font.font(30));
        text.setFill(Color.RED);

        Button btn1 = new Button("进入模式1");
        Button btn2 = new Button("进入模式2");
        btn1.setLayoutX(420);  // 横向位置：距离左边100像素
        btn1.setLayoutY(220);  // 纵向位置：距离顶部150像素
        btn1.setPrefWidth(120);
        btn1.setPrefHeight(40);
        btn2.setLayoutX(420);
        btn2.setLayoutY(280);
        btn2.setPrefWidth(120);
        btn2.setPrefHeight(40);

        btn1.setOnAction(e -> {
            primaryStage.setScene(mode1.getScene());
            mode1.start();
            mediaStartBgPlayer.stop();
        });

        btn2.setOnAction(e -> {
            primaryStage.setScene(mode2.getScene());
            mode2.start();
        });

        Pane root = new Pane( mediaView,text, btn1, btn2,checkBox);
        mediaView.fitWidthProperty().bind(root.widthProperty());
        mediaView.fitHeightProperty().bind(root.heightProperty());
        mediaView.setPreserveRatio(false);

        startScene = new Scene(root, sceneWid, sceneHei);

        // 初始化两个模式
        mode1 = new Mode1(primaryStage);
        mode2 = new Mode2(primaryStage, startScene);

        // 设置初始界面
        primaryStage.setScene(startScene);
        primaryStage.setTitle("坦克大战");
        primaryStage.show();

        mediaStartBgPlayer.setOnReady(() -> {
            mediaStartBgPlayer.play();
        });


    }

    public static void main(String[] args) {
        launch(args);
    }




}
