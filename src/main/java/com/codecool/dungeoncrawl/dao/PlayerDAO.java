package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface PlayerDAO {
    void insert(PlayerModel player);
    boolean update(PlayerModel player, String player_name);
    PlayerModel get(int id);
    List<PlayerModel> getAll();
}
