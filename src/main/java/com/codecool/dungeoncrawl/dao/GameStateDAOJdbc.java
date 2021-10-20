package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameStateModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class GameStateDAOJdbc implements GameStateDAO {
    private DataSource dataSource;
    public boolean result;
    int if_player_name_exist_counter = 0;

    public GameStateDAOJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(GameStateModel state, int playerId) {
        try(Connection c = dataSource.getConnection()){
            String sql = "INSERT INTO game_state (current_map, saved_at, player_id) VALUES (?, ?, ?)";
            PreparedStatement statement = c.prepareStatement(sql);
            statement.setString(1, state.getCurrentMap());
            statement.setDate(2, state.getSavedAt());
            statement.setInt(3, playerId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GameStateModel state, String player_name, int playerId) {
    }

    @Override
    public GameStateModel get(int id) {
        return null;
    }

    @Override
    public List<GameStateModel> getAll() {
        return null;
    }
}
