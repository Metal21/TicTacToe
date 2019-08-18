package com.workout.metal.tictactoe.game.computer;

import com.workout.metal.tictactoe.game.field.GameField;
import com.workout.metal.tictactoe.game.field.Point;
import com.workout.metal.tictactoe.game.field.PointCombination;

import java.util.LinkedList;
import java.util.Random;

public class CompEasy implements Computer {


    public void stepComputer(GameField field, int comp, int hum) {

        Point p;
        PointCombination[] comb = field.getComb();
        //Если есть поле с двумя 'comp',ставлю третий
        for (PointCombination pc : comb) { if (pc.coll(comp) == 2 && pc.coll(hum) == 0) { field.pick(comp,pc.getPoint());return; } }
        //Если есть поле с двумя 'hum',не даю противнику поставить третий
        for (PointCombination pc : comb) { if (pc.coll(hum) == 2 && pc.coll(comp) == 0) { field.pick(comp,pc.getPoint());return; } }

        LinkedList<Integer> listComb = new LinkedList<>();
        Random rand = new Random();
        while(listComb.size()<8){
            int a = rand.nextInt(8);
            if(!listComb.contains(a))listComb.add(a);
        }
        LinkedList<PointCombination> randComb = new LinkedList<>();
        for(int a:listComb)randComb.add(comb[a]);

        //Ставлю 'comp' в поле, где уже есть 1
        for (int i = 7; i >= 0; i--) {if (comb[i].coll(comp) == 1 && comb[i].coll(hum) == 0) {field.pick(comp,getPoint(comb[i]));return;}}
        //Ставлю 'comp' в пустое поле
        for (int i = 7; i >= 0; i--) {if (comb[i].coll(comp) == 0 && comb[i].coll(hum) == 0) {field.pick(comp,getPoint(comb[i]));return;}}
        //Ставлю 'comp' в оставшиеся клетки
        for (PointCombination pc : comb) {if (pc.isClearPoint()) {field.pick(comp,pc.getPoint());return;}}
    }

    private Point getPoint(PointCombination pc) {
        LinkedList<Integer> list = new LinkedList<>();
        Random rand = new Random();
        while(list.size()<3){
            int a = rand.nextInt(3);
            if(!list.contains(a))list.add(a);
        }
        while(list.size()>0){
            switch(list.removeFirst()){
                case 0:if (pc.C.value == Point.NONE) return pc.C;break;
                case 1:if (pc.A.value == Point.NONE) return pc.A;break;
                case 2:if (pc.B.value == Point.NONE) return pc.B;break;
            }
        }
        return null;
    }
}
