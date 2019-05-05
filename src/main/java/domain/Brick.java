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
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

/**
 * This class represents a brick object. Bricks are static, destructible game
 * objects that have a health property and a value property. Health determines
 * how many hits it takes to destroy this brick, value dictates how many points
 * will be awarded for destroying this brick.
 *
 * @author Jonkke
 */
public class Brick extends GameObject {

    private int width;
    private int height;
    private Color color;
    private int health;
    private int value;

    public Brick(int x, int y, int width, int height, Color color, int health, int value) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.colObj = new CollisionObject(width, height, this);
        this.color = color;
        this.health = health;
        this.value = value;
    }

    @Override
    public void update(int xBounds, int yBounds, List<GameObject> gameObjectList, Map<KeyCode, Boolean> activeKeys, List<Double> mouseVector) {
        if (this.wasHitBy instanceof Ball) {
            this.health--;
            this.wasHitBy = null;
        }
        if (this.health <= 0) {
            this.markForDestruction();
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        RadialGradient rg = new RadialGradient(0, .1, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE, new Stop(0, this.color), new Stop(1, this.color.darker().darker()));
        gc.setFill(rg);
        gc.fillRect(this.x - this.width / 2, this.y - this.height / 2, this.width, this.height);
    }

    public int getHealth() {
        return this.health;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getValue() {
        return this.value;
    }

}
