package com.valeriialexandrovich.gameserver.classicmultyplayer.mappers;

import com.valeriialexandrovich.gameserver.classicmultyplayer.data.StepData;
import com.valeriialexandrovich.gameserver.classicmultyplayer.web.model.StepDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StepDataMapper {

    StepData stepDataDtoToStepData(StepDataDto stepDataDto);

    StepDataDto stepDataToStepDataDto(StepData stepData);
}
