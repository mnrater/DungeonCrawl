package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.model.GameStateModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;


public class Main extends Application {
    Actor actor;
    String playerName = "";
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label strengthLabel = new Label();
    Label keyLabel = new Label();
    Label nameLabel = new Label();
    BorderPane borderPane = new BorderPane();
    Scene scene = new Scene(borderPane);
    Stage stage;
    Button nameButton = new Button("Set your name");
    GameDatabaseManager dbManager;
    GameStateModel gameState;
    String currentMap = "/map.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        startGame(stage);
    }

    private void saveGame() {
        Stage saveGameWindow = new Stage();
        saveGameWindow.setTitle("Save game");

        Label setName = new Label("Player's name: ");

        TextField textField = new TextField();
        textField.setText(map.getPlayer().getName());

        Button saveButton = new Button("Save");
        saveButton.setDefaultButton(true);

        saveButton.setOnAction(event -> {
            map.getPlayer().setName(textField.getText());
            dbManager.savePlayer(map.getPlayer());
            setGameState();
            dbManager.saveGameState(gameState);
            saveGameWindow.close();
            refresh();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setDefaultButton(false);

        cancelButton.setOnAction(event -> {
            saveGameWindow.close();
            refresh();
        });

        HBox hbox = new HBox(setName, textField, saveButton, cancelButton);
        hbox.setAlignment(Pos.CENTER);

        Scene changeNameScene = new Scene(hbox, 400, 50);

        saveGameWindow.setScene(changeNameScene);

        saveGameWindow.show();

    }

    private void setupDbManager() throws SQLException {

        try {
            this.dbManager = new GameDatabaseManager();
            dbManager.setup();
        } catch (SQLException exception) {
            throw new SQLException(exception);
        }
    }

    private void startGame(Stage stage) throws Exception {
        map = MapLoader.loadMap();
        setupDbManager();
        nameButton.setFocusTraversable(false);
        nameButton.setOnAction(event -> setPlayerName());
        GridPane ui = new GridPane();
        ui.setPrefWidth(250);
        ui.setPadding(new Insets(5));

        ui.add(new Label("Dungeon Crawl"), 1, 0);
        ui.add(new Label(""), 0, 1);
        ui.add(new Label("Name:"), 0, 2);
        ui.add(nameLabel, 2, 2);
        ui.add(new Label(""), 0, 4);
        ui.add(new Label("Health: "), 0, 5);
        ui.add(healthLabel, 2, 5);
        ui.add(new Label("Strength: "), 0, 6);
        ui.add(strengthLabel, 2, 6);
        ui.add(new Label(""), 0, 7);
        ui.add(new Label("Keys: "), 0, 8);
        ui.add(keyLabel, 2, 8);
        Button saveGameButton = new Button("Save game");
        saveGameButton.setFocusTraversable(false);
        saveGameButton.setOnAction(event -> saveGame());
        for (int i = 0; i < 24; i++) {
            ui.add(new Label(""), 0, 9+i);
        }
        ui.add(nameButton, 1, 48);
        ui.add(new Label(""), 0, 49);
        ui.add(saveGameButton, 1, 50);

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        stage.setScene(scene);
        map.getPlayer().setName(playerName);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        stage.setTitle("Dungeon Crawl");
        stage.show();
    }


    private void setPlayerName() {
        Stage askForPlayerNameWindow = new Stage();

        Label setName = new Label("Player's name: ");

        TextField textField = new TextField();
        textField.setText(map.getPlayer().getName());

        Button saveButton = new Button("Save");
        saveButton.setDefaultButton(true);

        saveButton.setOnAction(event -> {
            map.getPlayer().setName(textField.getText());
            playerName = textField.getText();
            askForPlayerNameWindow.close();
            refresh();
        });

        HBox hbox = new HBox(setName, textField, saveButton);
        hbox.setAlignment(Pos.CENTER);

        Scene changeNameScene = new Scene(hbox, 300, 50);

        askForPlayerNameWindow.setScene(changeNameScene);

        askForPlayerNameWindow.show();

    }


    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
//                moveAllActors();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
//                moveAllActors();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
//                moveAllActors();
                break;
            case RIGHT:
                map.getPlayer().move(1,0);
//                moveAllActors();
                break;
        }
        refresh();


    }

    public void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null ) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                }
                else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                }
                else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        strengthLabel.setText("" + map.getPlayer().getStrength());
        nameLabel.setText("" + playerName);
        if(map.getPlayer().isHasKey()){
            keyLabel.setText("" + 1);
        }
        else{
            keyLabel.setText("" + 0);
        }
        if(map.getPlayer().getHealth() < 1){
            endGameScreen('d');
        }
        else if(map.getPlayer().isEscaped()){
            endGameScreen('e');
        }
    }

    private void endGameScreen(char x) {
        Stage endGameStage = new Stage();
        Label label = null;
        if (x == 'd'){
            label = new Label("You are dead! Do you want to play again?");
        }
        else if (x == 'e') {
            label = new Label("Congratulations! You escaped! \n" +
                    "Do you want to play again?");
        }
        Button yesButton = new Button("Yes!");
        Button noButton = new Button("No!");

        yesButton.setDefaultButton(true);
        noButton.setCancelButton(true);

        yesButton.setOnAction(event -> {
            try {
                endGameStage.close();
                restart(stage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        noButton.setOnAction(event -> {
            System.exit(0);
        });


        VBox vbox = new VBox(label, yesButton, noButton);
        vbox.setAlignment(Pos.CENTER);
        Scene endGameScene = new Scene(vbox, 400, 150);
        endGameStage.setScene(endGameScene);
        endGameStage.show();
    }

    private void restart(Stage start) throws Exception {
        startGame(start);
    }

    private void moveAllActors() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    if (cell.getType().equals(CellType.GOLUM)) {
                        cell.getActor().moveActorRandomly(cell.getActor());
                    }
                    }
                }
            }
        }

    private void setGameState(){
        java.util.Date currentDate = new java.util.Date();
        gameState = new GameStateModel(currentMap, new Date(currentDate.getTime()), new PlayerModel(map.getPlayer()));

    }

}
