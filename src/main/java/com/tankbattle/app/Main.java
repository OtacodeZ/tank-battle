package com.tankbattle.app;
import com.tankbattle.config.GameConfig;
import com.tankbattle.model.Enemy;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.tankbattle.config.VedioUrl;

public class Main extends Application {



     public static int sceneWid=960;
     public static int sceneHei=540;

    private Stage primaryStage;
    private Scene startScene;
    private GameScene gameScene;
    private ChangeConfigScene changeConfigScene;

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
        CheckBox checkBoxGamer = new CheckBox("双人模式");
        checkBoxGamer.setLayoutX(420);
        checkBoxGamer.setLayoutY(420);
        checkBoxGamer.setOnAction(e -> {

            if (checkBoxGamer.isSelected()) {
                GameConfig.GAMER_COUNT="two";
            } else {
                GameConfig.GAMER_COUNT="one";
            }
        });

        Media mediaStartBg=new Media(VedioUrl.mediaUrl);
        MediaPlayer mediaStartBgPlayer = new MediaPlayer(mediaStartBg);

        MediaView mediaView = new MediaView(mediaStartBgPlayer);

        Text text=new Text("坦克大战");
        text.setLayoutX(420);
        text.setLayoutY(200);
        text.setFont(Font.font(30));
        text.setFill(Color.RED);

        Button btn1 = new Button("游戏开始");
        Button btn2 = new Button("修改器");
        btn1.setLayoutX(420);  // 横向位置：距离左边100像素
        btn1.setLayoutY(220);  // 纵向位置：距离顶部150像素
        btn1.setPrefWidth(120);
        btn1.setPrefHeight(40);
        btn2.setLayoutX(420);
        btn2.setLayoutY(280);
        btn2.setPrefWidth(120);
        btn2.setPrefHeight(40);

        btn1.setOnAction(e -> {
            primaryStage.setScene(gameScene.getHomeScene());
            gameScene.start();
            mediaStartBgPlayer.stop();
        });

        btn2.setOnAction(e -> {
            primaryStage.setScene(changeConfigScene.getScene());
            changeConfigScene.start();
        });

        Pane root = new Pane( mediaView,text, btn1, btn2, checkBoxGamer,checkBox);
        mediaView.fitWidthProperty().bind(root.widthProperty());
        mediaView.fitHeightProperty().bind(root.heightProperty());
        mediaView.setPreserveRatio(false);

        startScene = new Scene(root, sceneWid, sceneHei);

        // 初始化两个模式
        gameScene = new GameScene(primaryStage,startScene);
        changeConfigScene = new ChangeConfigScene(primaryStage,startScene);

        // 设置初始界面
        primaryStage.setScene(startScene);
        primaryStage.setTitle("坦克大战");
        primaryStage.show();

        mediaStartBgPlayer.setOnReady(() -> {
            mediaStartBgPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaStartBgPlayer.play();
        });


    }

    public static void main(String[] args) {
        launch(args);
    }




}
