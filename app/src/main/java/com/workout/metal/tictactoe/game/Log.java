package com.workout.metal.tictactoe.game;

public class Log {

    public static final String LOG = "game.engine";
    public static void d(String message){
        android.util.Log.d(LOG,message);
    }
}
