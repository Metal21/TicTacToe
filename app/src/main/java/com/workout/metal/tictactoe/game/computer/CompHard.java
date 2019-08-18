package com.workout.metal.tictactoe.game.computer;

import com.workout.metal.tictactoe.game.field.GameField;
import com.workout.metal.tictactoe.game.field.Point;
import com.workout.metal.tictactoe.game.field.PointCombination;

import java.util.Random;

public class CompHard implements Computer {


    public CompHard() {

    }

    public void stepComputer(GameField field,int comp, int hum) {

        PointCombination[] comb = field.getComb();
        //Если есть поле с двумя 'comp',ставлю третий
        for (PointCombination pc : comb) { if (pc.coll(comp) == 2 && pc.coll(hum) == 0) { field.pick(comp,pc.getPoint());return; } }
        //Если есть поле с двумя 'hum',не даю противнику поставить третий
        for (PointCombination pc : comb) { if (pc.coll(hum) == 2 && pc.coll(comp) == 0) { field.pick(comp,pc.getPoint());return; } }
        //Чекую выигрышную комбинацию противника
        if(field.collPick()==3){
            if(comb[6].A.value==hum&&comb[6].C.value==hum||
               comb[7].A.value==hum&&comb[7].C.value==hum){
                field.pick(comp,comb[1].A);
                return;
            }
        }
        //Ставлю 'comp' в поле, где уже есть 1
        for (int i = 7; i >= 0; i--) {if (comb[i].coll(comp) == 1 && comb[i].coll(hum) == 0) {field.pick(comp,getPoint(comb[i],i));return;}}
        //Ставлю 'comp' в пустое поле
        for (int i = 7; i >= 0; i--) {if (comb[i].coll(comp) == 0 && comb[i].coll(hum) == 0) {field.pick(comp,getPoint(comb[i],i));return;}}
        //Ставлю 'comp' в оставшиеся клетки
        for (PointCombination pc : comb) {if (pc.isClearPoint()) {field.pick(comp,pc.getPoint());return;}}

    }


    private Point getPoint(PointCombination pc,int i) {
        Random rand = new Random();
        int a = rand.nextInt(2);
        if (i == 7 || i == 6) {
            if (pc.B.value == Point.NONE) return pc.B;
            if (pc.A.value == Point.NONE && a == 0) return pc.A;
            if (pc.C.value == Point.NONE) return pc.C;
            if (pc.A.value == Point.NONE) return pc.A;
        }
        if (pc.A.value == Point.NONE && a == 0) return pc.A;
        if (pc.C.value == Point.NONE) return pc.C;
        if (pc.B.value == Point.NONE) return pc.B;
        if (pc.A.value == Point.NONE) return pc.A;
        return null;
    }
}