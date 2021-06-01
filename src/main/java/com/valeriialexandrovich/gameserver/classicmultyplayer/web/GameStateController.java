package com.valeriialexandrovich.gameserver.classicmultyplayer.web;

import com.valeriialexandrovich.gameserver.classicmultyplayer.services.GameInitService;
import com.valeriialexandrovich.gameserver.classicmultyplayer.web.model.GameKeyDataDto;
import com.valeriialexandrovich.gameserver.classicmultyplayer.web.model.GameStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GameStateController {

    private final GameInitService gameInitService;

    @GetMapping("game-keys")
    public GameKeyDataDto findGameForPlayer(@RequestParam("heroId") final Long heroId){
        return gameInitService.findGame(heroId);
    }

    @GetMapping("check-state/{gameId}")
    public GameStatusDto checkGameState(@PathVariable("gameId") String gameId,
                                        @RequestParam("playerId") final String playerId){
        return gameInitService.isGameReady(gameId, playerId);
    }
}
