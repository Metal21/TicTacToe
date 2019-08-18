package com.workout.metal.tictactoe;

import android.view.View;
import android.widget.Button;

import com.workout.metal.tictactoe.game.TicTacToe;
import com.workout.metal.tictactoe.game.connect.Gui;

public class GuiVersion1 implements Gui, View.OnClickListener {

    private Button[][] mButtons;
    private TicTacToe mTicTacToe;

    public GuiVersion1(MainActivity context) {
        mButtons = new Button[3][3];
        mButtons[0][0] = context.findViewById(R.id.b11);
        mButtons[0][1] = context.findViewById(R.id.b12);
        mButtons[0][2] = context.findViewById(R.id.b13);

        mButtons[1][0] = context.findViewById(R.id.b21);
        mButtons[1][1] = context.findViewById(R.id.b22);
        mButtons[1][2] = context.findViewById(R.id.b23);

        mButtons[2][0] = context.findViewById(R.id.b31);
        mButtons[2][1] = context.findViewById(R.id.b32);
        mButtons[2][2] = context.findViewById(R.id.b33);

        final View.OnClickListener listener = this;
        iterator(new IteratorLambda() {
            public boolean item(Button b, int l, int c) {
                b.setOnClickListener(listener);
                return false;
            }
        });
    }

    void setGame(TicTacToe ticTacToe){this.mTicTacToe = ticTacToe;}

    public void onClick(final View view) {
        iterator(new IteratorLambda() {
            public boolean item(Button b, int l, int c) {
                if(b == view){
                    mTicTacToe.event(l,c);
                    return true;
                }
                return false;
            }
        });
    }

    public void setX(int line, int column) {
        mButtons[line][column].setText("X");
    }

    public void setO(int line, int column) {
        mButtons[line][column].setText("O");
    }

    public void clear() {
        iterator(new IteratorLambda() {
            public boolean item(Button b, int l, int c) {
                b.setText(" ");
                return false;
            }
        });
    }

    private void iterator(IteratorLambda il){
        boolean exit = false;
        for(int l = 0;l < 3;l++){
            for(int c = 0;c < 3;c++){
                exit = il.item( mButtons[l][c],l,c);
            }
            if(exit)break;
        }
    }

    interface IteratorLambda{
        boolean item(Button b,int l,int c);
    }
}
