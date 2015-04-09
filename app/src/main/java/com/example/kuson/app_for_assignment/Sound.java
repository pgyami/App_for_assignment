package com.example.kuson.app_for_assignment;

/**
 * Created by 51100_000 on 10/04/2015.
 */
import android.app.Activity;
import android.media.MediaPlayer;
 public class Sound {

     public void click_sound(Activity _context){
         final MediaPlayer mp = MediaPlayer.create(_context, R.raw.click_sound);
         mp.start();
     }

}
