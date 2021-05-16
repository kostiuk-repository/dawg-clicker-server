package com.valeriialexandrovich.gameserver.classicmultyplayer.services;

import com.valeriialexandrovich.gameserver.classicmultyplayer.data.StepData;
import com.valeriialexandrovich.gameserver.classicmultyplayer.data.StepMetadata;
import com.valeriialexandrovich.gameserver.classicmultyplayer.exceprions.StepNotFoundException;
import com.valeriialexandrovich.gameserver.classicmultyplayer.mappers.StepDataMapper;
import com.valeriialexandrovich.gameserver.classicmultyplayer.web.model.StepDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class GameProgressService {

    private HashMap<String, HashMap<StepMetadata, StepData>> gameProgressDataStore;
    private final StepDataMapper stepDataMapper;

    @PostConstruct
    private void initialize(){
        gameProgressDataStore = new HashMap<>();
    }

    public boolean handleOneStep(StepDataDto stepDataDto) {
        gameProgressDataStore.computeIfAbsent(stepDataDto.getGameId(), k -> new HashMap<>());
        gameProgressDataStore.get(stepDataDto.getGameId())
                .put(StepMetadata.builder().gameId(stepDataDto.getGameId()).playerID(stepDataDto.getPlayerID()).build(),
                        stepDataMapper.stepDataDtoToStepData(stepDataDto));
        return true;
    }

    public StepDataDto findOpponentMove(String gameId, String playerId) {
        StepMetadata stepMetadata = StepMetadata.builder()
                .playerID(playerId)
                .gameId(gameId)
                .build();
        StepMetadata stepMetadataOpponentKey = gameProgressDataStore.get(gameId)
                .keySet()
                .stream()
                .filter(stepData -> !stepData.equals(stepMetadata))
                .findAny()
                .orElseThrow(StepNotFoundException::new);
        StepData stepData = gameProgressDataStore.get(gameId).remove(stepMetadataOpponentKey);
        return stepDataMapper.stepDataToStepDataDto(stepData);
    }
}
