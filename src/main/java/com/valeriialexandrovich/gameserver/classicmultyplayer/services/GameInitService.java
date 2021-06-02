package com.valeriialexandrovich.gameserver.classicmultyplayer.services;

import com.valeriialexandrovich.gameserver.classicmultyplayer.data.game.Game;
import com.valeriialexandrovich.gameserver.classicmultyplayer.data.game.Player;
import com.valeriialexandrovich.gameserver.classicmultyplayer.web.model.GameKeyDataDto;
import com.valeriialexandrovich.gameserver.classicmultyplayer.web.model.GameStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameInitService {

    private final GameDataHolderService gameDataHolderService;

    public GameKeyDataDto findGame(Long heroId) {
        Optional<Game> gameWithOnePlayer = gameDataHolderService.getAllGames()
                .stream()
                .filter(game -> game.getPlayers().size() < 2)
                .findAny();
        if (gameWithOnePlayer.isPresent()) {
            var playerId = UUID.randomUUID().toString();
            gameWithOnePlayer.get().getPlayers().add(
                    Player.builder()
                                .id(playerId)
                                .heroId(heroId)
                            .build()
            );
            return GameKeyDataDto.builder()
                        .isGameReadyToStart(true)
                        .heroID(gameWithOnePlayer.get().getPlayers().stream().findFirst().get().getHeroId())
                        .gameID(gameWithOnePlayer.get().getId())
                        .playerID(playerId)
                    .build();
        } else {
            var newGameId = UUID.randomUUID().toString();
            var newPlayerId = UUID.randomUUID().toString();
            ArrayList<Player> playerData = new ArrayList<>();
            playerData.add(
                    Player.builder()
                                .id(newPlayerId)
                                .heroId(heroId)
                            .build());
            gameDataHolderService.addNewGame(
                    Game.builder()
                                .id(newGameId)
                                .players(playerData)
                            .build()
            );
            return GameKeyDataDto.builder()
                        .isGameReadyToStart(false)
                        .gameID(newGameId)
                        .heroID(null)
                        .playerID(newPlayerId)
                    .build();
        }
    }

    public GameStatusDto isGameReady(String gameId, String playerId) {
        Optional<Player> playerData = gameDataHolderService.getGameById(gameId)
                .getPlayers()
                .stream()
                .filter(pData -> !pData.getId().equals(playerId))
                .findAny();
        if(playerData.isPresent()){
            return GameStatusDto.builder()
                        .status(gameDataHolderService.getGameById(gameId).getPlayers().size() > 1)
                        .heroId(playerData.get().getHeroId())
                    .build();
        } else {
            return GameStatusDto.builder()
                        .status(gameDataHolderService.getGameById(gameId).getPlayers().size() > 1)
                        .heroId(null)
                    .build();
        }
    }
}
