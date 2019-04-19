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
public class Brick extends GameObject {

    private int width;
    private int height;
    private int health;

    public Brick(int x, int y, int width, int height, int health) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.colObj = new CollisionObject(width, height, this);
        this.health = health;
    }

    @Override
    public void update(int xBounds, int yBounds, List<GameObject> gameObjectList, boolean[] keyStates, double[] mouseStates) {
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
        gc.setFill(Color.CYAN);
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

}
