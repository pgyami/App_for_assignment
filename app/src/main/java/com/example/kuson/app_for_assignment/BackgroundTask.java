package com.example.kuson.app_for_assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by KU SON on 09/04/2015.
 */
public class BackgroundTask extends AsyncTask<Void, Integer, Void> {
    protected Activity context;
    private ProgressBar mRoundTimeBar, mExtraTimeBar;
    private Generate mGamer;
    private boolean resultOfGamer;
    private boolean gameStatus = true;
    private boolean aaa = true;
    private TextView levelText,questionText;
    private Button yesButton, noButton;
    private int roundTimeStatus = 100;
    private int extraTimeStatus = 100;
    private long extraTimeTick = (long)(Global_Variable.EXTRA_TIME*1000/Global_Variable.MAX_PROGRESSBAR);

    BackgroundTask(Activity _context)
    {
        context = _context;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();

        mRoundTimeBar = (ProgressBar)context.findViewById(R.id.round_time_progressBar);
        mExtraTimeBar = (ProgressBar)context.findViewById(R.id.extra_time_progressBar);
        levelText = (TextView)context.findViewById(R.id.level_text);
        yesButton = (Button)context.findViewById(R.id.yes_button);
        noButton = (Button)context.findViewById(R.id.no_button);

        mGamer = new Generate(Global_Variable.DIFFICULTY);

    }

    @Override
    protected Void doInBackground(Void... params){
        while (gameStatus) {
            getNextLevel();
            while (roundTimeStatus > 0) {
                roundTimeStatus--;
                if (isCancelled())
                    break;
                SystemClock.sleep(mGamer.getClockTick());
                publishProgress(roundTimeStatus, extraTimeStatus);

                if (roundTimeStatus == 0) {
                    aaa = true;
                    while (extraTimeStatus > 0 && aaa) {
                        extraTimeStatus--;
                        if (isCancelled())
                            break;
                        SystemClock.sleep(extraTimeTick);
                        publishProgress(roundTimeStatus, extraTimeStatus);
                    }
                }
            }

            gameStatus = false;
        }
        if (!isCancelled()) {
            Intent activityIntent = new Intent(context, ResultActivity.class);
            context.startActivity(activityIntent);
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        super.onProgressUpdate();
        int roundTimeStatus = values[0];
        int extraTimeStatus = values[1];
        mRoundTimeBar.setProgress(roundTimeStatus);
        mExtraTimeBar.setProgress(extraTimeStatus);
        showText();
    }

    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);
    }


    public void getNextLevel(){                //Reload
        aaa = false;
        roundTimeStatus = 100;
        mGamer.GenerateMath();
        System.out.println("level: " + mGamer.getLevel() + " - " + "roundtime: " + mGamer.getRoundTime() + " - " +"clocktick: " + mGamer.getClockTick());
    }

    private void showText(){

        Global_Variable.LEVEL = mGamer.getLevel() - 1;
        levelText.setText("Cấp độ: " + String.valueOf(mGamer.getLevel()));

        questionText = (TextView)context.findViewById(R.id.question_text);

        int gen_number1 = mGamer.getNumber1();
        int gen_number2 = mGamer.getNumber2();
        int gen_result = mGamer.getResult();

        if(gen_number1 + gen_number2 == gen_result)
            resultOfGamer = true;
        else resultOfGamer = false;

        if(mGamer.getNumber2()<0)
            questionText.setText(gen_number1 + " - " + String.valueOf(gen_number2).substring(1) + " = " + gen_result);
        else
            questionText.setText(gen_number1 + " + " + gen_number2 + " = " + gen_result);
    }

    public boolean checkUserChosen(boolean userChosen){
        if (userChosen == resultOfGamer)
            return true;
        else
            return false;
    }
}
