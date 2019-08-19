package com.workout.metal.tictactoe.game.computer;

import com.workout.metal.tictactoe.game.field.GameField;
import com.workout.metal.tictactoe.game.field.Cell;
import com.workout.metal.tictactoe.game.field.CellCombs;

import java.util.Random;

public class CompHard implements Computer {


    public CompHard() {

    }

    public void stepComputer(GameField field,int comp, int hum) {

        CellCombs[] comb = field.getCombs();
        //Если есть поле с двумя 'comp',ставлю третий
        for (CellCombs pc : comb) { if (pc.quantity(comp) == 2 && pc.quantity(hum) == 0) { field.setCell(comp,pc.getCell());return; } }
        //Если есть поле с двумя 'hum',не даю противнику поставить третий
        for (CellCombs pc : comb) { if (pc.quantity(hum) == 2 && pc.quantity(comp) == 0) { field.setCell(comp,pc.getCell());return; } }
        //Чекую выигрышную комбинацию противника
        if(field.quantitySetCells()==3){
            if(comb[6].A.value==hum&&comb[6].C.value==hum||
               comb[7].A.value==hum&&comb[7].C.value==hum){
                field.setCell(comp,comb[1].A);
                return;
            }
        }
        //Ставлю 'comp' в поле, где уже есть 1
        for (int i = 7; i >= 0; i--) {if (comb[i].quantity(comp) == 1 && comb[i].quantity(hum) == 0) {field.setCell(comp, getCell(comb[i],i));return;}}
        //Ставлю 'comp' в пустое поле
        for (int i = 7; i >= 0; i--) {if (comb[i].quantity(comp) == 0 && comb[i].quantity(hum) == 0) {field.setCell(comp, getCell(comb[i],i));return;}}
        //Ставлю 'comp' в оставшиеся клетки
        for (CellCombs pc : comb) {if (pc.isEmptyCell()) {field.setCell(comp,pc.getCell());return;}}

    }


    private Cell getCell(CellCombs pc, int i) {
        Random rand = new Random();
        int a = rand.nextInt(2);
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