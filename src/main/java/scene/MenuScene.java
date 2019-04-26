/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import service.GameStateService;
import service.SceneDirectorService;

/**
 * This class represents the main menu scene. This is the first scene shown
 * to the user when they launch the app.
 * 
 * @author Jonkke
 */
public class MenuScene extends BaseScene {

    private GameStateService gss;
    private Canvas canvas;
    private VBox root;

    public MenuScene(SceneDirectorService sds, GameStateService gss) {
        super(sds);
        this.gss = gss;
        this.root = new VBox(10);
        this.root.setMinSize(sds.getSceneWidth(), sds.getSceneHeight());
        this.root.setAlignment(Pos.CENTER);
        addMenuItems(this.root);
    }

    public void addMenuItems(VBox root) {
        Button newGameBtn = new Button();
        newGameBtn.setText("New game");
        newGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sds.setGameScene();
            }
        });

        Button playerMenuBtn = new Button();
        playerMenuBtn.setText("Player menu");
        playerMenuBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sds.setPlayerMenuScene();
            }
        });

        Button settingsMenuBtn = new Button();
        settingsMenuBtn.setText("Settings");
        settingsMenuBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sds.setSettingsMenuScene();
            }
        });

        Button exitGameBtn = new Button();
        exitGameBtn.setText("Exit");
        exitGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sds.exitGame();
            }
        });

        root.getChildren().add(newGameBtn);
        root.getChildren().add(playerMenuBtn);
        root.getChildren().add(settingsMenuBtn);
        root.getChildren().add(exitGameBtn);

    }

    @Override
    public Parent getRoot() {
        return this.root;
    }

}
