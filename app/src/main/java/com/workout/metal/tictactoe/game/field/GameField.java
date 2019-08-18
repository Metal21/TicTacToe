package com.workout.metal.tictactoe.game.field;

import com.workout.metal.tictactoe.game.connect.Gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameField {

    private final int size = 3;
    private  Point[][] point;
    private PointCombination[] comb;
    private Gui gui;

    public GameField(Gui gui) {
        this.gui =gui;
        point = new Point[size][size];
        for (int l = 0; l != size; l++)
            for (int c = 0; c != size; c++) point[l][c] = new Point(l,c);

        comb = new PointCombination[8];
        for (int l = 0, c = 0; l < 6; l++, c++) {
            if (l < 3) comb[l] = new PointCombination(point[l][0], point[l][1], point[l][2]);
            if (l == 3) c = 0;
            if (l >= 3) comb[l] = new PointCombination(point[0][c], point[1][c], point[2][c]);
        }
        comb[6] = new PointCombination(point[0][0], point[1][1], point[2][2]);
        comb[7] = new PointCombination(point[0][2], point[1][1], point[2][0]);
    }

    /*** Работа с клетками ***/
    public boolean isEmpty(int l, int c) {
        return point[l][c].value == Point.NONE;
    }

    public void pick(int what,int l, int c) {
        point[l][c].value = what;
        if(what == Point.X)gui.setX(l,c);
        else gui.setO(l,c);
    }

    public void pick(int what,Point p) {
        pick(what,p.l,p.c);
    }

    public int collPick() {
        final List<Point> list = new ArrayList<>();
        iterator(new IteratorLambda() {
            public void item(Point p) {
                if (p.value != Point.NONE) list.add(p);
            }
        });
        return list.size();
    }

    public void clear() {
        iterator(new IteratorLambda() {
            public void item(Point p) {
                p.value = Point.NONE;
            }
        });
        gui.clear();
    }

    public PointCombination[] getComb() { return comb; }

    /*** Проверка победителя, конца игры ***/
    public int checkWinner(){
        int winner = Point.NONE;
        for(PointCombination pn:comb){
            if(pn.coll(Point.X)==size){winner = Point.X;break;}
            if(pn.coll(Point.O)==size){winner = Point.O;break;}
        }
        return winner;
    }

    public boolean hasGap(){ return collPick()<(size*size); }

    /*** Сохранение и загрузка состояния ***/
    public void saveData(final LinkedList<Integer> dataInt) {
        iterator(new IteratorLambda() {
            public void item(Point p) {
                dataInt.add(p.value);
            }
        });
    }

    public void addData(final LinkedList<Integer> dataInt){
        iterator(new IteratorLambda() {
            public void item(Point p) {
                p.value = dataInt.removeFirst();
                if(p.value == Point.X)gui.setX(p.l,p.c);
                if(p.value == Point.O)gui.setO(p.l,p.c);
            }
        });
    }

    private void iterator(IteratorLambda il){
        for(int l = 0;l < 3;l++)
            for(int c = 0;c < 3;c++)
                il.item( point[l][c]);
        }

    interface IteratorLambda{
        void item(Point b);
    }
}
