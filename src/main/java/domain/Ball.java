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
        this.colObj = new CollisionObject(this.radius, this);
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
        for (GameObject gameObj : gameObjectList) {
            if (gameObj == this || gameObj == null || gameObj.colObj == null) continue;
            
            if (this.colObj.checkCollision(gameObj.colObj)) {
                if (gameObj instanceof Ball) {
                    int yMem = this.velocityY;
                    int xMem = this.velocityX;
                    this.velocityX = ((Ball) gameObj).velocityX;
                    this.velocityY = ((Ball) gameObj).velocityY;
                    ((Ball) gameObj).velocityX = xMem;
                    ((Ball) gameObj).velocityY = yMem;
                } else {
                    this.velocityY = -this.velocityY;
                }
            }
        }

        if (this.x + this.radius >= xBounds || this.x - this.radius <= 0) {
            this.velocityX = -this.velocityX;
        }
        if (this.y + this.radius >= yBounds || this.y - this.radius <= 0) {
            this.velocityY = -this.velocityY;
        }
        this.x += this.velocityX;
        this.y += this.velocityY;
        if (this.x + this.radius > xBounds) this.x = xBounds - this.radius;
        if (this.x - this.radius < 0) this.x = this.radius;
        if (this.y + this.radius > yBounds) this.y = yBounds - this.radius;
        if (this.y - this.radius < 0) this.y = this.radius;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillOval(this.x - this.radius, this.y - this.radius, this.radius * 2, this.radius * 2);
    }

}
