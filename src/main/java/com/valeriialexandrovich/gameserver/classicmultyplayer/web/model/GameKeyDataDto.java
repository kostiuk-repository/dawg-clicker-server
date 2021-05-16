package com.valeriialexandrovich.gameserver.classicmultyplayer.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameKeyDataDto {

    private boolean isGameReadyToStart;
    private String gameID;
    private String playerID;
}
