/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.List;
import javafx.scene.canvas.GraphicsContext;

/**
 * This abstract class represents a generic game object. All game objects are
 * derived from this class. Generic attributes for all game objects include
 * position, size, whether they are affected by gravity (or other physical
 * forces), their update() -method and their draw() -method.
 *
 * @author Jonkke
 */
public abstract class GameObject {

    protected int x;
    protected int y;
    protected int velocityX;
    protected int velocityY;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocityX = 1;
        this.velocityY = 1;
    }

    abstract public void update(int xBounds, int yBounds, List<GameObject> gameObjectList, boolean[] keyStates, double[] mouseStates);

    abstract public void draw(GraphicsContext gc);

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    
    public void accelerateX(int accX) {
        this.velocityX += accX;
    }
    
    public void accelerateY(int accY) {
        this.velocityY += accY;
    }

}
