package com.example.aashankpratap.androidactivitylifecycle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by AASHANK PRATAP on 3/5/2016.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Broadcast Received ",Toast.LENGTH_SHORT).show();
        Bundle bundle = intent.getExtras();
        String text = bundle.getString("message");
        Toast.makeText(context,"Intent broadcasted the text : "+text,Toast.LENGTH_SHORT).show();
    }
}
