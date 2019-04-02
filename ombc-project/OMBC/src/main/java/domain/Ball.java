/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Jonkke
 */
public class Ball extends GameObject {

    private int radius;
    private long startTime;

    public Ball(int x, int y, int radius) {
        super(x, y);
        this.radius = radius;
    }

    @Override
    public void update(int xBounds, int yBounds, List<GameObject> gameObjectList, boolean[] keyStates, double[] mouseStates) {
        
//        if (keyStates[0]) {
//            this.velocityY--;
//        } else if (keyStates[1]) {
//            this.velocityY++;
//        } else if (keyStates[2]) {
//            this.velocityX--;
//        } else if (keyStates[3]) {
//            this.velocityX++;
//        } else if (keyStates[4]) {
//            this.velocityX = 0;
//            this.velocityY = 0;
//        }

        if (this.x + this.radius >= xBounds || this.x - this.radius <= 0) {
            this.velocityX = -this.velocityX;
        }
        if (this.y + this.radius >= yBounds || this.y - this.radius <= 0) {
            this.velocityY = -this.velocityY;
        }
        this.x += this.velocityX;
        this.y += this.velocityY;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillOval(this.x - this.radius, this.y - this.radius, this.radius * 2, this.radius * 2);
    }

}
