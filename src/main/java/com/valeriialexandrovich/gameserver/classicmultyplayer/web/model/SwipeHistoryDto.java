package com.valeriialexandrovich.gameserver.classicmultyplayer.web.model;

import lombok.Data;

@Data
public class SwipeHistoryDto {

    private int posX;
    private int posY;
    private float directionX;
    private float directionY;
}
