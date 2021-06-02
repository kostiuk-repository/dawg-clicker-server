package com.valeriialexandrovich.gameserver.classicmultyplayer.mappers;

import com.valeriialexandrovich.gameserver.classicmultyplayer.data.StepData;
import com.valeriialexandrovich.gameserver.classicmultyplayer.web.model.StepDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StepDataMapper {

    @Mapping(target = "playerId", source = "playerID")
    StepData stepDataDtoToStepData(StepDataDto stepDataDto);

    StepDataDto stepDataToStepDataDto(StepData stepData);
}
