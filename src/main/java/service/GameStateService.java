/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.Ball;
import domain.GameObject;
import domain.Paddle;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * This service holds all the information pertaining to a single game session,
 * such as list of the game objects, current player, point counter and update()
 * and render() methods for handling updates and renders for all game objects
 * during a single update or render cycle.
 *
 * This class also implements the EventHandler interface, so that KeyEvents and
 * MouseEvents can easily update the current game state.
 *
 * @author Jonkke
 */
public class GameStateService implements /*EventHandler<KeyEvent>*/ EventHandler<MouseEvent> {

    // Game objects
    private Ball ball;
    private Paddle paddle;
    private List<GameObject> gameObjectList;

    // Game area dimensions
    private int canvasWidth;
    private int canvasHeight;

    // Active keys
    // This array contains the current states for all supported keys for the game
    // 0=UP, 1=DOWN, 2=LEFT, 3=RIGHT, 5=HALT
    private boolean[] keyStates = new boolean[5];
    private double[] mouseStates = new double[2];

    public GameStateService(int width, int height) {
        this.canvasWidth = width;
        this.canvasHeight = height;
        this.ball = new Ball(200, 200, 10);
        this.ball.setVelocityX(3);
        this.ball.setVelocityY(5);
        this.paddle = new Paddle(canvasWidth / 2, canvasHeight - 15, 150, 20);
        this.gameObjectList = new ArrayList();
        this.gameObjectList.add(ball);
        this.gameObjectList.add(paddle);
    }

    public void update() {
        for (GameObject obj : gameObjectList) {
            obj.update(this.canvasWidth, this.canvasHeight, gameObjectList, keyStates, mouseStates);
        }
        this.keyStates = new boolean[5]; // Reset keys after each update loop, since we may update several times during one frame
    }

    public void draw(GraphicsContext gc) {
        for (GameObject obj : gameObjectList) {
            obj.draw(gc);
        }
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
            this.mouseStates[0] = event.getX();
            this.mouseStates[1] = event.getY();
        }
    }

//    @Override
//    public void handle(KeyEvent event) {
//        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
//            switch (event.getCode()) {
//                case UP:
//                    this.keyStates[0] = true;
//                    break;
//                case DOWN:
//                    this.keyStates[1] = true;
//                    break;
//                case LEFT:
//                    this.keyStates[2] = true;
//                    break;
//                case RIGHT:
//                    this.keyStates[3] = true;
//                    break;
//                case SPACE:
//                    this.keyStates[4] = true;
//            }
//        } else {
//            switch (event.getCode()) {
//                case UP:
//                    this.keyStates[0] = false;
//                    break;
//                case DOWN:
//                    this.keyStates[1] = false;
//                    break;
//                case LEFT:
//                    this.keyStates[2] = false;
//                    break;
//                case RIGHT:
//                    this.keyStates[3] = false;
//                    break;
//            }
//        }
//    }
    public int getCanvasWidth() {
        return this.canvasWidth;
    }

    public int getCanvasHeight() {
        return this.canvasHeight;
    }

}
