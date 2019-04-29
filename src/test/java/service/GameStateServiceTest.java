/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.GameObject;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jonask
 */
public class GameStateServiceTest {
    
    GameStateService gts;
    List<GameObject> gameObjectList;
    
    public GameStateServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        gts = new GameStateService(500, 500, new DatabaseService());
        gameObjectList = gts.getGameObjectList();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void gameStateBecomesActiveWhenNewGameInitialized() {
        this.gts.initNewGame();
        assertTrue(this.gts.gameIsActive());
    }

}
