/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import javafx.scene.Parent;
import service.SceneDirectorService;

/**
 *
 * @author Jonkke
 */
public class PlayerScene extends BaseScene {

    // TODO: Create player creation / loading scene
    // TODO: Create a database to hold player information, along with DAO class and other necessary stuff
    
    public PlayerScene(SceneDirectorService sds) {
        super(sds);
    }

    @Override
    public Parent getRoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
