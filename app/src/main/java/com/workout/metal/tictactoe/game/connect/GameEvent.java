package com.workout.metal.tictactoe.game.connect;

public interface GameEvent {

    int WINNER_PLAYER_X = 1;
    int WINNER_PLAYER_O = 2;
    int WINNER_PLAYER = 3;
    int WINNER_COMPUTER = 4;
    int NO_SPACE = 5;

    void gameEvent(int type);
}
