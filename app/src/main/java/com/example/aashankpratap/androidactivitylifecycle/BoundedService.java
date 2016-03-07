package com.example.aashankpratap.androidactivitylifecycle;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by AASHANK PRATAP on 3/5/2016.
 */
public class BoundedService extends Service {

    IBinder iBinder ;
    private MediaPlayer mp;
    private IBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        mp = MediaPlayer.create(getApplicationContext(),R.raw.jab_tak_hai_jaan);
        Toast.makeText(BoundedService.this,"onCreate BoundedService",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(BoundedService.this,"onStart BoundedService",Toast.LENGTH_SHORT).show();
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(BoundedService.this,"onStartCommand BoundedService",Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(BoundedService.this,"onBind BoundedService",Toast.LENGTH_SHORT).show();
        mp.start();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(BoundedService.this,"onUnbind BoundedService",Toast.LENGTH_SHORT).show();
        mp.release();
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Toast.makeText(BoundedService.this,"onRebind BoundedService",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(BoundedService.this,"onDestroy BoundedService",Toast.LENGTH_SHORT).show();
    }

    public class MyBinder extends Binder {
        BoundedService getService() {
            return BoundedService.this;
        }
    }
}
