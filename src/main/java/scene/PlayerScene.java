/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import dao.PlayerDao;
import domain.Player;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import service.DatabaseService;
import service.GameStateService;
import service.SceneDirectorService;

/**
 * This class represents the scene view where a player profile can be chosen
 * from existing ones, or new one created.
 *
 * @author Jonkke
 */
public class PlayerScene extends BaseScene {  // TODO: This whole thing could use some serious cleanup...

    PlayerDao pd;
    List<Player> playerList;

    private GameStateService gameStateService;
    private VBox root;
    private ObservableList<String> playerNames;

    public PlayerScene(SceneDirectorService sds, GameStateService gameStateService, DatabaseService dataBaseService) {
        super(sds);
        this.pd = new PlayerDao(dataBaseService);
        this.playerList = this.pd.getAll();
        this.gameStateService = gameStateService;
        this.gameStateService.setActivePlayer(this.playerList.get(0));
        this.root = new VBox(10);
        this.root.setMinSize(sds.getSceneWidth(), sds.getSceneHeight());
        this.root.setAlignment(Pos.CENTER);
        addPlayerMenuItems(this.root);
    }

    private void updateListNames() {
        this.playerNames = FXCollections.observableArrayList(this.playerList.stream().map(p -> p.getName()).collect(Collectors.toList()));
    }

    private void addPlayerMenuItems(VBox root) {
        Label selectedPlayerLabel = new Label();
        selectedPlayerLabel.setText("Currently selected player: " + this.gameStateService.getActivePlayer().getName());

        updateListNames();
        final ListView<String> namesList = new ListView(playerNames);
        namesList.setPrefWidth(300);

        // Add new player label, field and button
        GridPane addPlayerPane = new GridPane();

        Label addPlayerLabel = new Label();
        addPlayerLabel.setText("Add new player: ");

        TextField addPlayerTF = new TextField();

        Button addPlayerBtn = new Button();
        addPlayerBtn.setText("Add player");
        addPlayerBtn.setOnAction(event -> {
            Thread t = new Thread(new Runnable() {  // Async way of adding new player... sort of
                @Override
                public void run() {
                    pd.save(new Player(-1, addPlayerTF.getText()));
                    playerList = pd.getAll();
                }
            });
            t.start();
            namesList.getItems().add(addPlayerTF.getText());
            addPlayerTF.setText("");
        });

        addPlayerPane.setAlignment(Pos.CENTER);
        addPlayerPane.setHgap(10);
        addPlayerPane.add(addPlayerLabel, 0, 0);
        addPlayerPane.add(addPlayerTF, 1, 0);
        addPlayerPane.add(addPlayerBtn, 2, 0);

        // Player selection & deletion
        Button selectBtn = new Button();
        selectBtn.setText("Select player");
        selectBtn.setOnAction(event -> {
            int selectedIndex = namesList.getSelectionModel().getSelectedIndex();
            if (this.playerList.get(selectedIndex) == this.gameStateService.getActivePlayer()) {
                return;
            }
            if (this.gameStateService.gameIsActive()) {
                if (!getEndGameConfirmation()) {
                    return;
                }
                this.gameStateService.endGame(true);
            }
            this.gameStateService.setActivePlayer(this.playerList.get(selectedIndex));
            selectedPlayerLabel.setText("Currently selected player: " + this.gameStateService.getActivePlayer().getName());
        });
        Button deleteBtn = new Button();
        deleteBtn.setText("Delete selected player");
        deleteBtn.setOnAction(event -> {
            int i = namesList.getSelectionModel().getSelectedIndex();
            if (i == 0) {
                return; // Don't ever delete default player
            }
            if (this.gameStateService.getActivePlayer() == this.playerList.get(i)) {
                this.gameStateService.setActivePlayer(playerList.get(0));
            }
            this.pd.delete(playerList.get(i));
            this.playerList = this.pd.getAll();
            this.updateListNames();
            namesList.setItems(playerNames);
        });
        GridPane lowerBtnPane = new GridPane();
        lowerBtnPane.setHgap(10);
        lowerBtnPane.setAlignment(Pos.CENTER);
        lowerBtnPane.add(selectBtn, 0, 0);
        lowerBtnPane.add(deleteBtn, 1, 0);

        Button backBtn = new Button();
        backBtn.setText("Back to main menu");
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneDirectorService.setMenuScene();
            }
        });

        root.getChildren().add(selectedPlayerLabel);
        root.getChildren().add(addPlayerPane);
        root.getChildren().add(namesList);
        root.getChildren().add(lowerBtnPane);
        root.getChildren().add(backBtn);
    }

    /**
     * Shows a confirmation dialog asking whether or not the current game should
     * end, which needs to happen when changing player mid-game.
     *
     * @return boolean value representing confirmed or declined confirmation
     */
    private boolean getEndGameConfirmation() {
        Alert a = new Alert(AlertType.CONFIRMATION);
        a.setTitle("End game confirmation");
        a.setHeaderText("End current game?");
        a.setContentText("Changing player will end the currently running game. Are you sure you want switch to another player?");

        Optional<ButtonType> res = a.showAndWait();
        return res.get() == ButtonType.OK;
    }

    @Override
    public Parent getRoot() {
        return this.root;
    }

}
