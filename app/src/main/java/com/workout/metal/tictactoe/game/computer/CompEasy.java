package com.workout.metal.tictactoe.game.computer;

import com.workout.metal.tictactoe.game.field.GameField;
import com.workout.metal.tictactoe.game.field.Cell;
import com.workout.metal.tictactoe.game.field.CellCombs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CompEasy implements Computer {


    public void stepComputer(GameField field, int comp, int hum) {

        CellCombs[] combs = field.getCombs();
        //Если есть поле с двумя 'comp',ставлю третий
        for (CellCombs comb : combs) { if (comb.quantity(comp) == 2 && comb.quantity(hum) == 0) { field.setCell(comp,comb.getCell());return; } }
        //Если есть поле с двумя 'hum',не даю противнику поставить третий
        for (CellCombs comb : combs) { if (comb.quantity(hum) == 2 && comb.quantity(comp) == 0) { field.setCell(comp,comb.getCell());return; } }

        List<CellCombs> randCombs = getRandomCombs(combs);
        //Ставлю 'comp' в поле, где уже есть 1
        for (CellCombs comb : randCombs) {if (comb.quantity(comp) == 1 && comb.quantity(hum) == 0) {field.setCell(comp, getCell(comb));return;}}
        //Ставлю 'comp' в пустое поле
        for (CellCombs comb : randCombs) {if (comb.quantity(comp) == 0 && comb.quantity(hum) == 0) {field.setCell(comp, getCell(comb));return;}}
        //Ставлю 'comp' в оставшиеся клетки
        for (CellCombs comb : randCombs) {if (comb.isEmptyCell()) {field.setCell(comp,comb.getCell());return;}}
    }

    private Cell getCell(CellCombs pc) {
        LinkedList<Integer> list = new LinkedList<>();
        Random rand = new Random();
        while(list.size()<3){
            int a = rand.nextInt(3);
            if(!list.contains(a))list.add(a);
        }
        while(list.size()>0){
            switch(list.removeFirst()){
                case 0:if (pc.C.value == Cell.NONE) return pc.C;break;
                case 1:if (pc.A.value == Cell.NONE) return pc.A;break;
                case 2:if (pc.B.value == Cell.NONE) return pc.B;break;
            }
        }
        return null;
    }

    private List<CellCombs> getRandomCombs(CellCombs[] combs){
        List<Integer> listComb = new ArrayList<>();
        Random rand = new Random();
        while(listComb.size()<8){
            int a = rand.nextInt(8);
            if(!listComb.contains(a))listComb.add(a);
        }
        List<CellCombs> randCombs = new ArrayList<>();
        for(int a:listComb)randCombs.add(combs[a]);
        return randCombs;
    }
}
