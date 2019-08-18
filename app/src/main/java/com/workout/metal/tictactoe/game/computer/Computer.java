package com.workout.metal.tictactoe.game.computer;

import com.workout.metal.tictactoe.game.field.GameField;
import com.workout.metal.tictactoe.game.field.Point;
import com.workout.metal.tictactoe.game.field.PointCombination;

public interface Computer {


    void stepComputer(GameField field, int pc, int hum);

}
