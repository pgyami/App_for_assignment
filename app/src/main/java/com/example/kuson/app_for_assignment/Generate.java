package com.example.kuson.app_for_assignment;

import java.util.Random;

/**
 * Created by asus on 3/31/2015.
 */
public class Generate {
    private int number1;
    private int number2;
    private int result;
    private int difficult;
    private int level;
    private long roundTime;
    private long clockTick;


    public Generate(int _difficult){
        difficult = _difficult;
        level = 0;
        number1 = 0;
        number2 = 0;
        result = 0;
        roundTime = 0;
        clockTick = 0;
    }

    public int getNumber1() {
        return number1;
    }

    public int getNumber2() {
        return number2;
    }

    public int getResult() {
        return result;
    }

    public long getRoundTime() {
        return roundTime;
    }

    public long getClockTick() {
        return clockTick;
    }

    public int getLevel() {
        return level;
    }
    //Generate Math
    public void GenerateMath(){
        int sign;       //SIGN OF NUMBER_2
        int sign_error; //SIGN OF ERROR
        int error = 0;
        int randomsize; //randomsize = 20 if diff = 1 ---- randomsize = 30 if diff = 2 ----- else randomsize = 50
        Random randomizer = new Random();

        level++;
        if(difficult == 1) randomsize = 20;
        else if(difficult == 2) randomsize = 30;
        else randomsize = 50;


        int gen_sign = randomizer.nextInt(2);   //IF 0 -> SIGN = + ELSE SIGN = -
        if(gen_sign == 0) sign = 1;
        else sign = -1;

        //CREATE A AND B
        number1 = (1 + randomizer.nextInt(randomsize));
        number2 = sign*(1 + randomizer.nextInt(randomsize));
        int test = randomizer.nextInt(2);   //IF 0 -> FALSE // ELSE  -> TRUE
        if(test==0){

            int gen_sign_x = randomizer.nextInt(2); //IF 0 -> SIGN = + ELSE SIGN = -
            if(gen_sign_x == 0) sign_error = 1;
            else sign_error = -1;
            error = sign_error*(1 + randomizer.nextInt(3));

        }
        result = number1 + number2 + error;
        roundTime = (long)(Global_Variable.TOTAL_TIME*1000 - (level - 1)*Global_Variable.DECREASE_TIME*1000);
        clockTick = roundTime/Global_Variable.MAX_PROGRESSBAR;
    }
}
