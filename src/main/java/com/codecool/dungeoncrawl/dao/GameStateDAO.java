package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameStateModel;

import java.util.List;

public interface GameStateDAO {
    void insert(GameStateModel state, int playerId);
    void update(GameStateModel state, String player_name, int playerId);
    GameStateModel get(int id);
    List<GameStateModel> getAll();
}