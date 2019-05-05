/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ScoreDao;
import domain.Ball;
import domain.Brick;
import domain.GameObject;
import domain.Paddle;
import domain.Player;
import domain.Score;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import scene.GameScene;
import util.CanvasUtils;
import util.Utils;

/**
 * This service holds all the information pertaining to a single game session,
 * such as list of the game objects, current player, point counter and update()
 * and render() methods for handling updates and renders for all game objects
 * during a single update or render cycle.
 *
 * @author Jonkke
 */
public class GameStateService {

    // Link to game scene
    private GameScene gameScene;

    // Link to DatabaseService
    private DatabaseService databaseService;

    // Game properties
    private Player activePlayer;
    private boolean gameActive;
    private boolean gameEnded;

    // Game objects
    private Ball ball;
    private Paddle paddle;
    private List<GameObject> gameObjectList;

    // Game area dimensions
    private int xBounds;
    private int yBounds;

    // Game status
    private int ballCount;
    private int points;
    private double ballSpeed;
    private long runTime;
    private boolean[] phases;
    private long newBallCountDownTime;

    // Active keys & mouse position vector
    Map<KeyCode, Boolean> activeKeys;
    List<Double> mouseVector;

    public GameStateService(int width, int height, DatabaseService databaseService) {
        this.databaseService = databaseService;
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

    public void setGameSceneRef(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    /**
     * This method initializes a new game.
     */
    public void initNewGame() {
        this.ball = null;
        this.paddle = new Paddle(xBounds / 2, yBounds - 15, 150, 10);
        this.gameObjectList = new ArrayList();
        this.gameObjectList.add(paddle);

        this.ballCount = 3;
        this.points = 0;
        this.ballSpeed = 2.2;
        this.newBallCountDownTime = 3000;

        this.gameObjectList.addAll(Utils.build16by8BrickArray(this.xBounds, this.yBounds, 2));

        this.phases = new boolean[]{false, false, false, false};
        this.gameActive = true;
        this.gameEnded = false;
        this.runTime = 0;
    }

    /**
     * Returns whether there is currently an active game session or not
     *
     * @return boolean value
     */
    public boolean gameIsActive() {
        return this.gameActive;
    }

    /**
     * Starts the game
     */
    public void runGame() {
        this.gameScene.start();
    }

    /**
     * Temporarily halts the game, while keeping game state
     */
    public void haltGame() {
        this.gameScene.stop();
    }

    /**
     * Ends current game session.
     *
     * @param noSave if true, score will not be saved to highscore database
     */
    public void endGame(boolean noSave) {
        if (!this.gameActive) {
            return;
        }
        this.gameEnded = true;
        this.gameActive = false;
        this.haltGame();
        if (!noSave) {
            Score score = new Score(-1, this.points, new Date(), this.runTime, this.activePlayer.getId());
            ScoreDao sd = new ScoreDao(this.databaseService);
            sd.save(score);
        }
    }

    /**
     * Handles transitioning from one difficulty phase to the next. In harder
     * phases, ball speed is increased and paddle width decreased.
     */
    private void handlePhases() {
        if (this.points >= 80 && !this.phases[0]) {
            this.phases[0] = true;
        } else if (this.points >= 170 && !this.phases[1]) {
            this.phases[1] = true;
        } else if (this.points >= 300 && !this.phases[2]) {
            this.phases[2] = true;
        } else if (this.points >= 420 && !this.phases[3]) {
            this.phases[3] = true;
        } else {
            return;
        }
        this.ballSpeed *= 1.3;
        this.ball.setVelocity(ballSpeed);
        this.paddle.setWidth((int) (this.paddle.getWidth() * 0.8));
    }

    /**
     * If a ball was destroyed, set new ball countdown timer and reduce amount
     * of balls available for play
     */
    private void handleLostBall() {
        if (this.ball.markedForDestruction()) {
            this.ballCount--;
            this.ball = null;
            this.newBallCountDownTime = 3000;
        }
    }

    /**
     * Spawns a new ball
     */
    private void spawnNewBall() {
        this.ball = new Ball(500, 300, 7);
        this.ball.disableBottomCollision();
        this.ball.setHeading(270);
        this.ball.setVelocity(ballSpeed);
        this.gameObjectList.add(this.ball);
    }

    /**
     * Checks whether or not the conditions for ending the game exist or not
     */
    private void handleEndGameCheck() {
        if (this.gameObjectList.size() == 2 || this.ballCount <= 0) {
            endGame(false);
        }
    }

    /**
     * Updates all game logic during a single update cycle iteration. This
     * includes variables pertaining to the current game session, such as
     * elapsed time, ball count, points, and also calling the update() methods
     * of every individual game object contained within the gameObjectList
     * variable of this class instance.
     *
     * @param deltaTimeMS time in milliseconds elapsed since the last update
     */
    public void update(long deltaTimeMS) {
        this.runTime += deltaTimeMS;
        if (this.newBallCountDownTime > 0) {
            this.paddle.update(this.xBounds, this.yBounds, gameObjectList, this.activeKeys, this.mouseVector);
            this.newBallCountDownTime -= deltaTimeMS;
            return;
        }
        if (this.ball == null && this.ballCount > 0) {
            spawnNewBall();
        }
        for (GameObject obj : gameObjectList) {
            obj.update(this.xBounds, this.yBounds, gameObjectList, this.activeKeys, this.mouseVector);
        }
        this.gameObjectList.forEach(ob -> {
            if (ob instanceof Brick && ob.markedForDestruction()) {
                this.points += ((Brick) ob).getValue();
            }
        });
        this.gameObjectList = this.gameObjectList.stream().filter(obj -> !obj.markedForDestruction()).collect(Collectors.toList());
        handleLostBall();
        handlePhases();
        handleEndGameCheck();
        this.activeKeys = new HashMap(); // Reset keys after each update loop, since we may update several times during one frame
    }

    /**
     * Handles the drawing of all the game objects, and also all the game UI
     * information (game stats, ball countdown timers, end game popups) on the
     * designated GraphicsContext
     *
     * @param gc GraphicsContext we're drawing to
     */
    public void draw(GraphicsContext gc) {
        CanvasUtils.fillCanvas(gc, Color.BLACK, this.xBounds, this.yBounds);
        for (GameObject obj : gameObjectList) {
            obj.draw(gc);
        }
        CanvasUtils.drawGameSceneInfo(gc, this.activePlayer, this.ballCount, this.points, this.runTime);

        if (this.newBallCountDownTime > 0 && this.ballCount > 0) {
            CanvasUtils.drawBallCountDownTimer(gc, this.xBounds, this.yBounds, this.newBallCountDownTime);
        }

        if (this.gameEnded) {
            CanvasUtils.drawEndGamePopUp(gc, this.xBounds, this.yBounds, this.points, this.runTime);
        }
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

}
