package com.tankbattle.ui;// Background.java
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Background {
    public void draw(GraphicsContext gc,int bgWidth,int bgHeight){
        // 画背景，比如绿色的草地
        gc.setFill(Color.BEIGE);
        gc.fillRect(0, 0, bgWidth, bgHeight);

    }
}
