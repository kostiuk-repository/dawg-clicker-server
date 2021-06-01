package com.valeriialexandrovich.gameserver.classicmultyplayer.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameStatusDto {

    private boolean status;
    private Long heroId;
}
