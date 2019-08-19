package com.workout.metal.tictactoe.game;

import com.workout.metal.tictactoe.game.computer.CompEasy;
import com.workout.metal.tictactoe.game.computer.Computer;
import com.workout.metal.tictactoe.game.computer.CompHard;
import com.workout.metal.tictactoe.game.connect.GameEvent;
import com.workout.metal.tictactoe.game.connect.Gui;
import com.workout.metal.tictactoe.game.field.GameField;
import com.workout.metal.tictactoe.game.field.Cell;

import java.util.LinkedList;

public class TicTacToe {

    public static final int PLAYER     = 1;
    public static final int COMPUTER   = 2;
    public static final int LEVEL_EASY = 3;
    public static final int LEVEL_HARD = 4;

    private int enemy;
    private int level;
    private boolean firstStepComputer;

    private GameEvent gameEvent;
    private Computer comp;
    private GameField field;

    private boolean isGame;//идет ли в данный момент партия
    private boolean stepX;//в режиме PLAYER с помощью этой переменной определяется, чья очередб ходить

    public TicTacToe(Gui gui, GameEvent gameEvent) {
        this.gameEvent = gameEvent;
        field = new GameField(gui);
    }

    /*** Событие ввода  ***/
    public void fieldClick(int l, int c) {
        if(!isGame||!field.isEmptyCell(l,c))return;
        switch (enemy){
            case PLAYER:
                if(stepX) field.setCell(Cell.X,l,c);
                else field.setCell(Cell.O,l,c);
                checkWin();
                stepX = !stepX;
                break;
            case COMPUTER:
                int pc = Cell.O,hum = Cell.X;
                if(firstStepComputer){
                    pc = hum;
                    hum = Cell.O;
                }
                field.setCell(hum,l,c);
                checkWin();
                if(isGame){
                    comp.stepComputer(field,pc,hum);
                    checkWin();
                }
                break;
        }
    }

    private void checkWin(){
        int winner = field.checkWinner();
        if(winner != Cell.NONE){
            if(winner == Cell.X){
                if(enemy == PLAYER) gameEvent.gameEvent(GameEvent.WINNER_PLAYER_X);
                else {
                    if(firstStepComputer) gameEvent.gameEvent(GameEvent.WINNER_COMPUTER);
                    else gameEvent.gameEvent(GameEvent.WINNER_PLAYER);
                }
            } else {
                if(enemy == PLAYER) gameEvent.gameEvent(GameEvent.WINNER_PLAYER_O);
                else {
                    if(!firstStepComputer) gameEvent.gameEvent(GameEvent.WINNER_COMPUTER);
                    else gameEvent.gameEvent(GameEvent.WINNER_PLAYER);
                }
            }
            isGame = false;
            return;
        }
        if(!field.hasGap()){
            gameEvent.gameEvent(GameEvent.NO_SPACE);
            isGame = false;
        }
    }

    /*** Управление игрой
     * после установки(изменения) параметров противника нужно вызвать метод newGame()***/
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
        field.clearCells();
        stepX = true;
        isGame = true;
        if(enemy == COMPUTER&&firstStepComputer)comp.stepComputer(field, Cell.X, Cell.O);
    }

    /*** Сохранение и загрузка состояния ***/
    public Object[] saveData(){
        LinkedList<Integer> dataInt = new LinkedList<>();
        dataInt.add(enemy);
        dataInt.add(level);
        field.saveData(dataInt);
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
        field.addData(list);

        switch (enemy){
            case PLAYER:  vsPlayer();break;
            case COMPUTER:vsComputer(firstStepComputer,level);break;
        }
        isGame = dataBoll[0];
    }



}
