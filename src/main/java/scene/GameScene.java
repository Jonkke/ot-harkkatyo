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
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import service.SceneDirectorService;
import service.GameStateService;

/**
 *
 * @author Jonkke
 */
public class GameScene extends BaseScene {

    private GameStateService gss;
    private int canvasWidth;
    private int canvasHeight;
    private Canvas canvas;
    private Group root;

    public GameScene(SceneDirectorService sds, GameStateService gss) {
        super(sds);
        this.gss = gss;
        this.canvasWidth = gss.getCanvasWidth();
        this.canvasHeight = gss.getCanvasHeight();
        this.root = new Group();
        this.canvas = new Canvas(canvasWidth, canvasHeight);
        root.getChildren().add(canvas);
    }

    AnimationTimer loop = new AnimationTimer() {
        private int fpsCap = 60;
        private int fixedDTns = 5 * 1000000;
        private long timeLastFrame = System.nanoTime();
        private long accumulatedTime = 0;

        public void handle(long timeNow) {
            long frameDuration = 0;
            long timeLast = timeNow - timeLastFrame;
            accumulatedTime += timeLast;
            timeLastFrame = timeNow;
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvasWidth, canvasHeight);

            while (accumulatedTime >= fixedDTns) {
                gss.update();
                accumulatedTime -= fixedDTns;
            }
            gss.draw(gc);

            // Sort of dirty hack to cap FPS to 60. JavaFX's AnimationTimer *should* cap it automatically,
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
    public Group getRoot() {
        return this.root;
    }

    public void start() {
        this.loop.start();
    }

    public void stop() {
        this.loop.stop();
    }

}