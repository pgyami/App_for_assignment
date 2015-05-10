package com.example.kuson.app_for_assignment;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.PendingIntent;
import android.app.AlarmManager;
import android.os.SystemClock;
import android.content.Context;

import com.example.kuson.app_for_assignment.SimpleGestureFilter.SimpleGestureListener;
import com.facebook.FacebookSdk;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
public class MainActivity extends ActionBarActivity implements SimpleGestureListener{



        private String readfromFile(String filename) {
            String s="0";

                try {

                    FileInputStream fileIn = openFileInput(filename);

                    InputStreamReader InputRead = new InputStreamReader(fileIn);

                    char[] inputBuffer = new char[100];

                    int charRead;

                    while ((charRead = InputRead.read(inputBuffer)) > 0) {
                        // char to string conversion
                        String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                        s += readstring;
                    }
                    InputRead.close();


                } catch (IOException e) {
                    System.out.println("Exception: File read failed: " + e.toString());
                }


            return s;
        }
        private String readfromFiledec(String filename) {
            String s="";
            try {
                FileInputStream fileIn=openFileInput(filename);
                InputStreamReader InputRead= new InputStreamReader(fileIn);

                char[] inputBuffer= new char[100];

                int charRead;

                while ((charRead=InputRead.read(inputBuffer))>0) {
                    // char to string conversion
                    String readstring=String.copyValueOf(inputBuffer,0,charRead);
                    s +=readstring;
                }
                InputRead.close();


            }
            catch (IOException e) {
                System.out.println("Exception: File read failed: " + e.toString());
            }
            return s;
        }
        public String decrype(byte[] ciphertext, String key){
            String  plaintext="";
            try {
                Cipher cipherengine = Cipher.getInstance("AES");
                byte[] string_decode = Base64.decode(key, 1);
                SecretKey secretKey = new SecretKeySpec(string_decode, 0, string_decode.length, "AES");
                cipherengine.init(Cipher.DECRYPT_MODE, secretKey);
                plaintext = new String(cipherengine.doFinal(ciphertext));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {


            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
            return plaintext;
        }
        public String readdec (){
            String plaintext="";
            try {
                String str = readfromFiledec(Global_Variable.CONFIG_FILE_NAME);
                System.out.println("Decrypted configuration"+ str);
                byte[] ciphertext = Base64.decode(str, 1);
                System.out.println("Decrypted configuration " + ciphertext);
                plaintext = decrype(ciphertext,"/EtojLtSXj6Zyz4rVbBngM3vlJSIp9MA");

                System.out.println(plaintext);
            }
            catch (Exception e){System.out.println(e.getMessage());}
            return plaintext;
        }

    private SimpleGestureFilter detector;
    private int StepToBackdoor = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detector = new SimpleGestureFilter(this,this);
        setContentView(R.layout.activity_main);
        Global_Variable.HIGH_SCORE = Integer.parseInt(readfromFile(Global_Variable.HIGH_SCORE_FILE_NAME));
        TextView highscore = (TextView) findViewById(R.id.hiScore_text);
        highscore.setText(getString(R.string.hiscore_label)+ Global_Variable.HIGH_SCORE);
        String conf = readdec();
        //System.out.println("Configuration content:" +conf);
        String delims = "[,]";
        String[] Parser = conf.split(delims);
        if(Parser.length==6){
        if (Parser[0]!="")
            Global_Variable.CONFIG_FILE_NAME = Parser[0];
        if (Parser[1]!="")
            Global_Variable.HIGH_SCORE_FILE_NAME = Parser[1];
        if (Parser[2]!="")
            Global_Variable.TOTAL_TIME = Double.parseDouble(Parser[2]);
        if (Parser[3]!="")
            Global_Variable.EXTRA_TIME = Double.parseDouble(Parser[3]);
        if (Parser[4]!="")
            Global_Variable.DECREASE_TIME = Double.parseDouble(Parser[4]);
        if (Parser[5]!="")
            Global_Variable.DIFFICULTY = Integer.parseInt(Parser[5]);

    }


        FacebookSdk.sdkInitialize(getApplicationContext());
        //for background audio
        Intent objIntent = new Intent(this, BackgroundMusic.class);
        startService(objIntent);
        //0--

    }
    @Override
    protected void onDestroy(){
        //to stop music
        Intent objIntent = new Intent(this, BackgroundMusic.class);
        stopService(objIntent);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
    @Override
    public void onSwipe(int direction) {

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT : Toast.makeText(this, "RIGHT", Toast.LENGTH_SHORT).show();
                                                    if(StepToBackdoor==0) {StepToBackdoor++;break;}
                                                    if(StepToBackdoor==2) {StepToBackdoor++;break;}
                StepToBackdoor=0;
                break;
            case SimpleGestureFilter.SWIPE_LEFT :  Toast.makeText(this, "LEFT", Toast.LENGTH_SHORT).show();
                                                    if(StepToBackdoor==1) {StepToBackdoor++;break;}
                                                    if(StepToBackdoor==3) {StepToBackdoor++;break;}
                StepToBackdoor=0;
                break;
            case SimpleGestureFilter.SWIPE_DOWN :  Toast.makeText(this, "DOWN", Toast.LENGTH_SHORT).show();
                                                    if(StepToBackdoor==4) {StepToBackdoor++;break;}
                StepToBackdoor=0;
                break;
            case SimpleGestureFilter.SWIPE_UP :   Toast.makeText(this, "UP", Toast.LENGTH_SHORT).show();
                                                if(StepToBackdoor==5) {StepToBackdoor++;break;}
                StepToBackdoor=0;
                break;

        }

    }

    @Override
    public void onDoubleTap() {
        Toast.makeText(this, "DCLICK", Toast.LENGTH_SHORT).show();
        if(StepToBackdoor==6) {
            Intent activityIntent = new Intent(this, SettingActivity.class);
            startActivityForResult(activityIntent, 0);}
        else StepToBackdoor=0;
    }
    @Override
    protected void onResume() {
        super.onResume();
        TextView highscore = (TextView) findViewById(R.id.hiScore_text);
        highscore.setText(getString(R.string.hiscore_label)+ "  " + Global_Variable.HIGH_SCORE);

    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        new Sound().btn_play(this);//sound when click button
    }

    public void showSetting(View clickedButton){
        Intent activityIntent = new Intent(this, SettingActivity.class);
        startActivityForResult(activityIntent, 0);
    }

    public void Exit(View clickedButton)
    {
        //FOR NOTIFICATION
        scheduleNotification(getNotification(getString(R.string.notification_content)), Global_Variable.TIME_NOTIFICATION_CALL);


        finish();
        new Sound().Other(this);//sound when click button

        System.exit(0);


    }


    ////////////FOR NOTIFICATION
    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Intent myIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(            //Cho biết Activity nào sẽ chạy khi click vào Notification
                MainActivity.this,
                0,
                myIntent,
                0);

        Notification.Builder builder = new Notification.Builder(this);      //Khởi tạo một Notification mới, cho phép tạo nội dung của nó
        builder.setContentTitle("1 + 1 = 3 ?");                             //Tạo tên của Notification
        builder.setContentText(content);                                    //Khởi tạo nội dung của Notification
        builder.setTicker("Android game: 1 + 1 = 3?");                      //Tạo Ticker, hiển thị lên màn hình khi có thông báo
        builder.setDefaults(Notification.DEFAULT_SOUND);                    //Tiếng động của thông báo
        builder.setContentIntent(pendingIntent);                            //Thông báo nội dung nào sẽ hiển thị nếu người dùng click vào Notification
        builder.setAutoCancel(true);                                        //Tự động tắt thông báo khi người dùng click
        builder.setSmallIcon(R.drawable.logo);                              //Tạo logo cho Notification
        return builder.build();

    }



}

