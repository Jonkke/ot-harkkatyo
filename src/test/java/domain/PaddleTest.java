/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyCode;
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
public class PaddleTest {

    Ball ball;
    Paddle paddle;
    List<GameObject> gameObjectList;

    public PaddleTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ball = new Ball(500, 920, 5);
        paddle = new Paddle(500, 950, 200, 50);
        gameObjectList = new ArrayList();
        gameObjectList.add(ball);
        gameObjectList.add(paddle);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void padddleBouncesBallBack() {
        ball.setVelocity(5);
        ball.setHeading(270);
        Map<KeyCode, Boolean> activeKeys = new HashMap();
        List<Double> mouseVector = new ArrayList();
        mouseVector.add(0, 0.0);
        for (int i = 0; i < 5; i++) {
            gameObjectList.forEach(go -> go.update(1000, 1000, gameObjectList, activeKeys, mouseVector));
        }
        assertEquals(-5, ball.getVelocityY(), 0.1);
    }

    @Test
    public void paddleMovesToMouseXPosition() {
        Map<KeyCode, Boolean> activeKeys = new HashMap();
        List<Double> mouseVector = new ArrayList();
        mouseVector.add(0, 250.4);
        mouseVector.add(1, 605.34);
        gameObjectList.forEach(go -> go.update(1000, 1000, gameObjectList, activeKeys, mouseVector));
        assertEquals(mouseVector.get(0), paddle.getX(), 0.1);
    }
}
