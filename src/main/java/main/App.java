package main;

import javafx.application.Application;
import javafx.stage.Stage;
import service.DatabaseService;
import service.SceneDirectorService;

/**
 * The main class used to launch the game.
 * 
 * @author Jonkke
 */
public class App extends Application {

    SceneDirectorService sceneDirector;
    DatabaseService dbs;

    public App() {
        this.sceneDirector = new SceneDirectorService();
        this.dbs = new DatabaseService();
        this.dbs.connect();
    }

    @Override
    public void start(Stage stage) throws InterruptedException {
        this.sceneDirector.setMenuScene();
        stage.setResizable(false);
        stage.setTitle("OMBC - One More Breakout Clone");
        stage.setScene(this.sceneDirector.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        System.setProperty("quantum.multithreaded", "false");
        launch(args);
    }

}
