/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import service.SceneDirectorService;

/**
 *
 * @author Jonkke
 */
public class MenuScene extends BaseScene {

    private int width;
    private int height;
    private Canvas canvas;
    private Group root;

    public MenuScene(SceneDirectorService sds) {
        super(sds);
        this.width = 1024;
        this.height = 768;
        this.root = new Group();
        this.canvas = new Canvas(width, height);
        this.root.getChildren().add(canvas);
    }

    @Override
    public Group getRoot() {
        return this.root;
    }

}
