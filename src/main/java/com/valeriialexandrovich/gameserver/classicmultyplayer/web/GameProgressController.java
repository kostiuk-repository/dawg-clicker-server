package com.valeriialexandrovich.gameserver.classicmultyplayer.web;

import com.valeriialexandrovich.gameserver.classicmultyplayer.services.GameProgressService;
import com.valeriialexandrovich.gameserver.classicmultyplayer.web.model.StepDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GameProgressController {

    private final GameProgressService gameProgressService;

    @PostMapping("step")
    private boolean gameStep(@RequestBody StepDataDto stepDataDto){
        return gameProgressService.handleOneStep(stepDataDto);
    }

    @GetMapping("step")
    private StepDataDto gameStep(@RequestParam("gameId") final String gameId,
                                 @RequestParam("playerId") final String playerId){
        return gameProgressService.findOpponentMove(gameId,playerId);
    }

}
