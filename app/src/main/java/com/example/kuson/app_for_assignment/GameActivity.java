package com.example.kuson.app_for_assignment;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


public class GameActivity extends ActionBarActivity {

    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i=0;
    int level = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        level++;
        TextView level_text = (TextView)findViewById(R.id.level_text);
        level_text.setText(String.valueOf(level));

        mProgressBar=(ProgressBar)findViewById(R.id.round_time_progressBar);
        mProgressBar.setProgress(i);
        mCountDownTimer=new CountDownTimer(3000,60) {

            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                mProgressBar.setProgress(i);

            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                mProgressBar.setProgress(50);
            }
        };
        mCountDownTimer.start();
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
        Intent activityIntent = new Intent(this, ResultActivity.class);
        startActivity(activityIntent);
    }
}
