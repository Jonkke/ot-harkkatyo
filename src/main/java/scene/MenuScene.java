/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import service.GameStateService;
import service.SceneDirectorService;

/**
 * This class represents the main menu scene. This is the first scene shown to
 * the user when they launch the app.
 *
 * @author Jonkke
 */
public class MenuScene extends BaseScene {

    private GameStateService gameStateService;
    private Canvas canvas;
    private VBox root;

    public MenuScene(SceneDirectorService sceneDirectorService, GameStateService gameStateService) {
        super(sceneDirectorService);
        this.gameStateService = gameStateService;
        buildMenu();
    }

    public void buildMenu() {
        this.root = new VBox(10);
        this.root.setMinSize(sceneDirectorService.getSceneWidth(), sceneDirectorService.getSceneHeight());
        this.root.setAlignment(Pos.CENTER);
        addMenuItems(this.root);
    }

    private void addMenuItems(VBox root) {
        Label selectedPlayerLabel = new Label();
        selectedPlayerLabel.setText("Currently selected player: " + this.gameStateService.getActivePlayer().getName());

        Button continueGameBtn = new Button();
        continueGameBtn.setText("Continue");
        continueGameBtn.setOnAction(event -> {
            sceneDirectorService.setGameScene();
        });

        Button newGameBtn = new Button();
        newGameBtn.setText("New game");
        newGameBtn.setOnAction(event -> {
            gameStateService.initNewGame();
            gameStateService.runGame();
            sceneDirectorService.setGameScene();
        });

        Button playerMenuBtn = new Button();
        playerMenuBtn.setText("Player menu");
        playerMenuBtn.setOnAction(event -> {
            this.sceneDirectorService.setPlayerMenuScene();
        });

        Button highscoreMenuBtn = new Button();
        highscoreMenuBtn.setText("High score");
        highscoreMenuBtn.setOnAction(event -> {
            this.sceneDirectorService.setHighscoreMenuScene();
        });

        // TODO: Either remove settings menu or create it (resolution, mouse/keyboard maybe?)
//        Button settingsMenuBtn = new Button();
//        settingsMenuBtn.setText("Settings");
//        settingsMenuBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                sceneDirectorService.setSettingsMenuScene();
//            }
//        });
        Button exitGameBtn = new Button();
        exitGameBtn.setText("Exit");
        exitGameBtn.setOnAction(event -> {
            sceneDirectorService.exitGame();
        });

        root.getChildren().add(selectedPlayerLabel);
        if (this.gameStateService.gameIsActive()) {
            root.getChildren().add(continueGameBtn);
        }
        root.getChildren().add(newGameBtn);
        root.getChildren().add(playerMenuBtn);
        root.getChildren().add(highscoreMenuBtn);
//        root.getChildren().add(settingsMenuBtn);
        root.getChildren().add(exitGameBtn);

    }

    @Override
    public Parent getRoot() {
        return this.root;
    }

}
