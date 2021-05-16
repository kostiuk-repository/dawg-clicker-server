package com.valeriialexandrovich.gameserver.classicmultyplayer.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StepMetadata {

    private String gameId;
    private String playerID;
}
