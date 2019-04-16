/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jonask
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
        ball.setVelocityY(5);
        boolean[] keyStates = {false, false, false, false, false};
        double[] mouseStates = {250.4, 605.34};
        for (int i = 0; i < 5; i++) {
            gameObjectList.forEach(go -> go.update(1000, 1000, gameObjectList, keyStates, mouseStates));
        }
        assertEquals(-5, ball.getVelocityY());
    }

    @Test
    public void paddleMovesToMouseXPosition() {
        boolean[] keyStates = {false, false, false, false, false};
        double[] mouseStates = {250.4, 605.34};
        gameObjectList.forEach(go -> go.update(1000, 1000, gameObjectList, keyStates, mouseStates));
        assertEquals((int) mouseStates[0], paddle.getX());
    }
}
