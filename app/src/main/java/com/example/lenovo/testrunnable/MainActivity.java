package com.example.lenovo.testrunnable;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    MyHandler myHandler;

    int timePiece=5000;
    MyRunnable myRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRunnable=new MyRunnable();
        HandlerThread handlerThread=new HandlerThread("test");
        handlerThread.start();
        myHandler=new MyHandler(handlerThread.getLooper());
        Message message=myHandler.obtainMessage();
        message.sendToTarget();
        myHandler.post(myRunnable);

       boolean postDelayResult=myHandler.postDelayed(myRunnable,15000);
        Log.v("PLU","------postDelayResult is "+postDelayResult);


    }

    class MyRunnable implements Runnable{

        int num=0;
        @Override
        public void run() {
                num++;
                int eachTime=timePiece/10;
            Log.v("PLU", "=====NUM IS " + num);


            if (num>=10){
                myHandler.removeCallbacks(this);
            }else{
                Message message=myHandler.obtainMessage();
                myHandler.sendMessage(message);
                myHandler.postDelayed(this, eachTime);
            }


        }
    }

    class MyHandler extends Handler {

        public MyHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.v("PLU", "handleMessage thread is " + Thread.currentThread().getName());
           // myHandler.post(myRunnable);
        }
    }


}
