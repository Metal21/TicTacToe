package com.workout.metal.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.workout.metal.tictactoe.game.TicTacToe;
import com.workout.metal.tictactoe.game.connect.GameEvent;


public class MainActivity extends AppCompatActivity implements GameEvent, RadioGroup.OnCheckedChangeListener {

    private final static String TITLE       = "tl";
    private final static String ENGINE_INT  = "ei";
    private final static String ENGINE_BOOL = "eb";
    private final static String P_ENEMY     = "pe";
    private final static String P_LEVEL     = "pl";
    private final static String P_UPDATE    = "pu";

    private TicTacToe mTicTacToe;
    private Button newGame;
    private TextView title;
    private GuiVersion1 gui;

    private RadioGroup rg1;
    private RadioGroup rg2;
    private CheckBox cb;

    private int enemy;
    private int level;
    private boolean updateParam;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.app_content);
        newGame = findViewById(R.id.new_game);
        newGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(updateParam){
                    if(enemy == TicTacToe.PLAYER) mTicTacToe.vsPlayer();
                    else mTicTacToe.vsComputer(cb.isChecked(),level);
                    updateParam = false;
                }
                mTicTacToe.newGame();
                if(enemy == TicTacToe.PLAYER)title.setText(R.string.player_vs_player);
                else title.setText(R.string.player_vs_computer);
            }
        });
        title = findViewById(R.id.title);

        rg1 = findViewById(R.id.rg1);
        rg2 = findViewById(R.id.rg2);
        rg1.setOnCheckedChangeListener(this);
        rg2.setOnCheckedChangeListener(this);
        rg2.setVisibility(View.INVISIBLE);
        cb = findViewById(R.id.cb);
        cb.setVisibility(View.INVISIBLE);
        cb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateParam = true;
            }
        });

        gui = new GuiVersion1(this);
        mTicTacToe = new TicTacToe(gui,this);

        if(bundle==null){
            enemy = TicTacToe.PLAYER;
            level = TicTacToe.LEVEL_EASY;
            title.setText(R.string.player_vs_player);
            mTicTacToe.vsPlayer();
            mTicTacToe.newGame();
        } else {
            title.setText(bundle.getString(TITLE));
            enemy = bundle.getInt(P_ENEMY);
            level = bundle.getInt(P_LEVEL);
            updateParam = bundle.getBoolean(P_UPDATE);
            mTicTacToe.addData(
                    bundle.getIntArray(ENGINE_INT),
                    bundle.getBooleanArray(ENGINE_BOOL)
            );
        }
    }

    public void fieldClick(View view){
        int[]lc = gui.onClick(view);
        mTicTacToe.fieldClick(lc[0],lc[1]);
    }

    public void gameEvent(int type) {
        switch (type){
            case GameEvent.WINNER_PLAYER_X:
                title.setText(R.string.win_x); break;
            case GameEvent.WINNER_PLAYER_O:
                title.setText(R.string.win_o); break;
            case GameEvent.WINNER_PLAYER:
                title.setText(R.string.win_player);break;
            case GameEvent.WINNER_COMPUTER:
                title.setText(R.string.win_computer);break;
            case GameEvent.NO_SPACE:
                title.setText(R.string.no_winner);break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int rbId) {
        System.out.println(rbId);
        if(radioGroup == rg1){
            switch (rbId){
                case R.id.rb1:
                    rg2.setVisibility(View.INVISIBLE);
                    cb.setVisibility(View.INVISIBLE);
                    enemy = TicTacToe.PLAYER;
                    break;
                case R.id.rb2:
                    rg2.setVisibility(View.VISIBLE);
                    cb.setVisibility(View.VISIBLE);
                    enemy = TicTacToe.COMPUTER;
                    cb.setChecked(false);
                    RadioButton rb = findViewById(R.id.rb3);
                    rb.setChecked(true);
                    level = TicTacToe.LEVEL_EASY;
                    break;
            }
        }
        if(radioGroup == rg2){
            switch (rbId){
                case R.id.rb3:level = TicTacToe.LEVEL_EASY;break;
                case R.id.rb4:level = TicTacToe.LEVEL_HARD;break;
            }
        }
        updateParam = true;
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Object [] data = mTicTacToe.saveData();
        outState.putIntArray(ENGINE_INT, (int[]) data[0]);
        outState.putBooleanArray(ENGINE_BOOL, (boolean[]) data[1]);
        outState.putString(TITLE, String.valueOf(title.getText()));
        outState.putInt(P_ENEMY,enemy);
        outState.putInt(P_LEVEL,level);
        outState.putBoolean(P_UPDATE,updateParam);
    }

}
