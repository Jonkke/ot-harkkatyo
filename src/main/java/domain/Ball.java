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
    public void update(int xBounds, int yBounds, List<GameObject> gameObjectList, Map<KeyCode, Boolean> activeKeys, List<Double> mouseVector) {

        for (GameObject gameObj : gameObjectList) {
            if (gameObj == this || gameObj == null || gameObj.colObj == null) {
                continue;
            }
            if (this.colObj.checkCollision(gameObj.colObj)) {
                if (gameObj instanceof Ball) {
                    respondToBallCollision((Ball) gameObj);
                } else if (gameObj instanceof Brick) {
                    respondToBrickCollision((Brick) gameObj);
                } else if (gameObj instanceof Paddle) {
                    respondToPaddleCollision((Paddle) gameObj);
                }
            }
        }
        respondToBoundsCollision(xBounds, yBounds);
    }

    private void respondToBoundsCollision(int xBounds, int yBounds) {
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
        this.updateHeadingAngle();

        enforceBallInsideGameArea(xBounds, yBounds);

        if (this.y - this.radius > yBounds) {
            this.markForDestruction();
        }
    }

    private void enforceBallInsideGameArea(int xBounds, int yBounds) {
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
    }

    private void respondToBallCollision(Ball ball) {
        double yMem = this.velocityY;
        double xMem = this.velocityX;
        this.velocityX = ball.velocityX;
        this.velocityY = ball.velocityY;
        ball.velocityX = xMem;
        ball.velocityY = yMem;
    }

    private void respondToBrickCollision(Brick brick) {
        brick.setWasHitBy(this);
        int side = this.colObj.getSideHit(brick.colObj);
        int brickWidth = brick.getWidth();
        int brickHeight = brick.getHeight();

        switch (side) {
            case 0:
                this.setY(brick.getY() - brickHeight / 2 - this.radius - 1);
                this.velocityY = -this.velocityY;
                break;
            case 2:
                this.setY(brick.getY() + brickHeight / 2 + this.radius + 1);
                this.velocityY = -this.velocityY;
                break;
            case 1:
                this.setX(brick.getX() + brickWidth / 2 + this.radius + 1);
                this.velocityX = -this.velocityX;
                break;
            default:
                this.setX(brick.getX() - brickWidth / 2 - this.radius - 1);
                this.velocityX = -this.velocityX;
                break;
        }
    }

    private void respondToPaddleCollision(Paddle paddle) {
        paddle.setWasHitBy(this);
        int side = this.colObj.getSideHit(paddle.colObj);
        int paddleWidth = paddle.getWidth();
        int paddleHeight = paddle.getHeight();

        switch (side) {
            case 0:
                this.setY(paddle.getY() - paddleHeight / 2 - this.radius - 1);
                // When hitting the paddle, redirect the ball according to the part of paddle that was hit
                double newAngleFactor = (this.x - (paddle.x - paddle.getWidth() / 2)) / paddle.getWidth();
                newAngleFactor = Math.max(0, Math.min(1, newAngleFactor));
                double newAngle = 45 + (1 - newAngleFactor) * 90;
                this.setHeading(newAngle);
                break;
            case 2:
                this.setY(paddle.getY() + paddleHeight / 2 + this.radius + 1);
                this.velocityY = -this.velocityY;
                break;
            case 1:
                this.setX(paddle.getX() + paddleWidth / 2 + this.radius + 1);
                this.velocityX = -this.velocityX;
                break;
            default:
                this.setX(paddle.getX() - paddleWidth / 2 - this.radius - 1);
                this.velocityX = -this.velocityX;
                break;
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
