package com.workout.metal.tictactoe.game;

import com.workout.metal.tictactoe.game.computer.CompEasy;
import com.workout.metal.tictactoe.game.computer.Computer;
import com.workout.metal.tictactoe.game.computer.CompHard;
import com.workout.metal.tictactoe.game.connect.GameEvent;
import com.workout.metal.tictactoe.game.connect.Gui;
import com.workout.metal.tictactoe.game.field.GameField;
import com.workout.metal.tictactoe.game.field.Point;

import java.util.LinkedList;

public class TicTacToe {

    public static final int PLAYER = 1;
    public static final int COMPUTER = 2;
    public static final int LEVEL_EASY = 3;
    public static final int LEVEL_HARD = 4;

    private int enemy;
    private int level;
    private boolean firstStepComputer;

    private GameEvent mGameEvent;
    private Computer comp;
    private GameField mField;

    private boolean isGame;//идет ли в данный момент партия
    private boolean stepX;//

    public TicTacToe(Gui gui, GameEvent mGameEvent) {
        this.mGameEvent = mGameEvent;
        mField = new GameField(gui);
    }

    /*** Событие ввода  ***/
    public void event(int l, int c) {
        if(!isGame||!mField.isEmpty(l,c))return;
        switch (enemy){
            case PLAYER:
                if(stepX)mField.pick(Point.X,l,c);
                else mField.pick(Point.O,l,c);
                checkWin();
                stepX = !stepX;
                break;
            case COMPUTER:
                int pc = Point.O,hum = Point.X;
                if(firstStepComputer){
                    pc = hum;
                    hum = Point.O;
                }
                mField.pick(hum,l,c);
                checkWin();
                if(isGame){
                    comp.stepComputer(mField,pc,hum);
                    checkWin();
                }
                break;
        }
    }

    private void checkWin(){
        int winner = mField.checkWinner();
        if(winner != Point.NONE){
            if(winner == Point.X){
                if(enemy == PLAYER) mGameEvent.gameEvent(GameEvent.WINNER_PLAYER_X);
                else {
                    if(firstStepComputer)mGameEvent.gameEvent(GameEvent.WINNER_COMPUTER);
                    else mGameEvent.gameEvent(GameEvent.WINNER_PLAYER);
                }
            } else {
                if(enemy == PLAYER) mGameEvent.gameEvent(GameEvent.WINNER_PLAYER_O);
                else {
                    if(!firstStepComputer)mGameEvent.gameEvent(GameEvent.WINNER_COMPUTER);
                    else mGameEvent.gameEvent(GameEvent.WINNER_PLAYER);
                }
            }
            isGame = false;
            return;
        }
        if(!mField.hasGap()){
            mGameEvent.gameEvent(GameEvent.NO_SPACE);
            isGame = false;
        }
    }

    /*** Управление игрой
     * после установки(изменения) параметров противника нужно вызвать метод newGame***/
    public void vsPlayer(){
        isGame = false;
        enemy = PLAYER;
    }

    public void vsComputer(boolean firstStepComputer,int compLevel){
        isGame = false;
        enemy = COMPUTER;
        level = compLevel;
        this.firstStepComputer = firstStepComputer;
        switch (level){
            case LEVEL_EASY:comp = new CompEasy();break;
            case LEVEL_HARD:comp = new CompHard();break;
        }
    }

    public void newGame(){
        mField.clear();
        stepX = true;
        isGame = true;
        if(enemy == COMPUTER&&firstStepComputer)comp.stepComputer(mField,Point.X,Point.O);
    }

    /*** Сохранение и загрузка состояния ***/
    public Object[] saveData(){
        LinkedList<Integer> dataInt = new LinkedList<>();
        dataInt.add(enemy);
        dataInt.add(level);
        mField.saveData(dataInt);
        int [] array = new int[dataInt.size()];
        for (int i = 0; i < array.length; i++) array[i] = dataInt.get(i);
        return new Object[]{array,new boolean[]{isGame,stepX,firstStepComputer}};
    }

    public void addData(int[] dataInt, boolean[] dataBoll){
        stepX = dataBoll[1];
        boolean firstStepComputer = dataBoll[2];

        int enemy = dataInt[0];
        int level = dataInt[1];
        LinkedList<Integer> list = new LinkedList<>();
        for(int i = 2;i < dataInt.length;i++)list.add(dataInt[i]);
        mField.addData(list);

        switch (enemy){
            case PLAYER:  vsPlayer();break;
            case COMPUTER:vsComputer(firstStepComputer,level);break;
        }
        isGame = dataBoll[0];
    }



}
