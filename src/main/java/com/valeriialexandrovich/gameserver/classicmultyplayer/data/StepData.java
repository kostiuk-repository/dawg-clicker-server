package com.valeriialexandrovich.gameserver.classicmultyplayer.data;

import lombok.Data;

import java.util.List;

@Data
public class StepData {

    private int stepNumber;
    private int damageAmount;
    private String playerId;
    private List<LiteElement> board;
    private List<SwipeHistory> swipeHistory;
    private List<Integer> newGemsType;

}
