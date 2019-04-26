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
import domain.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
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
public class GameStateService {

    private Player activePlayer;
    private boolean gameActive;

    // Game objects
    private Ball ball;
    private Paddle paddle;
    private List<GameObject> gameObjectList;

    // Game area dimensions
    private int xBounds;
    private int yBounds;

    // Game status
    private int lostBallCount;
    private int points;
    private double ballSpeed;

    // Active keys & mouse position vector
    Map<KeyCode, Boolean> activeKeys;
    List<Double> mouseVector;

    public GameStateService(int width, int height) {
        this.activePlayer = new Player(1, "default");
        this.xBounds = width;
        this.yBounds = height;
        this.gameActive = false;
    }

    public void setActiveKeys(Map<KeyCode, Boolean> activeKeys) {
        this.activeKeys = activeKeys;
    }

    public void setMouseVector(List<Double> mouseVector) {
        this.mouseVector = mouseVector;
    }

    /**
     * This method initializes a new game.
     */
    public void initNewGame() {
        this.ball = new Ball(500, 300, 7);
        this.paddle = new Paddle(xBounds / 2, yBounds - 15, 150, 10);
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
        this.gameActive = true;
    }

    public boolean gameIsActive() {
        return this.gameActive;
    }

    private void endGame() {

    }

    public void update() {
        for (GameObject obj : gameObjectList) {
            obj.update(this.xBounds, this.yBounds, gameObjectList, this.activeKeys, this.mouseVector);
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
        this.activeKeys = new HashMap(); // Reset keys after each update loop, since we may update several times during one frame
    }

    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, xBounds, yBounds);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, xBounds, yBounds);

        for (GameObject obj : gameObjectList) {
            obj.draw(gc);
        }
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("monospace", FontWeight.LIGHT, FontPosture.REGULAR, 15));
        gc.fillText("Player: " + this.activePlayer.getName(), 10, 25);
        gc.fillText("Lost balls: " + this.lostBallCount, 10, 40);
        gc.fillText("Score: " + this.points, 10, 55);
        gc.fillText("time: ", 10, 70);
    }

    public List<GameObject> getGameObjectList() {
        return this.gameObjectList;
    }

    public Player getActivePlayer() {
        return this.activePlayer;
    }

    public void setActivePlayer(Player player) {
        this.activePlayer = player;
    }

    public int getCanvasWidth() {
        return this.xBounds;
    }

    public int getCanvasHeight() {
        return this.yBounds;
    }

    public List<GameObject> buildBrickArray(int columns, int rows, int gapSize) {
        List<GameObject> brickArray = new ArrayList();
        if (columns < 1 || rows < 1) {
            return brickArray;
        }
        int brickWidth = (int) (this.xBounds * 0.7 - gapSize * (columns - 1)) / columns;
        int brickHeight = (int) (this.yBounds * 0.2 - gapSize * (rows - 1)) / rows;
        int posXBase = (int) (this.xBounds * 0.150) + (int) (brickWidth / 2);
        int posX = posXBase;
        int posY = (int) (this.yBounds * 0.1);
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

}
