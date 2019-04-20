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

    protected double x;
    protected double y;
    protected double velocity;
    protected double headingAngleDegrees;
    protected double velocityX;
    protected double velocityY;
    protected CollisionObject colObj;
    protected GameObject wasHitBy;
    protected boolean destroyed; // If true, the object will be deleted during the update cycle

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    /**
     * Each inheriting game object class must implement it's own update method.
     * This method is called once for every active game object during a single
     * update cycle.
     *
     * @param xBounds The vertical upper bounds of the game area
     * @param yBounds The horizontal upper bounds of the game area
     * @param gameObjectList List of other game objects (for collision checking
     * and other interaction)
     * @param keyStates An array of currently pressed keys
     * @param mouseStates An array of currently active mouse states
     */
    abstract public void update(int xBounds, int yBounds, List<GameObject> gameObjectList, boolean[] keyStates, double[] mouseStates);

    /**
     * The draw method is used to draw this game object on the GraphicsContext
     * supplied. Each inheriting class must implement it's own draw method.
     *
     * @param gc The GraphicsContext to draw on.
     */
    abstract public void draw(GraphicsContext gc);

    /**
     * Sets the speed this object is traveling at, towards the current angular
     * value denoted by the headingAngleDegrees value.
     *
     * @param velocity new velocity value for this object
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
        double xVelocityFactor = Math.cos(this.headingAngleDegrees * Math.PI / 180);
        double yVelocityFactor = -Math.sin(this.headingAngleDegrees * Math.PI / 180);  // negative, as the Y axis grows towards the bottom
        this.setVelocityX(this.velocity * xVelocityFactor);
        this.setVelocityY(this.velocity * yVelocityFactor);
    }

    /**
     * Sets the angular heading for this object.
     *
     * @param headingAngleDegrees new heading angle for this object
     */
    public void setHeading(double headingAngleDegrees) {
        this.headingAngleDegrees = headingAngleDegrees;
        setVelocity(this.velocity);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void accelerateX(double accX) {
        this.velocityX += accX;
    }

    public void accelerateY(double accY) {
        this.velocityY += accY;
    }

    public void markForDestruction() {
        this.destroyed = true;
    }

    public boolean markedForDestruction() {
        return this.destroyed;
    }

    public void setWasHitBy(GameObject gameObj) {
        this.wasHitBy = gameObj;
    }

}
