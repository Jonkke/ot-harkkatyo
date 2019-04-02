/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import scene.GameScene;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import scene.BaseScene;
import scene.MenuScene;

/**
 * This service is responsible for switching between the various scenes in the
 * app. In reality, no JavaFX scenes are changed, only root nodes that they
 * contain.
 *
 * @author Jonkke
 */
public class SceneDirectorService {
    
    private int canvasWidth;
    private int canvasHeight;

    private GameStateService gss;
    private Scene scene;
    private BaseScene activeScene;
    private GameScene gameScene;
    private MenuScene menuScene;

    public SceneDirectorService() {
        this.canvasWidth = 1024;
        this.canvasHeight = 768;
        this.gss = new GameStateService(canvasWidth, canvasHeight);
        this.gameScene = new GameScene(this, this.gss);
        this.menuScene = new MenuScene(this);
        this.scene = new Scene(this.gameScene.getRoot());
    }
    
    public void setGameScene() {
        this.scene.setRoot(this.gameScene.getRoot());
        this.activeScene = gameScene;
        this.scene.addEventHandler(MouseEvent.MOUSE_MOVED, this.gss);
        this.scene.addEventHandler(MouseEvent.MOUSE_MOVED, this.gss);
        this.gameScene.start();
    }

    public void setMenuScene() {
        this.scene.setRoot(this.menuScene.getRoot());
        this.activeScene = menuScene;
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
}
