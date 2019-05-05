/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.Player;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * This class contains methods for drawing UI elements and other replicable
 * non-game objcet related stuff on a canvas. A GraphicsContext must be supplied
 * to all methods called from this class.
 *
 * @author Jonkke
 */
public class CanvasUtils {

    /**
     * Fills the canvas with the chosen color
     *
     * @param gc
     * @param width
     * @param height
     */
    public static void fillCanvas(GraphicsContext gc, Color color, int width, int height) {
        gc.clearRect(0, 0, width, height);
        gc.setFill(color);
        gc.fillRect(0, 0, width, height);
    }

    /**
     * Used to draw game information to the top-left corner of the game screen.
     *
     * @param gc GraphicsContext to draw on
     * @param activePlayer Currently active player
     * @param ballCount Amount of remaining balls
     * @param points Current points
     * @param runTime Current running time, in milliseconds
     */
    public static void drawGameSceneInfo(GraphicsContext gc, Player activePlayer, int ballCount, int points, long runTime) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("monospace", FontWeight.LIGHT, FontPosture.REGULAR, 15));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.BASELINE);
        gc.fillText("Player: " + activePlayer.getName(), 10, 25);
        gc.fillText("Balls left: " + (ballCount-1), 10, 40);
        gc.fillText("Score: " + points, 10, 55);
        gc.fillText("time: " + Utils.getFormattedTime(runTime), 10, 70);
    }

    /**
     * Used to draw the popup dialog that is show when the game has come to an
     * end.
     *
     * @param gc GraphicsContext to draw on
     * @param width Width of the canvas
     * @param height Height of the canvas
     * @param points Current Points
     * @param runTime Current running time, in milliseconds
     */
    public static void drawEndGamePopUp(GraphicsContext gc, int width, int height, int points, long runTime) {
        int boxX1 = (int) (width * 0.30);
        int boxX2 = (int) (width * 0.40);
        int boxY1 = (int) (height * 0.20);
        int boxY2 = (int) (height * 0.40);
        gc.setFill(Color.rgb(100, 0, 0, 0.8));
        gc.fillRect(boxX1, boxY1, boxX2, boxY2);

        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(new Font("monospace", 40));
        gc.fillText("Game Over", width / 2, (boxY1 + boxY2) / 2);

        gc.setFont(new Font("monspace", 26));
        gc.fillText("Your score: " + points, width / 2, (boxY1 + boxY2) / 2 + 92);
        gc.fillText("Your time: " + Utils.getFormattedTime(runTime), width / 2, (boxY1 + boxY2) / 2 + 128);

        gc.setFont(new Font("monospace", 16));
        gc.fillText("Press any key to continue", width / 2, (boxY1 + boxY2) / 2 + 164);
    }

    public static void drawBallCountDownTimer(GraphicsContext gc, int width, int height, long ballCountDown) {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(new Font("monospace", 40));
        gc.setFill(Color.WHITE);
        gc.fillText("New ball spawning in", width / 2, height / 3);

        gc.fillText("" + (Math.floorDiv(ballCountDown, 1000) + 1), width / 2, height / 3 + 40);
    }

}
