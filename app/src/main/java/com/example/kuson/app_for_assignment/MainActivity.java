package com.example.kuson.app_for_assignment;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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


public class MainActivity extends ActionBarActivity {


        private String readfromFile(String filename) {
            String s="0";
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



    private class Readdecfile implements View.OnClickListener{
        private String readfromFile(String filename) {
            String s="";
            try {
                FileInputStream fileIn=openFileInput("config.conf");
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
                byte[] string_decode = Base64.decode("/EtojLtSXj6Zyz4rVbBngM3vlJSIp9MA", 1);
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
        public String read (){
            String str = readfromFile("config.txt");
            byte[] ciphertext = Base64.decode(str, 1);
            String plaintext = decrype(ciphertext,"face_key");
            System.out.println(plaintext);
            return plaintext;
        }
        @Override
        public void onClick(View arg0){
            String msg = read();
            System.out.println("read successfully");


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Global_Variable.HIGH_SCORE= Integer.parseInt(readfromFile(Global_Variable.HIGH_SCORE_FILE_NAME));
        TextView highscore = (TextView) findViewById(R.id.hiScore_text);
        highscore.setText("Điểm cao nhất là "+ Global_Variable.HIGH_SCORE);
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
    }

    public void showSetting(View clickedButton){
        Intent activityIntent = new Intent(this, SettingActivity.class);
        startActivity(activityIntent);
    }
}
