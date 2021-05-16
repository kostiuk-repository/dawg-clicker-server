package com.valeriialexandrovich.gameserver.classicmultyplayer.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class StepDataDto {

    private String gameId;
    private String playerID;
    private int damageAmount;
    private List<LiteElementDto> board;
    private List<SwipeHistoryDto> swipeHistory;
    private List<Integer> newGemsType;
}
