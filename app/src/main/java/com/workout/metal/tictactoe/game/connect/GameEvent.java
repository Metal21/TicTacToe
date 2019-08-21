package com.workout.metal.tictactoe.game.connect;

public interface GameEvent {

    enum Event{
        WINNER_PLAYER_X,
        WINNER_PLAYER_O,
        WINNER_PLAYER,
        WINNER_COMPUTER,
        NO_SPACE
    }

    void gameEvent(Event event);
}
