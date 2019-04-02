/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

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
public class BallTest {

    Ball ball;
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
    }

    @After
    public void tearDown() {
    }

    @Test
    public void ballMovesWhenVelocityXIsAddedAndUpdateIsCalled() {
        int oldBallX = ball.getX();
        ball.setVelocityX(5);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        assertEquals(oldBallX + 15, ball.getX());
    }

    @Test
    public void ballMovesWhenVelocityYIsAddedAndUpdateIsCalled() {
        int oldBallY = ball.getY();
        ball.setVelocityY(-18);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        assertEquals(oldBallY + (4 * -18), ball.getY());
    }

    @Test
    public void ballStaysStationaryWhenNoVelocityAndUpdateIsCalled() {
        int oldBallX = ball.getX();
        int oldBallY = ball.getY();
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        assertEquals(oldBallX, ball.getX());
        assertEquals(oldBallY, ball.getY());
    }

    @Test
    public void ballChangesDirectionWhenItHitsVerticalLowerBounds() {
        ball.setVelocityX(-100);
        int oldVelocityX = ball.getVelocityX();
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        assertEquals(-oldVelocityX, ball.getVelocityX());
    }

    @Test
    public void ballChangesDirectionWhenItHitsHorizontalLowerBounds() {
        ball.setVelocityY(100);
        int oldVelocityY = ball.getVelocityY();
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        assertEquals(-oldVelocityY, ball.getVelocityY());
    }

    @Test
    public void ballChangesDirectionWhenItHitsVerticalUpperBounds() {
        ball.setVelocityX(100);
        int oldVelocityX = ball.getVelocityX();
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        assertEquals(-oldVelocityX, ball.getVelocityX());
    }

    @Test
    public void ballChangesDirectionWhenItHitsHorizontalUpperBounds() {
        ball.setVelocityY(100);
        int oldVelocityY = ball.getVelocityY();
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        ball.update(fakeAreaWidth, fakeAreaHeight, null, null, null);
        assertEquals(-oldVelocityY, ball.getVelocityY());
    }
}
