package com.example.vijayshankar.vijayapptimer2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.media.ToneGenerator;
import android.media.AudioManager;

import android.view.MotionEvent;
import android.view.GestureDetector;
import android.support.v4.view.GestureDetectorCompat;

import android.widget.EditText;


////////////////////////////////////////

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener, SetupFragment.SetupFragmentListener
{

    TextView timerTextView;
    long startTime = 0;

    ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);

    private GestureDetectorCompat GestureDetector;
    int BeepCount =0;
    int TapCount = 0;
    int TapBeepCount = 0;
    private TextView ResultMessage;

    private TextView CorrectTapCount;
    private TextView TotalTapCount;
    private Integer MainTouchTimer = 5;
    private Integer MainDuration = 5;


    //runs without a timer by reposting this handler at the end of the runnable
    final Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {

            long millis = System.currentTimeMillis() - startTime;

            int seconds = (int) (millis / 1000)%60;
           // int minutes = seconds / 60;
           // seconds = seconds % 60;

            //timerTextView.setText(String.format("%d:%02d", minutes, seconds));

            Button b = (Button) findViewById(R.id.button);
         /*
            if (seconds>=1) {

            toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 1000); //500 is duration in ms
            toneG.stopTone();
            }*/
            if (seconds<=MainDuration) {
                if (seconds>=1) {
                    toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 1000); //1000 is duration in ms
                    toneG.stopTone();
                    timerTextView.setText(String.valueOf(seconds));
                    BeepCount = seconds;}
                    timerHandler.postDelayed(this, 1000);

            } else {
                timerHandler.removeCallbacks(timerRunnable);
                b.setText("start");
                if (TapBeepCount > 0) {
                    ResultMessage.setTextColor(Color.BLUE);

                    ResultMessage.setText("Great Job !");
                } else {
                    ResultMessage.setTextColor(Color.RED);
                    ResultMessage.setText("Keep Trying !");
                }


            }
        }
    };




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = (TextView) findViewById(R.id.TimerTextView);

        this.GestureDetector = new GestureDetectorCompat(this,this);
        GestureDetector.setOnDoubleTapListener(this);
        CorrectTapCount = (TextView)findViewById(R.id.CorrectTapCount);
        TotalTapCount = (TextView)findViewById(R.id.TotalTapCount);
        ResultMessage = (TextView)findViewById(R.id.ResultMessage);

        Button b = (Button) findViewById(R.id.button);
        b.setText("start");
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("stop")) {
                    timerHandler.removeCallbacks(timerRunnable);
                    b.setText("start");
                    if (TapBeepCount > 0) {
                        ResultMessage.setTextColor(Color.BLUE);

                        ResultMessage.setText("Great Job !");
                    } else {
                        ResultMessage.setTextColor(Color.RED);
                        ResultMessage.setText("Keep Trying !");
                    }
                }
                else {

                    TapCount=0;
                    TapBeepCount=0;
                    CorrectTapCount.setText(String.valueOf(0));
                    TotalTapCount.setText(String.valueOf(0));
                    ResultMessage.setTextColor(Color.BLACK);
                    ResultMessage.setText("Go for it !");
                    b.setText("stop");
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                }
            }
        });
    }

    //This gets called by the setupfragment


    @Override
    public void SetupInfo(Integer TouchTimer, Integer Duration) {
        MainTouchTimer = TouchTimer;
        MainDuration = Duration;
        //ResultMessage.setText("sdfsds");
    }

    /*public void interrupt(){
        try{
            super.interrupt();

        }
    }*/
    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);

        Button b = (Button)findViewById(R.id.button);
        b.setText("start");
    }
///Gesture Events Starts

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        TapCount++;
        TotalTapCount.setText(String.valueOf(TapCount));

        if (BeepCount%MainTouchTimer ==0)  {

            CorrectTapCount.setText(String.valueOf(++TapBeepCount));
            //CorrectTapCount.setText("CORRECT Tap");
        }
        //else {
          //  CorrectTapCount.setText("Wrong Tap"+TapBeepCount);
       // }

        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //CorrectTapCount.setText("onShowPress");

    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
       // CorrectTapCount.setText("onDoubleTap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        //CorrectTapCount.setText("onDoubleTapEvent");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
       // CorrectTapCount.setText("success");
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //CorrectTapCount.setText("onDown");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //CorrectTapCount.setText("onScroll");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
       // CorrectTapCount.setText("onLongPress");

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
       // CorrectTapCount.setText("onFling");
        return true;
    }

    ///Gesture Events Ends


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.GestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}


