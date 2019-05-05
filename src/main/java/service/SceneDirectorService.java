/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.scene.Cursor;
import scene.GameScene;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import scene.BaseScene;
import scene.HighscoreScene;
import scene.MenuScene;
import scene.PlayerScene;

/**
 * This service is responsible for switching between the various scenes in the
 * app. In reality, no JavaFX scenes are changed, only root nodes that they
 * contain.
 *
 * @author Jonkke
 */
public class SceneDirectorService {

    private int width;
    private int height;

    private Map<KeyCode, Boolean> activeKeys;
    private List<Double> mouseVector;

    private DatabaseService databaseService;
    private GameStateService gameStateService;
    private Scene scene;
    private BaseScene activeScene;
    private GameScene gameScene;
    private MenuScene menuScene;
    private PlayerScene playerScene;
    private HighscoreScene highscoreScene;

    public SceneDirectorService() {
        // NOTE: Do not change, this is currently the only supported resolution!
        this.width = 1024;
        this.height = 768;

        this.databaseService = new DatabaseService();
        this.databaseService.connect("gamedb");
        this.gameStateService = new GameStateService(width, height, this.databaseService);
        this.gameScene = new GameScene(this, this.gameStateService);
        this.gameStateService.setGameSceneRef(gameScene);
        this.playerScene = new PlayerScene(this, this.gameStateService, this.databaseService);
        this.menuScene = new MenuScene(this, this.gameStateService);
        this.highscoreScene = new HighscoreScene(this, this.databaseService);
        this.scene = new Scene(this.gameScene.getRoot());

        addKeyHandler();
        addMouseHandler();
    }

    private void addKeyHandler() {
        this.activeKeys = new HashMap();
        this.gameStateService.setActiveKeys(activeKeys);
        this.scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (this.activeScene == gameScene && !this.gameStateService.gameIsActive()) {
                this.menuScene.buildMenu();
                this.setMenuScene();
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                if (this.activeScene == gameScene) {
                    this.gameStateService.haltGame();
                    this.menuScene.buildMenu();
                    this.setMenuScene();
                } else if (this.activeScene == menuScene && this.gameStateService.gameIsActive()) {
                    this.setGameScene();
                    this.gameStateService.runGame();
                } else {
                    this.menuScene.buildMenu();
                    this.setMenuScene();
                }
            }
            this.activeKeys.put(event.getCode(), true);
            this.gameStateService.setActiveKeys(activeKeys);
            this.activeKeys = new HashMap();
        });
    }

    private void addMouseHandler() {
        this.mouseVector = new ArrayList();
        this.mouseVector.add(0.0);
        this.mouseVector.add(0.0);
        this.gameStateService.setMouseVector(mouseVector);
        this.scene.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            this.mouseVector.set(0, event.getX());
            this.mouseVector.set(1, event.getY());
        });
    }

    /**
     * Changes current scene to the game scene and calls the runGame() which
     * will start the game.
     */
    public void setGameScene() {
        this.scene.setRoot(this.gameScene.getRoot());
        this.scene.setCursor(Cursor.NONE);
        this.activeScene = gameScene;
        if (!this.gameStateService.gameIsActive()) {
            this.gameStateService.initNewGame();
        }
        this.gameStateService.runGame();
    }

    /**
     * Changes current scene to the main menu scene.
     */
    public void setMenuScene() {
        this.scene.setCursor(Cursor.DEFAULT);
        this.scene.setRoot(this.menuScene.getRoot());
        this.activeScene = this.menuScene;
    }

    /**
     * Changes current scene to the player selection and/or creation menu scene
     */
    public void setPlayerMenuScene() {
        this.scene.setRoot(this.playerScene.getRoot());
        this.activeScene = this.playerScene;
    }

    /**
     * Changes current scene to the high score / leaderboard menu scene.
     */
    public void setHighscoreMenuScene() {
        this.scene.setRoot(this.highscoreScene.getRoot());
        this.highscoreScene.updateHighscoreMenuItems();
        this.activeScene = this.highscoreScene;
    }

    /**
     * @return the Scene object of this SceneDirectorService instance
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Exits the game
     */
    public void exitGame() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * @return width of the containing scene object
     */
    public int getSceneWidth() {
        return this.width;
    }

    /**
     * @return height of the containing scene object
     */
    public int getSceneHeight() {
        return this.height;
    }

    /**
     * @return mouse position vector
     */
    public List<Double> getMouseVector() {
        return this.mouseVector;
    }
}
