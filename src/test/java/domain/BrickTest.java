/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jonkke
 */
public class BrickTest {

    Ball ball;
    Brick brick;
    List<GameObject> gameObjectList;

    public BrickTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        brick = new Brick(100, 100, 200, 50, Color.BLACK, 1, 1);
        ball = new Ball(150, 70, 4);
        gameObjectList = new ArrayList();
        gameObjectList.add(brick);
        gameObjectList.add(ball);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void brickHealthIsReducedIfHitByBall() {
        ball.setVelocityY(5);
        for (int i = 0; i < 5; i++) {
            gameObjectList.forEach(go -> go.update(1000, 1000, gameObjectList, null, null));
        }
        assertEquals(0, brick.getHealth());
    }

    @Test
    public void brickIsMarkedForDestructionIfHealthIsZeroOrLess() {
        ball.setVelocityY(5);
        for (int i = 0; i < 5; i++) {
            gameObjectList.forEach(go -> go.update(1000, 1000, gameObjectList, null, null));
        }
        assertEquals(true, brick.markedForDestruction());
    }
}
