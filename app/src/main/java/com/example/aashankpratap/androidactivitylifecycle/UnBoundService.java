package com.example.aashankpratap.androidactivitylifecycle;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by AASHANK PRATAP on 3/5/2016.
 */
public class UnBoundService extends Service {

    //private int loop ;
    MediaPlayer mp ;

    @Override
    public void onCreate() {
        super.onCreate();
        //loop = 20;
        mp = MediaPlayer.create(getApplicationContext(),R.raw.main_tenu_samjhava_ki);
        Toast.makeText(UnBoundService.this,"onCreate UnboundedService",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Toast.makeText(UnBoundService.this,"onStart UnboundedService",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(UnBoundService.this,"onStartCommand UnboundedService",Toast.LENGTH_SHORT).show();
        mp.start();
        /*while(loop!=0) {
            Toast.makeText(UnBoundService.this,"onStartCommand Unbounded Service Loop : "+loop,Toast.LENGTH_SHORT).show();
            loop--;
        }*/
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(UnBoundService.this,"onBind UnboundedService",Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(UnBoundService.this,"onUnbind UnboundedService",Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Toast.makeText(UnBoundService.this,"onRebind UnboundedService",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //loop = 0 ;
        Toast.makeText(UnBoundService.this,"onDestroy UnboundedService",Toast.LENGTH_SHORT).show();
        mp.release();
    }
}
