package com.example.kuson.app_for_assignment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;
import java.io.OutputStreamWriter;


public class ResultActivity extends ActionBarActivity {

        //FBShare

    CallbackManager callbackManager;
    ShareDialog shareDialog;
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
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView score_text = (TextView)findViewById(R.id.score_text);
        score_text.setText(String.valueOf(Global_Variable.LEVEL));

        TextView hiscore_text = (TextView)findViewById(R.id.hiscore_text);
        if(Global_Variable.LEVEL>Global_Variable.HIGH_SCORE)

        {Global_Variable.HIGH_SCORE=Global_Variable.LEVEL;
            writeToFile(""+Global_Variable.HIGH_SCORE);
            new Sound().Congrat(this);
        }
        else {
            new Sound().Failure(this);
        }
        hiscore_text.setText(String.valueOf(Global_Variable.HIGH_SCORE));


        // listeners of our two buttons
        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.share_button:
                        shareGame();
                        break;
                }
            }
        };

// our buttons
        findViewById(R.id.share_button).setOnClickListener(handler);

        //Facebook Share
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
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
        new Sound().btn_play(this);//sound when click button
        Intent activityIntent = new Intent(this, GameActivity.class);
        startActivity(activityIntent);
    }

    public void showMain(View clickedButton){
        new Sound().Other(this);//sound when click button
        finish();
    }


    private void shareGame(){
        new Sound().Other(this);//sound when click button

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Chia sẻ cho bạn bè cùng chơi nào !!")
                    .setContentDescription(
                            "Bạn đã đạt được "+ Global_Variable.LEVEL + " điểm. Đỉnh cao của sự hư cấu rồi!")
                    .setContentUrl(Uri.parse("https://www.facebook.com/pages/M%E1%BB%99t-c%E1%BB%99ng-m%E1%BB%99t-b%E1%BA%B1ng-ba/829973387050647?skip_nax_wizard=true&ref_type=logout_gear"))
                    .build();

            try {
                shareDialog.show(linkContent);
            }
            catch (Exception e )
            {System.out.println(e.getMessage());}
  //
   }

    }
}
