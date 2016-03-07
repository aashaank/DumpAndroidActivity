package com.example.aashankpratap.androidactivitylifecycle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.aashankpratap.androidactivitylifecycle.BoundedService.MyBinder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button button, buttonPopUp,
            buttonUnBoundServiceStart, buttonUnBoundedServiceStop,
            buttonBoundedServiceStart, buttonBoundedServiceStop,
            buttonBroadcast;
    private EditText edText ;
    private PopupWindow popUp ;

    BoundedService mBoundedService ;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private File file ;
    boolean mServiceBound = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this,"onCreate My Application is getting created", Toast.LENGTH_SHORT).show();
        button = (Button) findViewById(R.id.button1);
        buttonPopUp = (Button) findViewById(R.id.button2);
        buttonUnBoundServiceStart = (Button) findViewById(R.id.button3);
        buttonUnBoundedServiceStop = (Button) findViewById(R.id.button4);
        buttonBoundedServiceStart = (Button) findViewById(R.id.button5);
        buttonBoundedServiceStop = (Button) findViewById(R.id.button6);
        buttonBroadcast = (Button) findViewById(R.id.button7);
        edText = (EditText) findViewById(R.id.editText);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(MainActivity.this,"onStart Activity has appeared on screen",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this,"onResume User is interacting with the activity",Toast.LENGTH_SHORT).show();
        button.setOnClickListener(this);
        buttonPopUp.setOnClickListener(this);
        buttonUnBoundServiceStart.setOnClickListener(this);
        buttonUnBoundedServiceStop.setOnClickListener(this);
        buttonBoundedServiceStart.setOnClickListener(this);
        buttonBoundedServiceStop.setOnClickListener(this);
        buttonBroadcast.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(MainActivity.this,"onPause Activity in not on front of screen",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(MainActivity.this,"onStop Activity is no longer visible",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(MainActivity.this,"onDestroy Activity is killed by user or system",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(MainActivity.this,"onRestart User navigates back to the activity",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1 : {
                AlertDialog.Builder dg = new AlertDialog.Builder(this);
                dg.setTitle("Launching Camera");
                dg.setMessage("When click on positive button camera will be launched");
                dg.setPositiveButton("Launch Camera",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        file = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,file);
                        startActivityForResult(cameraIntent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                    }
                });
                dg.setNegativeButton("Cancel",null);
                dg.create().show();
                break;
            }
            case R.id.button2 : {
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popup_window , (ViewGroup) findViewById(R.id.popupWindow));
                popUp = new PopupWindow(layout,1000,1700,true);
                popUp.showAtLocation(layout, Gravity.CENTER, 0, 0);
                Button buttonClose = (Button) layout.findViewById(R.id.popupButton);
                Button buttonSecondActivity = (Button) layout.findViewById(R.id.popupCameraUI);
                buttonClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUp.dismiss();
                    }
                });
                buttonSecondActivity.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,CameraUISelfie.class);
                        popUp.dismiss();
                        startActivity(intent);
                    }
                });
                break;
            }
            case R.id.button3 : {
                startService(new Intent(MainActivity.this,UnBoundService.class));
                break;
            }
            case R.id.button4 : {
                stopService(new Intent(MainActivity.this, UnBoundService.class));
                break;
            }
            case R.id.button5 : {
                Intent intent = new Intent(MainActivity.this,BoundedService.class);
                startService(intent);
                bindService(intent,mServiceConnection,Context.BIND_AUTO_CREATE);
                break;
            }
            case R.id.button6 : {
                if(mServiceBound) {
                    unbindService(mServiceConnection);
                    mServiceBound = false;
                }
                Intent intent = new Intent(MainActivity.this, BoundedService.class);
                stopService(intent);
                break;
            }
            case R.id.button7 : {
                Intent intent = new Intent();
                String text = edText.getText().toString();
                intent.putExtra("message",text);
                intent.setAction("com.example.aashaankpratap.androidactivitylifecycle.CUSTOM_INTENT");
                sendBroadcast(intent);
                break;
            }
            default: break;
        }
    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
            Toast.makeText(MainActivity.this,"Service is disconnected",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBinder myBinder = (MyBinder) service;
            mBoundedService = myBinder.getService();
            Toast.makeText(MainActivity.this,"Service is connected",Toast.LENGTH_SHORT).show();
            mServiceBound = true;
        }
    };

    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdir()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile ;
        if(type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath()+File.separator+"IMG_"+timeStamp+".jpg");
        }
        else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath()+File.separator+"VID_"+timeStamp+".mp4");
        }
        else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this,"IMAGE saved to: \n"+data.getData(),Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == RESULT_CANCELED) {
                //User cancelled the image capture
            }
            else {
                //Image capture failed,advise user
            }
        }

        if(requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this,"Vedio saved to: \n"+data.getData(),Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == RESULT_CANCELED) {
                //User cancelled the vedio capture
            }
            else {
                //Vedio capture failed, advise user.
            }
        }
    }
}
