package com.valeriialexandrovich.gameserver.classicmultyplayer.data.game;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Player {

    private String id;
    private Long heroId;
}
