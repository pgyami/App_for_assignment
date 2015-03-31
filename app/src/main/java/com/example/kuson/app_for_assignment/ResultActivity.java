package com.example.kuson.app_for_assignment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;


public class ResultActivity extends ActionBarActivity {


        private void writeToFile(String data) {
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(Global_Variable.HIGH_SCORE_FILE_NAME, Context.MODE_PRIVATE));
                outputStreamWriter.write(data);
                outputStreamWriter.close();
                System.out.println("write highscore successfull");
            }
            catch (IOException e) {
                System.out.println("Exception: File write failed: " + e.toString());
            }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView score_text = (TextView)findViewById(R.id.score_text);
        score_text.setText(String.valueOf(Global_Variable.LEVEL));

        TextView hiscore_text = (TextView)findViewById(R.id.hiscore_text);
        if(Global_Variable.LEVEL>Global_Variable.HIGH_SCORE)
        { Global_Variable.HIGH_SCORE=Global_Variable.LEVEL;
        writeToFile(""+Global_Variable.HIGH_SCORE);}
        System.out.println(Global_Variable.HIGH_SCORE);
        hiscore_text.setText(String.valueOf(Global_Variable.HIGH_SCORE));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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

    public void showGame(View clickedButton){
        Intent activityIntent = new Intent(this, GameActivity.class);
        startActivity(activityIntent);
    }

    public void showMain(View clickedButton){
        Intent activityIntent = new Intent(this, MainActivity.class);
        startActivity(activityIntent);
    }
}
