package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameStateModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class GameDatabaseManager {
    private PlayerDAO playerDao;
    private GameStateDAO gameStateDao;
    private int playerId;
    private String player_name;
    private boolean possible_to_save;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDAOJdbc(dataSource);
        gameStateDao = new GameStateDAOJdbc(dataSource);
    }

    public void name_is_already_in_db() {
        Stage name_is_in_db = new Stage();
        Label cantAdd = new Label("You can't add this name. It's already taken. \n\n Would you like to overwrite the already existing state?!");
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setDefaultButton(true);
        noButton.setDefaultButton(true);

        yesButton.setOnAction(event -> {
            name_is_in_db.close();
        });

        noButton.setOnAction(event -> {
            name_is_in_db.close();
        });

        VBox vbox = new VBox(cantAdd, yesButton, noButton);
        vbox.setAlignment(Pos.CENTER);
        Scene itemScene = new Scene(vbox, 400, 250);
        name_is_in_db.setScene(itemScene);
        name_is_in_db.show();
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        this.playerId = 1;
        this.player_name = model.getPlayerName();
        possible_to_save = playerDao.update(model, player_name);
        if (possible_to_save){
            playerDao.insert(model);
        }
        else {
            name_is_already_in_db();
        }
    }

    public void saveGameState(GameStateModel state) {
        if (possible_to_save) {
            gameStateDao.insert(state, playerId);
        }
    }


    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = System.getenv("PSQL_DB_NAME");
        String user = System.getenv("PSQL_USER_NAME");
        String password = System.getenv("PSQL_PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}