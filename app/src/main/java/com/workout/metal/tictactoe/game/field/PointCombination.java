package com.workout.metal.tictactoe.game.field;

public class PointCombination {

    public Point A, B, C;

    public PointCombination(Point a, Point b, Point c) {
        A = a;
        B = b;
        C = c;

    }
    //кол-во крестиков/ноликов в комбинации
    public int coll(int who) {
        int ret = 0;
        if (A.value == who) ret++;
        if (B.value == who) ret++;
        if (C.value == who) ret++;
        return ret;
    }
    //есть ли пустая клетка
    public boolean isClearPoint() {
        if (A.value == Point.NONE) return true;
        if (B.value == Point.NONE) return true;
        if (C.value == Point.NONE) return true;
        return false;
    }
    //извлекает первую попавшуюся пустую клетку
    public Point getPoint() {
        if (A.value == Point.NONE) return A;
        if (B.value == Point.NONE) return B;
        if (C.value == Point.NONE) return C;
        return null;
    }
}
