package com.example.kuson.app_for_assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends ActionBarActivity {

    BackgroundTask progressPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        progressPlayer = new BackgroundTask(this);
        progressPlayer.execute();
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


    public void showResult(){                      //Finish Page
        progressPlayer.cancel(true);

        Intent activityIntent = new Intent(this, ResultActivity.class);
        startActivity(activityIntent);
    }



    public void trueClicked(View clickedButton){
        Global_Variable.GAME_RUNNING_STATUS = false;
        if(progressPlayer.checkUserChosen(true))
        {
            progressPlayer.getNextLevel();
            new Sound().btn_true(this);
        }   //go to next level
        else {showResult();
        } //End game
    }

    public void falseClicked(View clickedButton){
        Global_Variable.GAME_RUNNING_STATUS = false;
        if(progressPlayer.checkUserChosen(false))
        {   new Sound().btn_false(this);
            progressPlayer.getNextLevel(); }  //Finish Page
        else
        {
            showResult();} //Reload
    }
}
