/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.Ball;
import domain.Brick;
import domain.GameObject;
import domain.Paddle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
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
        this.ball = new Ball(200, 200, 15);
        this.ball.setVelocityX(1);
        this.ball.setVelocityY(3);
        this.paddle = new Paddle(canvasWidth / 2, canvasHeight - 15, 150, 20);
        this.gameObjectList = new ArrayList();
        this.gameObjectList.add(ball);
        this.gameObjectList.add(paddle);

        this.gameObjectList.addAll(buildBrickArray(8, 4, 2));
    }

    public void update() {
        for (GameObject obj : gameObjectList) {
            obj.update(this.canvasWidth, this.canvasHeight, gameObjectList, keyStates, mouseStates);
        }
        this.gameObjectList = this.gameObjectList.stream().filter(obj -> !obj.markedForDestruction()).collect(Collectors.toList());
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

    // TODO: Isolate to a more sensible class?
    public List<GameObject> buildBrickArray(int columns, int rows, int gapSize) {
        List<GameObject> brickArray = new ArrayList();
        if (columns < 1 || rows < 1) {
            return brickArray;
        }
        int brickWidth = (int) (this.canvasWidth * 0.7 - gapSize * (columns - 1)) / columns;
        int brickHeight = (int) (this.canvasHeight * 0.2 - gapSize * (rows - 1)) / rows;
        int posXBase = (int) (this.canvasWidth * 0.150) + (int) (brickWidth / 2);
        int posX = posXBase;
        int posY = (int) (this.canvasHeight * 0.1);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                brickArray.add(new Brick(posX, posY, brickWidth, brickHeight, 1));
                posX += brickWidth + gapSize;
            }
            posX = posXBase;
            posY += brickHeight + gapSize;
        }
        return brickArray;
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
