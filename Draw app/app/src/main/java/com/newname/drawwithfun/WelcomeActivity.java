package com.newname.drawwithfun;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.YoYo;

import static com.daimajia.androidanimations.library.Techniques.DropOut;
import static com.daimajia.androidanimations.library.Techniques.Pulse;
import static com.daimajia.androidanimations.library.Techniques.SlideInLeft;
import static com.daimajia.androidanimations.library.Techniques.SlideInRight;
import static com.daimajia.androidanimations.library.Techniques.Tada;
import static com.daimajia.androidanimations.library.Techniques.ZoomInDown;

public class WelcomeActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 4000;
    ImageView splashText;
    com.newname.drawwithfun.TypeWriter displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        Log.d("Activity","WelcomeActivity");

        splashText = findViewById(R.id.splashText);
        displayText = findViewById(R.id.displayText);



        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //balloon.setVisibility(View.VISIBLE);
                splashText.setVisibility(View.VISIBLE);
                YoYo.with(ZoomInDown)
                        .duration(1000)
                        .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                        .interpolate(new AccelerateDecelerateInterpolator())
                        .playOn(splashText);
            }
        }, 100); // milliseconds: 1 seg.

//        new android.os.Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 500); // milliseconds: 1 seg.

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //balloon.setVisibility(View.VISIBLE);
                displayText.setCharacterDelay(10);
                displayText.animateText("www.womenindigital.net");
                YoYo.with(Pulse)
                        .duration(1500)
                        .repeat(YoYo.INFINITE)
                        .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                        .interpolate(new AccelerateDecelerateInterpolator())
                        .playOn(splashText);
            }
        }, 1100); // milliseconds: 1 seg.


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                WelcomeActivity.this.startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}

