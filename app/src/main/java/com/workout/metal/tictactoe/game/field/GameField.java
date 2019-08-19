package com.workout.metal.tictactoe.game.field;

import com.workout.metal.tictactoe.game.connect.Gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameField {

    private final int size = 3;
    private  Cell[][] cells;
    private CellCombs[] combs;
    private Gui gui;

    public GameField(Gui gui) {
        this.gui =gui;
        cells = new Cell[size][size];
        for (int l = 0; l != size; l++)
            for (int c = 0; c != size; c++) cells[l][c] = new Cell(l,c);

        combs = new CellCombs[8];
        for (int l = 0, c = 0; l < 6; l++, c++) {
            if (l < 3) combs[l] = new CellCombs(cells[l][0], cells[l][1], cells[l][2]);
            if (l == 3) c = 0;
            if (l >= 3) combs[l] = new CellCombs(cells[0][c], cells[1][c], cells[2][c]);
        }
        combs[6] = new CellCombs(cells[0][0], cells[1][1], cells[2][2]);
        combs[7] = new CellCombs(cells[0][2], cells[1][1], cells[2][0]);
    }

    /*** Работа с клетками ***/
    public boolean isEmptyCell(int l, int c) {
        return cells[l][c].value == Cell.NONE;
    }

    public void setCell(int what, int l, int c) {
        cells[l][c].value = what;
        if(what == Cell.X)gui.setX(l,c);
        else gui.setO(l,c);
    }

    public void setCell(int what, Cell p) {
        setCell(what,p.l,p.c);
    }

    public int quantitySetCells() {
        final List<Cell> list = new ArrayList<>();
        iterator(new IteratorLambda() {
            public void item(Cell p) {
                if (p.value != Cell.NONE) list.add(p);
            }
        });
        return list.size();
    }

    public void clearCells() {
        iterator(new IteratorLambda() {
            public void item(Cell p) {
                p.value = Cell.NONE;
            }
        });
        gui.clearAll();
    }

    public CellCombs[] getCombs() { return combs; }

    /*** Проверка победителя, конца игры ***/
    public int checkWinner(){
        int winner = Cell.NONE;
        for(CellCombs pn: combs){
            if(pn.quantity(Cell.X)==size){winner = Cell.X;break;}
            if(pn.quantity(Cell.O)==size){winner = Cell.O;break;}
        }
        return winner;
    }

    public boolean hasGap(){ return quantitySetCells()<(size*size); }

    /*** Сохранение и загрузка состояния ***/
    public void saveData(final LinkedList<Integer> dataInt) {
        iterator(new IteratorLambda() {
            public void item(Cell p) {
                dataInt.add(p.value);
            }
        });
    }

    public void addData(final LinkedList<Integer> dataInt){
        iterator(new IteratorLambda() {
            public void item(Cell p) {
                p.value = dataInt.removeFirst();
                if(p.value == Cell.X)gui.setX(p.l,p.c);
                if(p.value == Cell.O)gui.setO(p.l,p.c);
            }
        });
    }

    private void iterator(IteratorLambda il){
        for(int l = 0;l < 3;l++)
            for(int c = 0;c < 3;c++)
                il.item( cells[l][c]);
        }

    interface IteratorLambda{
        void item(Cell b);
    }
}
