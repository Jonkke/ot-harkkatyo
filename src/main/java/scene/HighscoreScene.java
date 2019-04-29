/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import dao.PlayerDao;
import dao.ScoreDao;
import domain.Player;
import domain.Score;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import service.DatabaseService;
import service.SceneDirectorService;
import util.Utils;

/**
 * This class represents the scene view where a high score leaderboard can be
 * viewed.
 *
 * @author Jonkke
 */
public class HighscoreScene extends BaseScene {

    private DatabaseService databaseService;
    private VBox root;

    private Map<Integer, String> playerIdNames;
    private List<Score> topScores;

    public HighscoreScene(SceneDirectorService sceneDirectorService, DatabaseService databaseService) {
        super(sceneDirectorService);
        this.databaseService = databaseService;
        this.root = new VBox(10);
        this.root.setMinSize(sceneDirectorService.getSceneWidth(), sceneDirectorService.getSceneHeight());
        this.root.setAlignment(Pos.CENTER);
        this.playerIdNames = mapPlayerIdsToNames();
        this.topScores = getSortedTopScores();
        addHighscoreMenuItems(this.root);
    }

    private Map<Integer, String> mapPlayerIdsToNames() {
        PlayerDao pd = new PlayerDao(this.databaseService);
        List<Player> players = pd.getAll();
        Map<Integer, String> mappedIdNames = new HashMap();
        players.forEach(p -> {
            mappedIdNames.put(p.getId(), p.getName());
        });
        return mappedIdNames;
    }

    private List<Score> getSortedTopScores() {
        ScoreDao sd = new ScoreDao(this.databaseService);
        List<Score> scores = sd.getAll();
        return scores.stream().sorted((s1, s2) -> {
            return s2.getPoints() - s1.getPoints();
        }).collect(Collectors.toList());
    }

    private void addHighscoreMenuItems(VBox root) {
        Label testLabel = new Label();
        testLabel.setText("High score");

        ListView highScoreList = new ListView();
        highScoreList.getItems().add("Points      Time      Date      Player");
        this.topScores.forEach(score -> {
            highScoreList.getItems().add(score.getPoints() + "      " + Utils.getFormattedTime(score.getScoreTime()) + "    " + score.getScoreDate() + "    " + playerIdNames.getOrDefault(score.getPlayerId(), ""));
        });

        Button backBtn = new Button();
        backBtn.setText("Back to main menu");
        backBtn.setOnAction(event -> {
            sceneDirectorService.setMenuScene();
        });

        root.getChildren().add(testLabel);
        root.getChildren().add(highScoreList);
        root.getChildren().add(backBtn);
    }

    @Override
    public Parent getRoot() {
        return this.root;
    }

}
