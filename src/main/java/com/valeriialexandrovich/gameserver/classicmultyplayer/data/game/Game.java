package com.valeriialexandrovich.gameserver.classicmultyplayer.data.game;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Game {

    private String id;
    private List<Player> players;
}
