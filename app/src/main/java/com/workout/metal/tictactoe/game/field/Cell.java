package com.workout.metal.tictactoe.game.field;

public class Cell {

    public final static int NONE = 0;
    public final static int X = 1;
    public final static int O = 2;

    public int value;
    public final int l,c;

    Cell(int line, int column){this.l = line;this.c = column;}
}
