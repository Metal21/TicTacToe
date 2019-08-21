package com.workout.metal.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    private TicTacToe game;
    private TextView title;
    private GuiVersion1 gui;
    private CheckBox firstStepComp;

    private int enemy;
    private int level;
    private boolean updateParam;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.app_content);
        findViewById(R.id.new_game).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(updateParam){
                    if(enemy == TicTacToe.PLAYER) game.vsPlayer();
                    else game.vsComputer(firstStepComp.isChecked(),level);
                    updateParam = false;
                }
                game.newGame();
                if(enemy == TicTacToe.PLAYER)title.setText(R.string.player_vs_player);
                else title.setText(R.string.player_vs_computer);
            }
        });
        title = findViewById(R.id.title);

        RadioGroup rg1 = findViewById(R.id.rg1);
        rg1.setOnCheckedChangeListener(this);
        RadioGroup rg2 = findViewById(R.id.rg2);
        rg2.setOnCheckedChangeListener(this);
        rg2.setVisibility(View.INVISIBLE);
        firstStepComp = findViewById(R.id.cb);
        firstStepComp.setVisibility(View.INVISIBLE);
        firstStepComp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateParam = true;
            }
        });

        gui = new GuiVersion1(this);
        game = new TicTacToe(gui,this);

        if(bundle==null){
            enemy = TicTacToe.PLAYER;
            level = TicTacToe.LEVEL_EASY;
            title.setText(R.string.player_vs_player);
            game.vsPlayer();
            game.newGame();
        } else {
            title.setText(bundle.getString(TITLE));
            enemy = bundle.getInt(P_ENEMY);
            level = bundle.getInt(P_LEVEL);
            updateParam = bundle.getBoolean(P_UPDATE);
            game.addData(
                    bundle.getIntArray(ENGINE_INT),
                    bundle.getBooleanArray(ENGINE_BOOL)
            );
        }
    }

    public void fieldClick(View view){
        GuiVersion1.CellView cv = gui.onClick(view);
        game.fieldClick(cv.l,cv.c);
    }

    public void gameEvent(Event event) {
        switch (event){
            case WINNER_PLAYER_X:
                title.setText(R.string.win_x);
                break;
            case WINNER_PLAYER_O:
                title.setText(R.string.win_o);
                break;
            case WINNER_PLAYER:
                title.setText(R.string.win_player);
                break;
            case WINNER_COMPUTER:
                title.setText(R.string.win_computer);
                break;
            case NO_SPACE:
                title.setText(R.string.no_winner);
                break;
        }
    }

    public void onCheckedChanged(RadioGroup radioGroup, int rbId) {
        System.out.println(rbId);
        if(radioGroup.getId() == R.id.rg1){
            RadioGroup rg2 = findViewById(R.id.rg2);
            switch (rbId){
                case R.id.rb1:
                    rg2.setVisibility(View.INVISIBLE);
                    firstStepComp.setVisibility(View.INVISIBLE);
                    enemy = TicTacToe.PLAYER;
                    break;
                case R.id.rb2:
                    rg2.setVisibility(View.VISIBLE);
                    firstStepComp.setVisibility(View.VISIBLE);
                    enemy = TicTacToe.COMPUTER;
                    firstStepComp.setChecked(false);
                    RadioButton rb = findViewById(R.id.rb3);
                    rb.setChecked(true);
                    level = TicTacToe.LEVEL_EASY;
                    break;
            }
        }
        if(radioGroup.getId() == R.id.rg2){
            switch (rbId){
                case R.id.rb3:level = TicTacToe.LEVEL_EASY;break;
                case R.id.rb4:level = TicTacToe.LEVEL_HARD;break;
            }
        }
        updateParam = true;
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Object [] data = game.saveData();
        outState.putIntArray(ENGINE_INT, (int[]) data[0]);
        outState.putBooleanArray(ENGINE_BOOL, (boolean[]) data[1]);
        outState.putString(TITLE, String.valueOf(title.getText()));
        outState.putInt(P_ENEMY,enemy);
        outState.putInt(P_LEVEL,level);
        outState.putBoolean(P_UPDATE,updateParam);
    }

}
