/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.Brick;
import domain.GameObject;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * A container class for some utility methods used within the game.
 *
 * @author Jonkke
 */
public class Utils {

    /**
     * Returns a string representation of time in the format mm:ss:SS
     *
     * @param timeMillis time in milliseconds that needs formatting
     * @return A formatted string representation of the input time
     */
    public static String getFormattedTime(long timeMillis) {
        int mins = (int) (timeMillis / 60000);
        int secs = (int) ((timeMillis / 1000) - (mins * 60));
        int ms = (int) (timeMillis - (mins * 60000) - (secs * 1000)) / 10;
        return String.format("%02d:%02d:%02d", mins, secs, ms);
    }

    /**
     * Builds a symmetric, uniform array of bricks with 8 rows and 16 columns.
     * This represents the "classic" arrangement of bricks in the original
     * Breakout game, where the brick color is changed at every other row. The
     * bricks at the top are most valuable, while those at the bottom are least
     * valuable.
     *
     * @param gameAreaWidth width of the game area
     * @param gameAreaHeight height of the game area
     * @param gapSize The width or height of gap between bricks
     * @return List of bricks in the array
     */
    public static List<GameObject> build16by8BrickArray(int gameAreaWidth, int gameAreaHeight, int gapSize) {
        List<GameObject> brickArray = new ArrayList();
        if (16 < 1 || 8 < 1) {
            return brickArray;
        }
        int brickWidth = (int) (gameAreaWidth * 0.7 - gapSize * (16 - 1)) / 16;
        int brickHeight = (int) (gameAreaHeight * 0.2 - gapSize * (8 - 1)) / 8;
        int posXBase = (int) (gameAreaWidth * 0.150) + (int) (brickWidth / 2);
        int posX = posXBase;
        int posY = (int) (gameAreaHeight * 0.1);
        for (int y = 0; y < 8; y++) {
            Color color
                    = y == 0 || y == 1 ? Color.YELLOW
                            : y == 2 || y == 3 ? Color.GREEN
                                    : y == 4 || y == 5 ? Color.ORANGE
                                            : Color.RED;
            int value = y == 0 || y == 1 ? 7
                    : y == 2 || y == 3 ? 5
                            : y == 4 || y == 5 ? 3 : 1;
            for (int x = 0; x < 16; x++) {
                brickArray.add(new Brick(posX, posY, brickWidth, brickHeight, color, 1, value));
                posX += brickWidth + gapSize;
            }
            posX = posXBase;
            posY += brickHeight + gapSize;
        }
        return brickArray;
    }

    /**
     * Resets the mouse position back inside the scene window bounds, if either
     * of it's X or Y positions are outside them. This method can be used to
     * "lock" the mouse inside game area, when called from within a running game
     * loop.
     *
     * @param scene the Scene object whose bounds we should observe when
     * determining whether to reset mouse or not
     */
    public static void lockMouseInsideGameArea(Scene scene) {
        Platform.runLater(() -> {
            try {
                Robot r = new Robot();
                int leftX = (int) scene.getWindow().getX() + 10;
                int topY = (int) scene.getWindow().getY() + 35;
                int rightX = (int) scene.getWidth() + leftX - 10;
                int bottomY = (int) scene.getHeight() + topY - 10;

                int mouseX = MouseInfo.getPointerInfo().getLocation().x;
                int mouseY = MouseInfo.getPointerInfo().getLocation().y;

                if (mouseX < leftX) {
                    r.mouseMove(leftX, mouseY);
                } else if (mouseX > rightX) {
                    r.mouseMove(rightX, mouseY);
                } else if (mouseY < topY) {
                    r.mouseMove(mouseX, topY);
                } else if (mouseY > bottomY) {
                    r.mouseMove(mouseX, bottomY);
                }
            } catch (AWTException awte) {
            }
        });
    }
}
