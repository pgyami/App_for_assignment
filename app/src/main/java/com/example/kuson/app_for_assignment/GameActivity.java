package com.example.kuson.app_for_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends ActionBarActivity {

    ProgressBar mProgressBar;
    int round_time = (int)(Global_Variable.TOTAL_TIME*1000);
    int clock_tick = (int)(round_time/Global_Variable.MAX_PROGRESSBAR);
    boolean time_cancel = true;
    boolean test = true;
    CountDownTimer mCountDownTimer = new CountDownTimer(round_time, clock_tick -10) {

        @Override
        public void onTick(long millisUntilFinished) {
            i++;
            //System.out.println(i + " - " +millisUntilFinished + " - " + clock_tick);
            mProgressBar.setProgress(i);

        }

        @Override
        public void onFinish() {

            if (!time_cancel)
                i++;
           // mProgressBar.setProgress(100);
            reload();

        }
    };
    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Global_Variable.LEVEL = 0;


        mProgressBar=(ProgressBar)findViewById(R.id.round_time_progressBar);
        mProgressBar.setProgress(i);

        Generate generate_math = GenerateMath(Global_Variable.DIFFICULTY);
        TextView question_text = (TextView) findViewById(R.id.question_text);

        int gen_number1 = generate_math.getNumber1();
        int gen_number2 = generate_math.getNumber2();
        int gen_result=generate_math.getResult();


        String text;

        if(gen_number1 + gen_number2 == gen_result)
        {test = true;
            text = "dung";}
        else{ test = false;
            text = "sai";}

        if(generate_math.getNumber2()<0)
            //question_text.setText(gen_number1 + " - " + String.valueOf(gen_number2).substring(1) + " = " + gen_result + "----->" + text);
            question_text.setText(gen_number1 + " - " + String.valueOf(gen_number2).substring(1) + " = " + gen_result);
        else
            //question_text.setText(gen_number1 + " + " + gen_number2 + " = " + gen_result  + "----->" + text);
            question_text.setText(gen_number1 + " + " + gen_number2 + " = " + gen_result);

        getNextLevel();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showResult(View clickedButton){


        if(test)    reload();   //Finish Page
         else getNextLevel(); //Reload

    }

    private void startTimer(int time, int tick){
        time_cancel = false;
        i = 0;
        /*System.out.println(time + " - " + tick);
        while (!time_cancel && i != 100)
        {
            mProgressBar.setProgress(i);
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {
                System.out.println(ex);
            }
            i++;
        }*/
        mCountDownTimer.start();

    }
    private void reload(){                      //Finish Page

        mCountDownTimer.cancel();
        time_cancel = true;
        Intent activityIntent = new Intent(this, ResultActivity.class);
        startActivity(activityIntent);
    }

    private void getNextLevel(){                //Reload
        i = 0;

        mProgressBar=(ProgressBar)findViewById(R.id.round_time_progressBar);
        mCountDownTimer.cancel();
        mProgressBar.setProgress(i);
        Global_Variable.LEVEL++;
        TextView level_text = (TextView)findViewById(R.id.level_text);
        level_text.setText("Cấp độ: " + String.valueOf(Global_Variable.LEVEL));

        startTimer(round_time, clock_tick);
        Generate generate_math = GenerateMath(Global_Variable.DIFFICULTY);
        TextView question_text = (TextView) findViewById(R.id.question_text);

        int gen_number1 = generate_math.getNumber1();
        int gen_number2 = generate_math.getNumber2();
        int gen_result=generate_math.getResult();


        String text;

        if(gen_number1 + gen_number2 == gen_result)
        {test = true;
            text = "dung";}
        else{ test = false;
            text = "sai";}

        if(generate_math.getNumber2()<0)
            question_text.setText(gen_number1 + " - " + String.valueOf(gen_number2).substring(1) + " = " + gen_result);
           // question_text.setText(gen_number1 + " - " + String.valueOf(gen_number2).substring(1) + " = " + gen_result + "----->" + text);
        else
            question_text.setText(gen_number1 + " + " + gen_number2 + " = " + gen_result);
            //question_text.setText(gen_number1 + " + " + gen_number2 + " = " + gen_result  + "----->" + text);

    }

    public void showNextLevel(View clickedButton){
        time_cancel = true;
        if(test) getNextLevel();   //go to next level
        else reload(); //End game
    }


    //Generate Math
    public static Generate GenerateMath(int difficult){
        int sign;       //SIGN OF NUMBER_2
        int sign_error; //SIGN OF ERROR
        int number1;    //A
        int number2;    //B
        int result;     //C
        int error = 0;
        int randomsize; //randomsize = 20 if diff = 1 ---- randomsize = 30 if diff = 2 ----- else randomsize = 50
        Random randomizer = new Random();

        if(difficult == 1) randomsize = 20;
        else if(difficult == 2) randomsize = 30;
        else randomsize = 50;


        int gen_sign = randomizer.nextInt(2);   //IF 0 -> SIGN = + ELSE SIGN = -
        if(gen_sign==0) sign = 1;
        else sign = -1;

        //CREATE A AND B
        number1 = (1+randomizer.nextInt(randomsize));
        number2 = sign*(1+randomizer.nextInt(randomsize));
        int test = randomizer.nextInt(2);   //IF 0 -> FALSE // ELSE  -> TRUE
        if(test==0){

            int gen_sign_x = randomizer.nextInt(2); //IF 0 -> SIGN = + ELSE SIGN = -
            if(gen_sign_x==0) sign_error = 1;
            else sign_error = -1;
            error = sign_error*(1+randomizer.nextInt(3));

        }
        result = number1 + number2 + error;

        return new Generate(number1,number2,result);
    }
}
