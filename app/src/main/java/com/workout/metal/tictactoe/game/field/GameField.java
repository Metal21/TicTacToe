package com.workout.metal.tictactoe.game.field;

import com.workout.metal.tictactoe.game.connect.Gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameField {

    public static final int size = 3;
    private final Cell[][]    cells;
    private final List<CellCombs> combs;
    private final Gui gui;

    public GameField(Gui gui) {
        this.gui =gui;
        cells = new Cell[size][size];
        for (int l = 0; l != size; l++)
            for (int c = 0; c != size; c++) cells[l][c] = new Cell(l,c);

        combs = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (i < 3)combs.add(new CellCombs(cells[i][0], cells[i][1], cells[i][2]));
            else      combs.add(new CellCombs(cells[0][i-3], cells[1][i-3], cells[2][i-3]));
        }
        combs.add(new CellCombs(cells[0][0], cells[1][1], cells[2][2]));
        combs.add(new CellCombs(cells[0][2], cells[1][1], cells[2][0]));
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

    public int getNumberSetCells() {
        final List<Cell> list = new ArrayList<>();
        iterator(new IteratorLambda() {
            public void item(Cell c) {
                if (c.value != Cell.NONE) list.add(c);
            }
        });
        return list.size();
    }

    public void clearCells() {
        iterator(new IteratorLambda() {
            public void item(Cell c) {
                c.value = Cell.NONE;
            }
        });
        gui.clearAll();
    }

    public List<CellCombs> getCombs() { return combs; }

    /*** Проверка победителя, конца игры ***/
    public int checkWinner(){
        for(CellCombs comb: combs){
            if(comb.getNumbers(Cell.X)==size)return Cell.X;
            if(comb.getNumbers(Cell.O)==size)return Cell.O;
        }
        return Cell.NONE;
    }

    public boolean isSpace(){ return getNumberSetCells()<(size*size); }

    /*** Сохранение и загрузка состояния ***/
    public void saveData(final LinkedList<Integer> dataInt) {
        iterator(new IteratorLambda() {
            public void item(Cell c) {
                dataInt.add(c.value);
            }
        });
    }

    public void addData(final LinkedList<Integer> dataInt){
        iterator(new IteratorLambda() {
            public void item(Cell c) {
                c.value = dataInt.removeFirst();
                if(c.value == Cell.X)gui.setX(c.l,c.c);
                if(c.value == Cell.O)gui.setO(c.l,c.c);
            }
        });
    }

    private void iterator(IteratorLambda il){
        for(int l = 0;l < size;l++)
            for(int c = 0;c < size;c++)
                il.item( cells[l][c]);
        }

    interface IteratorLambda{
        void item(Cell c);
    }
}
