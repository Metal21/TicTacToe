package com.workout.metal.tictactoe.game.field;

public class Cell {

    public final static int NONE = 0;
    public final static int X = 1;
    public final static int O = 2;

    public int value;// 0 - none 1 - X 2 - O
    public int l,c;

    Cell(int l, int c){this.l = l;this.c = c;}
}
