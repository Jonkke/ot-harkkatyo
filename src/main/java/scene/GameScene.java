/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import main.App;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import service.SceneDirectorService;
import service.GameStateService;

/**
 * This class represents the scene where the actual gameplay happens. It is
 * responsible for creating and maintaing the state of the game loop, which is
 * implemented using JavaFX's AnimationTimer class.
 *
 * @author Jonkke
 */
public class GameScene extends BaseScene {

    private GameStateService gameStateService;
    private int canvasWidth;
    private int canvasHeight;
    private Canvas canvas;
    private Group root;

    private long timeLastFrame;

    public GameScene(SceneDirectorService sceneDirectorService, GameStateService gameStateService) {
        super(sceneDirectorService);
        this.gameStateService = gameStateService;
        this.canvasWidth = sceneDirectorService.getSceneWidth();
        this.canvasHeight = sceneDirectorService.getSceneHeight();
        this.root = new Group();
        this.canvas = new Canvas(canvasWidth, canvasHeight);
        root.getChildren().add(canvas);
    }

    AnimationTimer loop = new AnimationTimer() {
        private int fpsCap = 120;
        private int fixedDTns = 5 * 1000000;
        private long accumulatedTime = 0;

        public void handle(long timeNow) {
            long frameDuration = 0;
            long timeLast = timeNow - timeLastFrame;
            accumulatedTime += timeLast;
            timeLastFrame = timeNow;
            GraphicsContext gc = canvas.getGraphicsContext2D();

            while (accumulatedTime >= fixedDTns) {
                gameStateService.update(fixedDTns / 1000000);
                accumulatedTime -= fixedDTns;
            }
            gameStateService.draw(gc);

            // Sort of dirty hack to cap FPS to the value denoted by fpsCap variable. JavaFX's AnimationTimer *should* cap it automatically,
            // but it seems that on some systems (Ubuntu that I'm running for instance) the throttling does
            // not work, so I have done it manually here.
            while ((frameDuration / 1000000) < (1.0 / fpsCap) * 1000) {
                try {
                    Thread.sleep(1);
                    frameDuration = System.nanoTime() - timeNow;
                } catch (InterruptedException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    };

    @Override
    public Parent getRoot() {
        return this.root;
    }

    /**
     * Calling this method will start the game loop contained within this scene.
     * Will also reset the time from last time to current system time, to
     * prevens skipping.
     */
    public void start() {
        this.timeLastFrame = System.nanoTime();
        this.loop.start();
    }

    /**
     * Calling this method will stop the game loop contained within this scene.
     */
    public void stop() {
        this.loop.stop();
    }

}
