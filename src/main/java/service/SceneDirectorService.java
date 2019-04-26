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
import scene.MenuScene;
import scene.PlayerScene;
import scene.SettingsScene;

/**
 * This service is responsible for switching between the various scenes in the
 * app. In reality, no JavaFX scenes are changed, only root nodes that they
 * contain.
 *
 * @author Jonkke
 */
public class SceneDirectorService {

    private int sceneWidth;
    private int sceneHeight;

    private Map<KeyCode, Boolean> activeKeys;
    private List<Double> mouseVector;

    private DatabaseService databaseService;
    private GameStateService gameStateService;
    private Scene scene;
    private BaseScene activeScene;
    private GameScene gameScene;
    private MenuScene menuScene;
    private PlayerScene playerScene;
    private SettingsScene settingsScene;

    public SceneDirectorService() {
        this.sceneWidth = 1024;
        this.sceneHeight = 768;

        this.databaseService = new DatabaseService();
        this.databaseService.connect();
        this.gameStateService = new GameStateService(sceneWidth, sceneHeight);
        this.gameScene = new GameScene(this, this.gameStateService);
        this.menuScene = new MenuScene(this, this.gameStateService);
        this.playerScene = new PlayerScene(this, this.gameStateService, this.databaseService);
        this.settingsScene = new SettingsScene(this, this.gameStateService);
        this.scene = new Scene(this.gameScene.getRoot());

        this.activeKeys = new HashMap();
        this.gameStateService.setActiveKeys(activeKeys);
        this.scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (this.activeScene == gameScene) {
                    this.gameScene.stop();
                    this.setMenuScene();
                } else if (this.activeScene == menuScene && this.gameStateService.gameIsActive()) {
                    this.setGameScene();
                } else {
                    this.setMenuScene();
                }
            }
            this.activeKeys.put(event.getCode(), true);
            this.gameStateService.setActiveKeys(activeKeys);
            this.activeKeys = new HashMap();
        });

        this.mouseVector = new ArrayList();
        this.mouseVector.add(0.0);
        this.mouseVector.add(0.0);
        this.gameStateService.setMouseVector(mouseVector);
        this.scene.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            this.mouseVector.set(0, event.getX());
            this.mouseVector.set(1, event.getY());
        });
    }

    public void setGameScene() {
        this.scene.setRoot(this.gameScene.getRoot());
        this.scene.setCursor(Cursor.NONE);
        this.activeScene = gameScene;
        if (!this.gameStateService.gameIsActive()) {
            this.gameStateService.initNewGame();
        }
        this.gameScene.start();
    }

    public void setMenuScene() {
        this.scene.setCursor(Cursor.DEFAULT);
        this.scene.setRoot(this.menuScene.getRoot());
        this.activeScene = this.menuScene;
    }

    public void setPlayerMenuScene() {
        this.scene.setRoot(this.playerScene.getRoot());
        this.activeScene = this.playerScene;
    }

    public void setSettingsMenuScene() {
        this.scene.setRoot(this.settingsScene.getRoot());
        this.activeScene = this.settingsScene;
    }

    public Scene getScene() {
        return this.scene;
    }

    public void changeScene() {
        if (activeScene == menuScene) {
            setGameScene();
        } else {
            this.gameScene.stop();
            setMenuScene();
        }
    }

    public void exitGame() {
        Platform.exit();
        System.exit(0);
    }

    public int getSceneWidth() {
        return this.sceneWidth;
    }

    public int getSceneHeight() {
        return this.sceneHeight;
    }
}
