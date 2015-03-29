package com.example.kuson.app_for_assignment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class SettingActivity extends ActionBarActivity {

        private void writeToFile(String data) {
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(Global_Variable.CONFIG_FILE_NAME, Context.MODE_PRIVATE));
                outputStreamWriter.write(data);
                outputStreamWriter.close();
            }
            catch (IOException e) {
                System.out.println("Exception: File write failed: " + e.toString());
            }
        }

        public void write (String msg){
            byte[] ciphertext= encrypt(msg,"/EtojLtSXj6Zyz4rVbBngM3vlJSIp9MA");
            String enc = Base64.encodeToString(ciphertext, 1);
            writeToFile(enc);
        }
        public byte[] encrypt(String data, String key){
            byte[] cipherText = new byte[0];
            try {

                byte[] string_decode;//= new byte[100];
                string_decode= Base64.decode(key, 0);
                SecretKey secretKey = new SecretKeySpec(string_decode, 0, string_decode.length, "AES");
                Cipher cipherengine = Cipher.getInstance("AES");
                cipherengine.init(Cipher.ENCRYPT_MODE, secretKey);
                cipherText = cipherengine.doFinal(data.getBytes());
            }

            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
            return cipherText;
        }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
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

    public void showMain(View clickedButton){
        Intent activityIntent = new Intent(this, MainActivity.class);
        //Edit here when you create new variable in GLOBAL_VARIABLE class
        write(Global_Variable.CONFIG_FILE_NAME+","+Global_Variable.HIGH_SCORE_FILE_NAME+","+Global_Variable.HIGH_SCORE+","+Global_Variable.TOTAL_TIME+","+Global_Variable.EXTRA_TIME+","+Global_Variable.DECREASE_TIME+","+Global_Variable.DIFFICULTY);
        startActivity(activityIntent);
    }
}
