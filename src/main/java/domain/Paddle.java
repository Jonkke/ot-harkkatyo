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
public class Paddle extends GameObject {

    private int width;
    private int height;
    private int moveSpeed;

    public Paddle(int x, int y) {
        super(x, y);
        this.width = 50;
        this.height = 10;
        this.moveSpeed = 15;
    }

    public Paddle(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.moveSpeed = 15;
    }

    @Override
    public void update(int xBounds, int yBounds, List<GameObject> gameObjectList, boolean[] keyStates, double[] mouseStates) {
        this.x = (int) mouseStates[0];
        if (this.x < this.width/2) this.x = this.width/2;
        if (this.x > xBounds-this.width/2) this.x = xBounds-this.width/2;
        if (keyStates[2]) {
            this.x -= this.moveSpeed;
        } else if (keyStates[3]) {
            this.x += this.moveSpeed;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(this.x-this.width/2, this.y-this.height/2, this.width, this.height);
    }

}
