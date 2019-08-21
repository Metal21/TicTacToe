package com.workout.metal.tictactoe.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Tools {

    public static int[] fromListToArray(List<Integer> list){
        int[] ret = new int[list.size()];
        for (int i = 0; i < ret.length; i++) ret[i] = list.get(i);
        return ret;
    }

    public static LinkedList<Integer> fromArrayToLinkedList(int [] array){
        LinkedList<Integer> list = new LinkedList<>();
        for(int i:array)list.add(i);
        return list;
    }

    public static int[] getRandomNumbers(int size){
        List<Integer> list = new ArrayList<>();
        Random rand = new Random();
        while(list.size()<size){
            int a = rand.nextInt(size);
            if(!list.contains(a))list.add(a);
        }
        return fromListToArray(list);
    }

}
