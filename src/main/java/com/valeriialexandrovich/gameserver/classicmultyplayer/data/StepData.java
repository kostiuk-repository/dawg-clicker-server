package com.valeriialexandrovich.gameserver.classicmultyplayer.data;

import lombok.Data;

import java.util.List;

@Data
public class StepData {

    private int damageAmount;
    private List<LiteElement> board;
    private List<SwipeHistory> swipeHistory;
    private List<Integer> newGemsType;

}
