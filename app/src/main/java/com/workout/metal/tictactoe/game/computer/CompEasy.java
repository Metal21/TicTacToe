package com.workout.metal.tictactoe.game.computer;

import com.workout.metal.tictactoe.game.Tools;
import com.workout.metal.tictactoe.game.field.GameField;
import com.workout.metal.tictactoe.game.field.Cell;
import com.workout.metal.tictactoe.game.field.CellCombs;

import java.util.ArrayList;
import java.util.List;

public class CompEasy implements Computer {


    public void stepComputer(GameField field, int comp, int hum) {

        List<CellCombs> combs = field.getCombs();
        //Если есть поле с двумя 'comp',ставлю третий
        for (CellCombs comb : combs) { if (comb.getNumbers(comp) == 2 && comb.getNumbers(hum) == 0) { field.setCell(comp,comb.getCell());return; } }
        //Если есть поле с двумя 'hum',не даю противнику поставить третий
        for (CellCombs comb : combs) { if (comb.getNumbers(hum) == 2 && comb.getNumbers(comp) == 0) { field.setCell(comp,comb.getCell());return; } }

        List<CellCombs> randCombs = getRandomCombs(combs);
        //Ставлю 'comp' в поле, где уже есть 1
        for (CellCombs comb : randCombs) {if (comb.getNumbers(comp) == 1 && comb.getNumbers(hum) == 0) {field.setCell(comp, getCell(comb));return;}}
        //Ставлю 'comp' в пустое поле
        for (CellCombs comb : randCombs) {if (comb.getNumbers(comp) == 0 && comb.getNumbers(hum) == 0) {field.setCell(comp, getCell(comb));return;}}
        //Ставлю 'comp' в оставшиеся клетки
        for (CellCombs comb : randCombs) {if (comb.isEmptyCell()) {field.setCell(comp,comb.getCell());return;}}
    }

    private Cell getCell(CellCombs pc) {
        int [] random = Tools.getRandomNumbers(3);
        for(int i = 0; i < random.length;i++){
            switch (random[i]){
                case 0:if (pc.C.value == Cell.NONE) return pc.C;break;
                case 1:if (pc.A.value == Cell.NONE) return pc.A;break;
                case 2:if (pc.B.value == Cell.NONE) return pc.B;break;
            }
        }
        return null;
    }

    private List<CellCombs> getRandomCombs(List<CellCombs> combs){
        List<CellCombs> randCombs = new ArrayList<>();
        for(int a:Tools.getRandomNumbers(8))randCombs.add(combs.get(a));
        return randCombs;
    }
}
