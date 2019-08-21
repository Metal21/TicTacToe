package com.workout.metal.tictactoe.game.computer;

import com.workout.metal.tictactoe.game.field.GameField;
import com.workout.metal.tictactoe.game.field.Cell;
import com.workout.metal.tictactoe.game.field.CellCombs;

import java.util.List;
import java.util.Random;

public class CompHard implements Computer {


    public CompHard() {

    }

    public void stepComputer(GameField field,int comp, int hum) {

        List<CellCombs> comb = field.getCombs();
        //Если есть поле с двумя 'comp',ставлю третий
        for (CellCombs pc : comb) { if (pc.getNumbers(comp) == 2 && pc.getNumbers(hum) == 0) { field.setCell(comp,pc.getCell());return; } }
        //Если есть поле с двумя 'hum',не даю противнику поставить третий
        for (CellCombs pc : comb) { if (pc.getNumbers(hum) == 2 && pc.getNumbers(comp) == 0) { field.setCell(comp,pc.getCell());return; } }
        //Чекую выигрышную комбинацию противника
        if(field.getNumberSetCells()==3){
            if(comb.get(6).A.value==hum&&comb.get(6).C.value==hum||
               comb.get(7).A.value==hum&&comb.get(7).C.value==hum){
                field.setCell(comp,comb.get(1).A);
                return;
            }
        }
        //Ставлю 'comp' в поле, где уже есть 1
        for (int i = 7; i >= 0; i--) {if (comb.get(i).getNumbers(comp) == 1 && comb.get(i).getNumbers(hum) == 0) {field.setCell(comp, getCell(comb.get(i),i));return;}}
        //Ставлю 'comp' в пустое поле
        for (int i = 7; i >= 0; i--) {if (comb.get(i).getNumbers(comp) == 0 && comb.get(i).getNumbers(hum) == 0) {field.setCell(comp, getCell(comb.get(i),i));return;}}
        //Ставлю 'comp' в оставшиеся клетки
        for (CellCombs pc : comb) {if (pc.isEmptyCell()) {field.setCell(comp,pc.getCell());return;}}

    }


    private Cell getCell(CellCombs pc, int i) {
        int a = new Random().nextInt(2);
        if (i == 7 || i == 6) {
            if (pc.B.value == Cell.NONE) return pc.B;
            if (pc.A.value == Cell.NONE && a == 0) return pc.A;
            if (pc.C.value == Cell.NONE) return pc.C;
            if (pc.A.value == Cell.NONE) return pc.A;
        }
        if (pc.A.value == Cell.NONE && a == 0) return pc.A;
        if (pc.C.value == Cell.NONE) return pc.C;
        if (pc.B.value == Cell.NONE) return pc.B;
        if (pc.A.value == Cell.NONE) return pc.A;
        return null;
    }
}