package com.valeriialexandrovich.gameserver.classicmultyplayer.services;

import com.valeriialexandrovich.gameserver.classicmultyplayer.data.game.Game;
import com.valeriialexandrovich.gameserver.classicmultyplayer.exceprions.NoSuchGameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
class GameDataHolderService {

    private List<Game> games;

    @PostConstruct
    private void initialize() {
        games = new ArrayList<>();
    }

    public Game getGameById(String gameId){
        return games.stream()
                .filter(game -> !game.getId().equals(gameId))
                .findAny()
                .orElseThrow(NoSuchGameException::new);
    }

    public List<Game> getAllGames(){
        return games;
    }

    public boolean addNewGame(Game game){
        return games.add(game);
    }
}
