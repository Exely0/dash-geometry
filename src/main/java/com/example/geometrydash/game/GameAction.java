package com.example.geometrydash.game;

import com.example.geometrydash.enums.GameState;

public interface GameAction {
    void start();

    void pause();

    void resume();

    void end();

    GameState getState();
}
