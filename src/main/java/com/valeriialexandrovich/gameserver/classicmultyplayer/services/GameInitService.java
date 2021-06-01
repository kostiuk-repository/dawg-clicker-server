package com.valeriialexandrovich.gameserver.classicmultyplayer.services;

import com.valeriialexandrovich.gameserver.classicmultyplayer.services.model.PlayerData;
import com.valeriialexandrovich.gameserver.classicmultyplayer.web.model.GameKeyDataDto;
import com.valeriialexandrovich.gameserver.classicmultyplayer.web.model.GameStatusDto;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameInitService {

    private ConcurrentHashMap<String, List<PlayerData>> games;

    @PostConstruct
    private void initialize(){
        games = new ConcurrentHashMap<>();
    }

    public GameKeyDataDto findGame(Long heroId) {
        Optional<Map.Entry<String, List<PlayerData>>> gameWithOnePlayer = games.entrySet().stream()
                .filter(game -> game.getValue().size() < 2)
                .findAny();
        if (gameWithOnePlayer.isPresent()) {
            var playerId = UUID.randomUUID().toString();
            gameWithOnePlayer.get().getValue().add(new PlayerData(playerId, heroId));
            return GameKeyDataDto.builder()
                    .isGameReadyToStart(true)
                    .heroID(gameWithOnePlayer.get().getValue().stream().findFirst().get().getHeroId())
                    .gameID(gameWithOnePlayer.get().getKey())
                    .playerID(playerId)
                    .build();
        } else {
            var newGameId = UUID.randomUUID().toString();
            var newPlayerId = UUID.randomUUID().toString();
            ArrayList<PlayerData> playerData = new ArrayList<>();
            playerData.add(new PlayerData(newPlayerId, heroId));
            games.put(newGameId, playerData);
            return GameKeyDataDto.builder()
                    .isGameReadyToStart(false)
                    .gameID(newGameId)
                    .heroID(null)
                    .playerID(newPlayerId)
                    .build();
        }
    }

    public GameStatusDto isGameReady(String gameId, String playerId) {
        Optional<PlayerData> playerData = games.get(gameId).stream()
                .filter(pData -> !pData.getPlayerId().equals(playerId))
                .findAny();
        if(playerData.isPresent()){
            return GameStatusDto.builder()
                    .status(games.get(gameId).size() > 1)
                    .heroId(playerData.get().getHeroId())
                    .build();
        } else {
            return GameStatusDto.builder()
                    .status(games.get(gameId).size() > 1)
                    .heroId(null)
                    .build();
        }
    }
}
