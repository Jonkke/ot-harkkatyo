/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.List;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

/**
 * This class represents the Paddle game object. Paddle is used to bounce the
 * ball in the game, and player is in direct control of it. The paddle is
 * controlled with the mouse.
 *
 * @author Jonkke
 */
public class Paddle extends GameObject {

    private int width;
    private int height;
    private double moveSpeed;

    public Paddle(int x, int y) {
        super(x, y);
        this.width = 50;
        this.height = 10;
        this.moveSpeed = 15;
        this.colObj = new CollisionObject(width, height, this);
    }

    public Paddle(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.moveSpeed = 15;
        this.colObj = new CollisionObject(width, height, this);
    }

    @Override
    public void update(int xBounds, int yBounds, List<GameObject> gameObjectList, Map<KeyCode, Boolean> activeKeys, List<Double> mouseVector) {
        this.x = mouseVector.get(0);
        if (this.x < this.width / 2) {
            this.x = this.width / 2;
        }
        if (this.x > xBounds - this.width / 2) {
            this.x = xBounds - this.width / 2;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        Stop[] stops = new Stop[]{new Stop(0, Color.BLUEVIOLET), new Stop(1, Color.DARKBLUE), new Stop(2, Color.BLUEVIOLET)};
        LinearGradient lg = new LinearGradient(0, 0, 0.5, 0, true, CycleMethod.REFLECT, stops);
        gc.setFill(lg);
        gc.fillRect(this.x - this.width / 2, this.y - this.height / 2, this.width, this.height);
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
        this.colObj = new CollisionObject(this.width, this.height, this);
    }

}
