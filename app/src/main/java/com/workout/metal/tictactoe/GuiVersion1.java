package com.workout.metal.tictactoe;

import android.view.View;
import android.widget.Button;

import com.workout.metal.tictactoe.game.connect.Gui;

public class GuiVersion1 implements Gui {

    private Button[][] buttons;

    public GuiVersion1(MainActivity context) {
        buttons = new Button[3][3];
        buttons[0][0] = context.findViewById(R.id.b11);
        buttons[0][1] = context.findViewById(R.id.b12);
        buttons[0][2] = context.findViewById(R.id.b13);

        buttons[1][0] = context.findViewById(R.id.b21);
        buttons[1][1] = context.findViewById(R.id.b22);
        buttons[1][2] = context.findViewById(R.id.b23);

        buttons[2][0] = context.findViewById(R.id.b31);
        buttons[2][1] = context.findViewById(R.id.b32);
        buttons[2][2] = context.findViewById(R.id.b33);
    }

    public int[] onClick(final View view) {
        final int [] ret = new int[2];
        iterator(new IteratorLambda() {
            public boolean item(Button b, int l, int c) {
                if(b == view){
                    ret[0] = l;
                    ret[1] = c;
                    return true;
                }
                return false;
            }
        });
        return ret;
    }

    public void setX(int line, int column) {
        buttons[line][column].setText("X");
    }

    public void setO(int line, int column) {
        buttons[line][column].setText("O");
    }

    public void clearAll() {
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
                exit = il.item( buttons[l][c],l,c);
            }
            if(exit)break;
        }
    }

    interface IteratorLambda{
        boolean item(Button b,int l,int c);
    }
}
