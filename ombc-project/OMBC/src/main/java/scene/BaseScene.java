/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import javafx.scene.Parent;
import service.SceneDirectorService;

/**
 * This is the abstract base class for all scenes in the app.
 * 
 * @author Jonkke
 */
public abstract class BaseScene {
    SceneDirectorService sds;
    
    public BaseScene(SceneDirectorService sds) {
        this.sds = sds;
    }
    
    public abstract Parent getRoot();
}
