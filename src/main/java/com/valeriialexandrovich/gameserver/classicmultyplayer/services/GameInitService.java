package com.valeriialexandrovich.gameserver.classicmultyplayer.services;

import com.valeriialexandrovich.gameserver.classicmultyplayer.web.model.GameKeyDataDto;
import com.valeriialexandrovich.gameserver.classicmultyplayer.web.model.GameStatusDto;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameInitService {

    private ConcurrentHashMap<String, List<String>> games;

    @PostConstruct
    private void initialize(){
        games = new ConcurrentHashMap<>();
    }

    public GameKeyDataDto findGame() {
        Optional<Map.Entry<String, List<String>>> gameWithOnePlayer = games.entrySet().stream()
                .filter(game -> game.getValue().size() < 2)
                .findAny();
        if (gameWithOnePlayer.isPresent()) {
            var playerId = UUID.randomUUID().toString();
            gameWithOnePlayer.get().getValue().add(playerId);
            return GameKeyDataDto.builder()
                    .isGameReadyToStart(true)
                    .gameID(gameWithOnePlayer.get().getKey())
                    .playerID(playerId)
                    .build();
        } else {
            var newGameId = UUID.randomUUID().toString();
            var newPlayerId = UUID.randomUUID().toString();
            ArrayList<String> playerIds = new ArrayList<>();
            playerIds.add(newPlayerId);
            games.put(newGameId, playerIds);
            return GameKeyDataDto.builder()
                    .isGameReadyToStart(false)
                    .gameID(newGameId)
                    .playerID(newPlayerId)
                    .build();
        }
    }

    public GameStatusDto isGameReady(String gameId) {
        return GameStatusDto.builder()
                .status(games.get(gameId).size() > 1)
                .build();
    }
}
