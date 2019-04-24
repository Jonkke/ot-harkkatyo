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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

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
// TODO: Clear up this EventHandler thingy...
public class GameStateService implements /*EventHandler<KeyEvent>*/ EventHandler<MouseEvent> {
    
    // Game objects
    private Ball ball;
    private Paddle paddle;
    private List<GameObject> gameObjectList;

    // Game area dimensions
    private int canvasWidth;
    private int canvasHeight;

    // Game status
    private int lostBallCount;
    private int points;
    private double ballSpeed;

    // Active keys
    // This array contains the current states for all supported keys for the game
    // 0=UP, 1=DOWN, 2=LEFT, 3=RIGHT, 5=HALT
    private boolean[] keyStates = new boolean[5];
    private double[] mouseStates = new double[2];

    public GameStateService(int width, int height) {
        this.canvasWidth = width;
        this.canvasHeight = height;
        this.ball = new Ball(500, 200, 7);  // TODO: Randomize ball spawning position & direction, new balls too
        this.paddle = new Paddle(canvasWidth / 2, canvasHeight - 15, 80, 10);
        this.gameObjectList = new ArrayList();
        this.gameObjectList.add(ball);
        this.gameObjectList.add(paddle);

        this.lostBallCount = 0;
        this.points = 0;
        this.ballSpeed = 2.2;

        this.ball.disableBottomCollision();
        this.ball.setHeading(270);
        this.ball.setVelocity(ballSpeed);

        this.gameObjectList.addAll(buildBrickArray(16, 8, 2));
    }

    public void update() {
        for (GameObject obj : gameObjectList) {
            obj.update(this.canvasWidth, this.canvasHeight, gameObjectList, keyStates, mouseStates);
        }
        this.gameObjectList.forEach(ob -> {
            if (ob instanceof Brick && ob.markedForDestruction()) {
                this.points += ((Brick) ob).getValue();
            }
        });
        this.gameObjectList = this.gameObjectList.stream().filter(obj -> !obj.markedForDestruction()).collect(Collectors.toList());
        if (this.ball.markedForDestruction()) {
            this.ball = new Ball(200, 200, 7);
            this.ball.disableBottomCollision();
            this.ball.setHeading(270);
            this.ball.setVelocity(ballSpeed);
            this.gameObjectList.add(this.ball);

            this.lostBallCount++;
        }
        this.keyStates = new boolean[5]; // Reset keys after each update loop, since we may update several times during one frame
    }

    public void draw(GraphicsContext gc) {
        for (GameObject obj : gameObjectList) {
            obj.draw(gc);
        }
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("monospace", FontWeight.LIGHT, FontPosture.REGULAR, 15));
        gc.fillText("Player: ", 10, 25); // TODO: HIGH PRIORITY: Player!
        gc.fillText("Lost balls: " + this.lostBallCount, 10, 40);
        gc.fillText("Score: " + this.points, 10, 55); // TODO: HIGH PRIORITY: Score!

    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
            this.mouseStates[0] = event.getX();
            this.mouseStates[1] = event.getY();
        }
    }

    public List<GameObject> getGameObjectList() {
        return this.gameObjectList;
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
            Color color
                    = y == 0 || y == 1 ? Color.YELLOW
                            : y == 2 || y == 3 ? Color.GREEN
                                    : y == 4 || y == 5 ? Color.ORANGE
                                            : Color.RED;
            int value = y == 0 || y == 1 ? 7
                    : y == 2 || y == 3 ? 5
                            : y == 4 || y == 5 ? 3 : 1;
            for (int x = 0; x < columns; x++) {
                brickArray.add(new Brick(posX, posY, brickWidth, brickHeight, color, 1, value));
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
