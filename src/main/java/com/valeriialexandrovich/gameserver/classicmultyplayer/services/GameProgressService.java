package com.valeriialexandrovich.gameserver.classicmultyplayer.services;

import com.valeriialexandrovich.gameserver.classicmultyplayer.data.StepData;
import com.valeriialexandrovich.gameserver.classicmultyplayer.data.StepMetadata;
import com.valeriialexandrovich.gameserver.classicmultyplayer.data.game.Game;
import com.valeriialexandrovich.gameserver.classicmultyplayer.exceprions.StepNotFoundException;
import com.valeriialexandrovich.gameserver.classicmultyplayer.mappers.StepDataMapper;
import com.valeriialexandrovich.gameserver.classicmultyplayer.web.model.StepDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameProgressService {

    private final GameDataHolderService gameDataHolderService;
    private final StepDataMapper stepDataMapper;
    private Map<Game, List<StepData>> gameStepsDataStore;

    @PostConstruct
    private void initialize() {
        gameStepsDataStore = new ConcurrentHashMap<>();
    }

    public boolean handleOneStep(StepDataDto stepDataDto) {
        Game gameById = gameDataHolderService.getGameById(stepDataDto.getGameId());
        if (gameStepsDataStore.containsKey(gameById)) {
            Integer lastStepNumber = getLastStepNumber(gameById);
            boolean isThereTwoLastSteps = isThereTwoLastSteps(gameById, lastStepNumber);
            var stepData = stepDataMapper.stepDataDtoToStepData(stepDataDto);
            if (isThereTwoLastSteps) {
                stepData.setStepNumber(lastStepNumber + 1);
            } else {
                stepData.setStepNumber(lastStepNumber);
            }
            gameStepsDataStore.get(gameById)
                    .add(stepData);
        } else {
            ArrayList<StepData> stepsData = new ArrayList<>();
            var stepData = stepDataMapper.stepDataDtoToStepData(stepDataDto);
            stepData.setStepNumber(0);
            stepsData.add(stepData);
            gameStepsDataStore.put(gameById, stepsData);
        }
        return true;
    }

    public StepDataDto findOpponentMove(String gameId, String playerId) {
        Game gameById = gameDataHolderService.getGameById(gameId);
        StepData opponentStep = gameStepsDataStore.get(gameById)
                .stream()
                .filter(stepData -> stepData.getStepNumber() != getLastStepNumber(gameById))
                .filter(stepData -> !stepData.getPlayerId().equals(playerId))
                .findFirst()
                .orElseThrow(StepNotFoundException::new);
        return stepDataMapper.stepDataToStepDataDto(opponentStep);
    }

    private Integer getLastStepNumber(Game gameById) {
        return gameStepsDataStore.get(gameById)
                .stream()
                .map(StepData::getStepNumber)
                .max(Integer::compareTo)
                .get();
    }

    private boolean isThereTwoLastSteps(Game gameById, Integer lastStepNumber) {
        return gameStepsDataStore.get(gameById)
                .stream()
                .filter(stepData -> stepData.getStepNumber() != lastStepNumber)
                .count() == 2;
    }
}
