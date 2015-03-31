package com.example.kuson.app_for_assignment;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;

import javax.microedition.khronos.opengles.GL;


public class GameActivity extends ActionBarActivity {

    ProgressBar mProgressBar;
    int round_time = (int)(Global_Variable.TOTAL_TIME*1000);
    int clock_tick = (int)(round_time/Global_Variable.MAX_PROGRESSBAR);
    boolean time_cancel = true;

    CountDownTimer mCountDownTimer = new CountDownTimer(round_time, clock_tick) {

        @Override
        public void onTick(long millisUntilFinished) {
            i++;
            //System.out.println(i + " - " +millisUntilFinished);
            mProgressBar.setProgress(i);

        }

        @Override
        public void onFinish() {
            //Do what you want
            //System.out.println("cac chinsu");
            if (!time_cancel)
            i++;
            mProgressBar.setProgress(100);
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
        reload();
    }

    private void startTimer(int time, int tick){

        mCountDownTimer.start();
        time_cancel = false;
    }
    private void reload(){

        mCountDownTimer.cancel();

        Intent activityIntent = new Intent(this, ResultActivity.class);
        startActivity(activityIntent);
    }

    private void getNextLevel(){
        i = 0;

       // mProgressBar=(ProgressBar)findViewById(R.id.round_time_progressBar);
        mCountDownTimer.cancel();
        mProgressBar.setProgress(i);
        Global_Variable.LEVEL++;
        TextView level_text = (TextView)findViewById(R.id.level_text);
        level_text.setText(String.valueOf(Global_Variable.LEVEL));

        startTimer(3000, 20);

    }

    public void showNextLevel(View clickedButton){
        time_cancel = true;
        getNextLevel();
    }

}
