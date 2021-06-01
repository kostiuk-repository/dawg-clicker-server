package com.valeriialexandrovich.gameserver.classicmultyplayer.services.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerData {

    private String playerId;
    private Long heroId;
}
