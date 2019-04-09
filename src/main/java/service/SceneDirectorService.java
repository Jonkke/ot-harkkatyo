/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javafx.application.Platform;
import javafx.scene.Cursor;
import scene.GameScene;
import javafx.scene.Scene;
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

    private GameStateService gss;
    private Scene scene;
    private BaseScene activeScene;
    private GameScene gameScene;
    private MenuScene menuScene;
    private PlayerScene playerScene;
    private SettingsScene settingsScene;

    public SceneDirectorService() {
        this.sceneWidth = 1024;
        this.sceneHeight = 768;
        this.gss = new GameStateService(sceneWidth, sceneHeight);
        this.gameScene = new GameScene(this, this.gss);
        this.menuScene = new MenuScene(this, this.gss);
        this.playerScene = new PlayerScene(this, this.gss);
        this.settingsScene = new SettingsScene(this, this.gss);
        this.scene = new Scene(this.gameScene.getRoot());
    }

    public void setGameScene() {
        this.scene.setRoot(this.gameScene.getRoot());
        this.scene.setCursor(Cursor.NONE);
        this.activeScene = gameScene;
        this.scene.addEventHandler(MouseEvent.MOUSE_MOVED, this.gss);
        this.gameScene.start();
    }

    public void setMenuScene() {
        if (this.activeScene == this.gameScene) {
            gameScene.stop();
        }
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
    
    // TODO: Both SceneDirectorService and GameStateService have width & height information. Do something about this.
    public int getSceneWidth() {
        return this.sceneWidth;
    }
    
    public int getSceneHeight() {
        return this.sceneHeight;
    }
}
