package com.workout.metal.tictactoe.game.field;

public class CellCombs {

    public Cell A, B, C;

    public CellCombs(Cell a, Cell b, Cell c) {
        A = a;
        B = b;
        C = c;

    }
    //кол-во крестиков/ноликов в комбинации
    public int quantity(int what) {
        int ret = 0;
        if (A.value == what) ret++;
        if (B.value == what) ret++;
        if (C.value == what) ret++;
        return ret;
    }
    //есть ли пустая клетка
    public boolean isEmptyCell() {
        if (A.value == Cell.NONE) return true;
        if (B.value == Cell.NONE) return true;
        if (C.value == Cell.NONE) return true;
        return false;
    }
    //извлекает первую попавшуюся пустую клетку
    public Cell getCell() {
        if (A.value == Cell.NONE) return A;
        if (B.value == Cell.NONE) return B;
        if (C.value == Cell.NONE) return C;
        return null;
    }
}
