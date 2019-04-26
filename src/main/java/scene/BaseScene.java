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
 * All hold a connection to an instance of a SceneDirectorService class,
 * which orchestrates the chgangin of scenes within the application.
 * 
 * @author Jonkke
 */
public abstract class BaseScene {
    SceneDirectorService sds;
    
    public BaseScene(SceneDirectorService sds) {
        this.sds = sds;
    }
    
    /**
     * This method is used to return the Parent object of the current scene.
     * This object is used to switch between scenes.
     * 
     * @return Parent object
     */
    public abstract Parent getRoot();
}
