package com.example.kuson.app_for_assignment;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

/**
 * Created by 51100_000 on 09/04/2015.
 */
public class BackgroundMusic extends Service {
    MediaPlayer mp;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    public void onCreate() {
        mp = MediaPlayer.create(this,R.raw.bg_music);
        mp.setLooping(false);
    }

    public void onDestroy() {
        mp.stop();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        mp.start();
       /* Log.d(LOGCAT, "Media Player started!");
        if(objPlayer.isLooping() != true){
            Log.d(LOGCAT, "Problem in Playing Audio");
        }*/
    return 1;
    }
}
