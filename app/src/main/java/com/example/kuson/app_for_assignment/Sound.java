package com.example.kuson.app_for_assignment;

/**
 * Created by 51100_000 on 10/04/2015.
 */
import android.app.Activity;
import android.media.MediaPlayer;
 public class Sound {

     public void btn_false(Activity _context){
         final MediaPlayer mp = MediaPlayer.create(_context, R.raw.button_false);
         mp.start();
     }
     public void btn_true(Activity _context){
         final MediaPlayer mp = MediaPlayer.create(_context, R.raw.button_true);
         mp.start();
     }
     public void btn_play(Activity _context){
         final MediaPlayer mp = MediaPlayer.create(_context, R.raw.button_play);
         mp.start();
     }
     public void Congrat(Activity _context){
         final MediaPlayer mp = MediaPlayer.create(_context, R.raw.congratulations_sound);
         mp.start();
     }
     public void Failure(Activity _context){
         final MediaPlayer mp = MediaPlayer.create(_context, R.raw.failed_sound);
         mp.start();
     }
     public void Other(Activity _context){
         final MediaPlayer mp = MediaPlayer.create(_context, R.raw.other_button);
         mp.start();
     }
}
