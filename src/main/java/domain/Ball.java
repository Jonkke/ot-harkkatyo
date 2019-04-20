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
    private boolean ignoreUpperYBound;

    public Ball(int x, int y, int radius) {
        super(x, y);
        this.radius = radius;
        this.colObj = new CollisionObject(this.radius, this);
    }

    @Override
    public void update(int xBounds, int yBounds, List<GameObject> gameObjectList, boolean[] keyStates, double[] mouseStates) {

        for (GameObject gameObj : gameObjectList) {
            if (gameObj == this || gameObj == null || gameObj.colObj == null) {
                continue;
            }

            if (this.colObj.checkCollision(gameObj.colObj)) {
                if (gameObj instanceof Ball) {
                    double yMem = this.velocityY;
                    double xMem = this.velocityX;
                    this.velocityX = ((Ball) gameObj).velocityX;
                    this.velocityY = ((Ball) gameObj).velocityY;
                    ((Ball) gameObj).velocityX = xMem;
                    ((Ball) gameObj).velocityY = yMem;
                } else if (gameObj instanceof Brick || gameObj instanceof Paddle) {
                    gameObj.setWasHitBy(this);
                    int side = this.colObj.whichSideHit(gameObj.colObj);  // Get the side/direction we hit the object to
                    int gameObjWidth;
                    int gameObjHeight;
                    gameObjWidth = gameObj instanceof Brick ? ((Brick) gameObj).getWidth() : ((Paddle) gameObj).getWidth();
                    gameObjHeight = gameObj instanceof Brick ? ((Brick) gameObj).getHeight() : ((Paddle) gameObj).getHeight();

                    // Here the ball gets pushed outside of the colliding object, to the opposite direction of the hit
                    if (side == 0) {
                        this.setY(gameObj.getY() - gameObjHeight / 2 - this.radius - 1);

                        // When hitting the paddle, redirect the ball according to the part of paddle that was hit
                        if (gameObj instanceof Paddle) {
                            double newAngleFactor = (this.x - (gameObj.x - ((Paddle) gameObj).getWidth() / 2)) / ((Paddle) gameObj).getWidth();
                            newAngleFactor = Math.max(0, Math.min(1, newAngleFactor));
                            double newAngle = 45 + (1 - newAngleFactor) * 90;
                            this.setHeading(newAngle);
                        } else {
                            this.velocityY = -this.velocityY;
                        }
                    } else if (side == 2) {
                        this.setY(gameObj.getY() + gameObjHeight / 2 + this.radius + 1);
                        this.velocityY = -this.velocityY;
                    } else if (side == 1) {
                        this.setX(gameObj.getX() + gameObjWidth / 2 + this.radius + 1);
                        this.velocityX = -this.velocityX;
                    } else {
                        this.setX(gameObj.getX() - gameObjWidth / 2 - this.radius - 1);
                        this.velocityX = -this.velocityX;
                    }
                }
            }
        }

        if (this.colObj.checkXBoundsCollision(xBounds)) {
            this.velocityX = -this.velocityX;
        }
        if (this.colObj.checkYBoundsCollision(yBounds)) {
            if (!this.ignoreUpperYBound) {
                this.velocityY = -this.velocityY;
            } else if (this.y - this.radius <= 0) {
                this.velocityY = -this.velocityY;
            }
        }
        
        this.x += this.velocityX;
        this.y += this.velocityY;

        // Enforce the ball to always stay inside of the bounds
        if (this.x + this.radius > xBounds) {
            this.x = xBounds - this.radius;
        }
        if (this.x - this.radius < 0) {
            this.x = this.radius;
        }
        if (!this.ignoreUpperYBound && this.y + this.radius > yBounds) {
            this.y = yBounds - this.radius;
        }
        if (this.y - this.radius < 0) {
            this.y = this.radius;
        }

        // If ball traveled past the upper Y bounds (=bottom), mark it for destruction
        if (this.y - this.radius > yBounds) {
            this.markForDestruction();
        }
    }

    /**
     * Disables the collision test for the bottom edge of the game area for this
     * ball.
     */
    public void disableBottomCollision() {
        this.ignoreUpperYBound = true;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillOval(this.x - this.radius, this.y - this.radius, this.radius * 2, this.radius * 2);
    }

}
