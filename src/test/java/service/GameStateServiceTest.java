/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.Ball;
import domain.GameObject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javafx.scene.input.KeyCode;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
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
        this.gts.initNewGame();
        this.gts.setActiveKeys(new HashMap<KeyCode, Boolean>());
        this.gts.setMouseVector(Arrays.asList(0.0, 0.0));
        gameObjectList = gts.getGameObjectList();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void gameStateBecomesActiveWhenNewGameInitialized() {
        assertTrue(this.gts.gameIsActive());
    }

    @Test
    public void gameObjectsAreCreatedWhenNewGameInitialized() {
        assertEquals(129, this.gameObjectList.size());
    }

    @Test
    public void ballGetsCreated3SecondsAfterNewGameInitialized() {
        this.gts.update(3000);
        this.gts.update(0);
        assertEquals(130, this.gameObjectList.size());
        assertTrue(this.gameObjectList.get(129) instanceof Ball);
    }

    @Test
    public void newBallCountDownStartsIfBallGoesOutOfBounds() {
        this.gts.update(3000);
        this.gts.update(0);
        this.gameObjectList.get(129).setVelocity(10);
        assertTrue(this.gts.getNewBallCountDownTime() <= 0);
        for (int i = 0; i < 100; i++) {
            this.gts.update(16);
        }
        assertTrue(this.gts.getNewBallCountDownTime() > 0);
    }

}
