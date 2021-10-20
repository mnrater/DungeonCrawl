package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class PlayerDAOJdbc implements PlayerDAO {
    private DataSource dataSource;
    private boolean result;
    private int if_player_name_exist_counter = 0;

    public PlayerDAOJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, x, y) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getX());
            statement.setInt(4, player.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(PlayerModel player, String player_name) {
        try(Connection conn = dataSource.getConnection()){
            String sql = "SELECT player_name FROM player WHERE player_name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, player_name);
            result = statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()) {
                System.out.println(resultSet.getString(1));
                if_player_name_exist_counter += 1;
            }
            if (if_player_name_exist_counter == 1) {
                if_player_name_exist_counter = 0;
                return false;
            }
            else {
                if_player_name_exist_counter = 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public PlayerModel get(int id) {
        return null;
    }

    @Override
    public List<PlayerModel> getAll() {
        return null;
    }
}
