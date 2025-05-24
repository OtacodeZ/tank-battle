package com.tankbattle.ui;// Background.java
import com.tankbattle.app.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Background {
    public void draw(GraphicsContext gc, int bgWidth, int bgHeight, Stage stage){
        // 画背景，比如绿色的草地
        int windowWid = (int) stage.getWidth(); // 获取当前宽度
        int windowHei = (int) stage.getHeight(); // 获取当前高度
        double scaleX = windowWid / (double) Main.sceneWid;
        double scaleY = windowHei / (double) Main.sceneHei;
        gc.save();
        gc.scale(scaleX,scaleY);
        gc.setFill(Color.BEIGE);
        gc.fillRect(0, 0, bgWidth, bgHeight);
        gc.restore();
    }
}
