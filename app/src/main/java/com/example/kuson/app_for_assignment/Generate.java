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
        int sign;       //Dấu của số bên phải (P)
        int sign_error; //Dấu của biến error
        int error = 0;  //Khai báo biến error = 0 (sẽ thay đổi nếu như muốn tạo câu hỏi SAI)
        int randomsize; //Khai báo khoảng cần random khi thay đổi difficult
        Random randomizer = new Random();   //Khởi tạo đối tượng random

        level++;
        if(difficult == 1) randomsize = 20;         //Nếu difficult == 1 hàm sẽ tạo ra các số trong khoảng từ -20 tới 20
        else if(difficult == 2) randomsize = 30;    //Nếu difficult == 2 hàm sẽ tạo ra các số trong khoảng từ -30 tới 30
        else randomsize = 50;                       //Nếu difficult == 3 hàm sẽ tạo ra các số trong khoảng từ -50 tới 50


        int gen_sign = randomizer.nextInt(2);   //Random ra dấu của Số bên phải (P)
        if(gen_sign == 0) sign = 1;             //Dấu dương (+)
        else sign = -1;                         //Dấu âm (-)

        //CREATE A AND B
        number1 = (1 + randomizer.nextInt(randomsize));             //Tạo ra số bên trái (T)
        number2 = sign*(1 + randomizer.nextInt(randomsize));        //Tạo ra số bên phải (P)
        int test = randomizer.nextInt(2);                           //Random xem câu hỏi sẽ là ĐÚNG hay SAI
        if(test==0){                                                //Tạo ra câu hỏi SAI

            int gen_sign_x = randomizer.nextInt(2);                 //Random ra dấu của ERROR
            if(gen_sign_x == 0) sign_error = 1;                     //Dấu của Error là dấu dương (+)
            else sign_error = -1;                                   //Dấu của Error là dấu âm (-)
            error = sign_error*(1 + randomizer.nextInt(3));         //Tạo Error trong khoảng từ [-3,3] và khác 0

        }
        result = number1 + number2 + error;                         //Tạo ra Số kết quả (KQ)
        roundTime = (long)(Global_Variable.TOTAL_TIME*1000 - (level - 1)*Global_Variable.DECREASE_TIME*1000);
        if (roundTime < 400)
            roundTime = 400;
        clockTick = roundTime/Global_Variable.MAX_PROGRESSBAR;
    }
}
