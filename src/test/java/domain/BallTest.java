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
 * @author Jonkke
 */
public class BallTest {

    Ball ball;
    List<GameObject> gameObjList;
    int fakeAreaWidth = 1000;
    int fakeAreaHeight = 1000;

    public BallTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ball = new Ball(500, 500, 50);
        gameObjList = new ArrayList();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void ballMovesWhenVelocityXIsAddedAndUpdateIsCalled() {
        double oldBallX = ball.getX();
        ball.setVelocityX(5);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        assertEquals(oldBallX + 15, ball.getX(), 0.1);
    }

    @Test
    public void ballMovesWhenVelocityYIsAddedAndUpdateIsCalled() {
        double oldBallY = ball.getY();
        ball.setVelocityY(-18);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        assertEquals(oldBallY + (4 * -18), ball.getY(), 0.1);
    }

    @Test
    public void ballStaysStationaryWhenNoVelocityAndUpdateIsCalled() {
        double oldBallX = ball.getX();
        double oldBallY = ball.getY();
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        assertEquals(oldBallX, ball.getX(), 0.1);
        assertEquals(oldBallY, ball.getY(), 0.1);
    }

    @Test
    public void ballChangesDirectionWhenItHitsVerticalLowerBounds() {
        ball.setVelocityX(-100);
        double oldVelocityX = ball.getVelocityX();
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        assertEquals(-oldVelocityX, ball.getVelocityX(), 0.1);
    }

    @Test
    public void ballChangesDirectionWhenItHitsHorizontalLowerBounds() {
        ball.setVelocityY(100);
        double oldVelocityY = ball.getVelocityY();
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        assertEquals(-oldVelocityY, ball.getVelocityY(), 0.1);
    }

    @Test
    public void ballChangesDirectionWhenItHitsVerticalUpperBounds() {
        ball.setVelocityX(100);
        double oldVelocityX = ball.getVelocityX();
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        assertEquals(-oldVelocityX, ball.getVelocityX(), 0.1);
    }

    @Test
    public void ballChangesDirectionWhenItHitsHorizontalUpperBounds() {
        ball.setVelocityY(100);
        double oldVelocityY = ball.getVelocityY();
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, gameObjList, null, null);
        assertEquals(-oldVelocityY, ball.getVelocityY(), 0.1);
    }
}
