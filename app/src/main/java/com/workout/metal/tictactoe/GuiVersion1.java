package com.workout.metal.tictactoe;

import android.view.View;
import android.widget.Button;

import com.workout.metal.tictactoe.game.connect.Gui;
import com.workout.metal.tictactoe.game.field.GameField;

import java.util.ArrayList;
import java.util.List;

public class GuiVersion1 implements Gui {

    private final List<CellView> views;

    public GuiVersion1(MainActivity context) {
        views = new ArrayList<>();
        for(int l = 0;l < GameField.size;l++)
            for(int c = 0;c < GameField.size;c++){
                String nameId = "b"+l+String.valueOf(c);
                int viewId = context.getResources().getIdentifier(nameId, "id", context.getPackageName());;
                views.add(new CellView(context.findViewById(viewId),l,c));
            }
    }

    public CellView onClick(final View v) {
        for(CellView view:views)if(view.view == v)return view;
        return null;
    }

    public void setX(int line, int column) {
        for(CellView view:views)if(view.isMine(line,column)){view.view.setText("X");break;}
    }

    public void setO(int line, int column) {
        for(CellView view:views)if(view.isMine(line,column)){view.view.setText("O");break;}
    }

    public void clearAll() {
        for(CellView view:views)view.view.setText("");
    }

    class CellView {

        final Button view;
        final int l;
        final int c;

        CellView(View view, int l, int c) {
            this.view = (Button) view;
            this.l    = l;
            this.c    = c;
        }

        boolean isMine(int line, int column){return l == line&&c == column;}
    }
}
