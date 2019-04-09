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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import service.GameStateService;
import service.SceneDirectorService;

/**
 *
 * @author Jonkke
 */
public class SettingsScene extends BaseScene {

    private GameStateService gss;
    private VBox root;

    public SettingsScene(SceneDirectorService sds, GameStateService gss) {
        super(sds);
        this.gss = gss;
        this.root = new VBox(10);
        this.root.setMinSize(sds.getSceneWidth(), sds.getSceneHeight());
        this.root.setAlignment(Pos.CENTER);
        addPlayerMenuItems(this.root);
    }
    
    private void addPlayerMenuItems(VBox root) {
        Label testLabel = new Label();
        testLabel.setText("Settings scene. Nothing here yet...");
        
        Button backBtn = new Button();
        backBtn.setText("Back to main menu");
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sds.setMenuScene();
            }
        });
        
        root.getChildren().add(testLabel);
        root.getChildren().add(backBtn);
    }

    @Override
    public Parent getRoot() {
        return this.root;
    }

}
