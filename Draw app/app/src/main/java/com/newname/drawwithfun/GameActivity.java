package com.newname.drawwithfun;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidanimations.library.bouncing_entrances.BounceInDownAnimator;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

import static com.daimajia.androidanimations.library.Techniques.Bounce;
import static com.daimajia.androidanimations.library.Techniques.BounceIn;
import static com.daimajia.androidanimations.library.Techniques.BounceInDown;
import static com.daimajia.androidanimations.library.Techniques.BounceInLeft;
import static com.daimajia.androidanimations.library.Techniques.BounceInUp;
import static com.daimajia.androidanimations.library.Techniques.DropOut;
import static com.daimajia.androidanimations.library.Techniques.FadeInDown;
import static com.daimajia.androidanimations.library.Techniques.FadeOutUp;
import static com.daimajia.androidanimations.library.Techniques.Flash;
import static com.daimajia.androidanimations.library.Techniques.Pulse;
import static com.daimajia.androidanimations.library.Techniques.RotateIn;
import static com.daimajia.androidanimations.library.Techniques.RubberBand;
import static com.daimajia.androidanimations.library.Techniques.SlideInUp;
import static com.daimajia.androidanimations.library.Techniques.SlideOutDown;
import static com.daimajia.androidanimations.library.Techniques.SlideOutUp;
import static com.daimajia.androidanimations.library.Techniques.Tada;
import static com.daimajia.androidanimations.library.Techniques.TakingOff;
import static com.daimajia.androidanimations.library.Techniques.ZoomInDown;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton image1,image2,image3,image4,image5,image6,image7,image8,result;
    HashMap<Integer,Integer> resultArray = new HashMap<Integer, Integer>();
    HashMap<Integer,Integer> demoArray = new HashMap<Integer, Integer>();
    ArrayList<Integer> check = new ArrayList<>();
    GifImageView excellent;
    ImageView close,replay;
    int ran1,ran2,ran3,ran4,ran5,ran6,ran7,ran8;
    private static int resRan;

    MediaPlayer ring,red,blue,yellow,brown,green,pink,purple,orange,ring2;


    LinearLayout gameLayout;
    pl.droidsonroids.gif.GifImageView sparkle1,sparkle2,sparkle3,sparkle4,sparkle5,sparkle6,sparkle7,sparkle8;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Log.d("Activity","GameActivity");

        {

            image1 = findViewById(R.id.image1);
            image2 = findViewById(R.id.image2);
            image3 = findViewById(R.id.image3);
            image4 = findViewById(R.id.image4);
            image5 = findViewById(R.id.image5);
            image6 = findViewById(R.id.image6);
            image7 = findViewById(R.id.image7);
            image8 = findViewById(R.id.image8);
            result = findViewById(R.id.result);
            {
                sparkle1 = findViewById(R.id.sparkle1);
                sparkle1.setVisibility(View.INVISIBLE);
                sparkle2 = findViewById(R.id.sparkle2);
                sparkle2.setVisibility(View.INVISIBLE);
                sparkle3 = findViewById(R.id.sparkle3);
                sparkle3.setVisibility(View.INVISIBLE);
                sparkle4 = findViewById(R.id.sparkle4);
                sparkle4.setVisibility(View.INVISIBLE);
                sparkle5 = findViewById(R.id.sparkle5);
                sparkle5.setVisibility(View.INVISIBLE);
                sparkle6 = findViewById(R.id.sparkle6);
                sparkle6.setVisibility(View.INVISIBLE);
                sparkle7 = findViewById(R.id.sparkle7);
                sparkle7.setVisibility(View.INVISIBLE);
                sparkle8 = findViewById(R.id.sparkle8);
                sparkle8.setVisibility(View.INVISIBLE);
            }

            ring= MediaPlayer.create(GameActivity.this,R.raw.kids_cheering);
            ring2= MediaPlayer.create(GameActivity.this,R.raw.red);


            gameLayout = (LinearLayout) findViewById(R.id.gameLayout);
            close = findViewById(R.id.close);
            PushDownAnim.setPushDownAnimTo(close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainIntent= new Intent(GameActivity.this,HomeActivity.class);
                    startActivity(mainIntent);
                    overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                    finish();
                }
            });
//            final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
//
//            // Use bounce interpolator with amplitude 0.2 and frequency 20
//            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
//            myAnim.setInterpolator(interpolator);
//
//            gameLayout.startAnimation(myAnim);
            gameLayout.setVisibility(View.INVISIBLE);
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    gameLayout.setVisibility(View.VISIBLE);
                    YoYo.with(DropOut)
                            .duration(1200)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(gameLayout);
                }
            }, 200); // milliseconds: 1 seg.


            excellent = findViewById(R.id.excellent);
            replay = findViewById(R.id.replay);
            PushDownAnim.setPushDownAnimTo(replay);
            excellent.setVisibility(View.INVISIBLE);
            replay.setVisibility(View.INVISIBLE);


            image1.setOnClickListener(this);
            image2.setOnClickListener(this);
            image3.setOnClickListener(this);
            image4.setOnClickListener(this);
            image5.setOnClickListener(this);
            image6.setOnClickListener(this);
            image7.setOnClickListener(this);
            image8.setOnClickListener(this);
        }

        //set Demo Image
        demoArray.put(1,R.drawable.game_left_kid_red);
        demoArray.put(2,R.drawable.game_left_kid_blue);
        demoArray.put(3,R.drawable.game_left_kid_yellow);
        demoArray.put(4,R.drawable.game_left_kid_green);
        demoArray.put(5,R.drawable.game_left_kid_pink);
        demoArray.put(6,R.drawable.game_left_kid_brown);
        demoArray.put(7,R.drawable.game_left_kid_purple);
        demoArray.put(8,R.drawable.game_left_kid_orange);

        ArrayList<Integer> getRandomNumber = generateRandomNumbers();
        ran1= getRandomNumber.get(0);
        ran2= getRandomNumber.get(1);
        ran3= getRandomNumber.get(2);
        ran4= getRandomNumber.get(3);
        ran5= getRandomNumber.get(4);
        ran6= getRandomNumber.get(5);
        ran7= getRandomNumber.get(6);
        ran8= getRandomNumber.get(7);
        image1.setImageResource(demoArray.get(ran1));
        image2.setImageResource(demoArray.get(ran2));
        image3.setImageResource(demoArray.get(ran3));
        image4.setImageResource(demoArray.get(ran4));
        image5.setImageResource(demoArray.get(ran5));
        image6.setImageResource(demoArray.get(ran6));
        image7.setImageResource(demoArray.get(ran7));
        image8.setImageResource(demoArray.get(ran8));

        Log.d("DemoArrayImage1", String.valueOf(" "+getRandomNumber.get(0)+" "+getRandomNumber.get(1)
                +" "+getRandomNumber.get(2)+" "+getRandomNumber.get(3)+" "+getRandomNumber.get(4)+" "+getRandomNumber.get(5)
                +" "+getRandomNumber.get(6)+" "+getRandomNumber.get(7)));

        //set Result Image
            resultArray.put(1,R.drawable.game_right_kid_red);
            resultArray.put(2,R.drawable.game_right_kid_blue);
            resultArray.put(3,R.drawable.game_right_kid_yellow);
            resultArray.put(4,R.drawable.game_right_kid_green);
            resultArray.put(5,R.drawable.game_right_kid_pink);
            resultArray.put(6,R.drawable.game_right_kid_brown);
            resultArray.put(7,R.drawable.game_right_kid_purple);
            resultArray.put(8,R.drawable.game_right_kid_orange);

            resRan = generateRandomNumberForResult();
            result.setImageResource(resultArray.get(resRan));

            // Toast.makeText(getApplicationContext(),resultArray.get(ran)+ ran, Toast.LENGTH_LONG).show();
            Log.d("ResultArray", String.valueOf(" "+resRan));

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excellent.setVisibility(View.INVISIBLE);
                replay.setVisibility(View.INVISIBLE);
                check.clear();
                gameLayout.setVisibility(View.VISIBLE);
                setResultArray();
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showItems();
                    }
                }, 1000); // milliseconds: 1 seg.


            }
        });
    }
    public void showItems(){
//        YoYo.with(DropOut)
//                .duration(1200)
//                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
//                .interpolate(new AccelerateDecelerateInterpolator())
//                .playOn(gameLayout);
        image1.setVisibility(View.VISIBLE);
        image2.setVisibility(View.VISIBLE);
        image3.setVisibility(View.VISIBLE);
        image4.setVisibility(View.VISIBLE);
        image5.setVisibility(View.VISIBLE);
        image6.setVisibility(View.VISIBLE);
        image7.setVisibility(View.VISIBLE);
        image8.setVisibility(View.VISIBLE);

        YoYo.with(DropOut)
                .duration(1200)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(image1);
        YoYo.with(DropOut)
                .duration(1200)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(image2);
        YoYo.with(DropOut)
                .duration(1200)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(image3);
        YoYo.with(DropOut)
                .duration(1200)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(image4);
        YoYo.with(DropOut)
                .duration(1200)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(image5);
        YoYo.with(DropOut)
                .duration(1200)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(image6);
        YoYo.with(DropOut)
                .duration(1200)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(image7);
        YoYo.with(DropOut)
                .duration(1200)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(image8);
        YoYo.with(DropOut)
                .duration(1200)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(result);
    }

    public Integer generateRandomNumberForResult(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        int element = 0;

            for (int i=1; i<9; i++) {
                list.add(new Integer(i));
            }
            Collections.shuffle(list);
            Boolean flag= false;
            element = 0;
            while(flag==false){
                if(check.contains(list.get(element))){
                    element++;
                }else {
                    check.add(list.get(element));
                    Log.d("CheckArraySize","Size: "+check.size());
                    flag = true;
                }
            }



        return list.get(element);

    }

    public ArrayList<Integer> generateRandomNumbers(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<9; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        return list;
    }

    public void setResultArray(){

        if(check.size()==8){
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    gameLayout.setVisibility(View.INVISIBLE);
                    excellent.setVisibility(View.VISIBLE);
                    ring.release();
                    ring2.release();
                    ring= MediaPlayer.create(GameActivity.this,R.raw.kids_cheering);
                    ring.start();
                }
            }, 500); // milliseconds: 1 seg.

            YoYo.with(DropOut)
                    .duration(1200)
                    .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                    .interpolate(new AccelerateDecelerateInterpolator())
                    .playOn(excellent);
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    replay.setVisibility(View.VISIBLE);
                    YoYo.with(RotateIn)
                            .duration(1200)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(replay);
                }
            }, 1000); // milliseconds: 1 seg.
            YoYo.with(Pulse)
                    .duration(1200)
                    .repeat(YoYo.INFINITE)
                    .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                    .interpolate(new AccelerateDecelerateInterpolator())
                    .playOn(replay);


        }else {
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("setResultArray", "enter");
                    resRan = generateRandomNumberForResult();
                    result.setImageResource(resultArray.get(resRan));
                    result.setVisibility(View.VISIBLE);
                    YoYo.with(FadeInDown)
                            .duration(1000)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(result);
                }
            }, 1000); // milliseconds: 1 seg.
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image1:
                if(resRan==ran1){
                    PushDownAnim.setPushDownAnimTo(image1);
                    playColorName(resRan);
                    sparkle1.setVisibility(View.VISIBLE);
                    image1.setVisibility(View.INVISIBLE);
                    YoYo.with( SlideOutUp)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(result);
                    setResultArray();
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sparkle1.setVisibility(View.INVISIBLE);
                        }
                    }, 200); // milliseconds: 1 seg.
                }else{
                    YoYo.with(Bounce)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(image1);

                }
                break;
            case R.id.image2:
                if(resRan==ran2){
                    PushDownAnim.setPushDownAnimTo(image2);
                    playColorName(resRan);
                    sparkle2.setVisibility(View.VISIBLE);
                    image2.setVisibility(View.INVISIBLE);
                    YoYo.with( SlideOutUp)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(result);
                    setResultArray();
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sparkle2.setVisibility(View.INVISIBLE);

                        }
                    }, 200); // milliseconds: 1 seg.
                }else{
                    YoYo.with(Bounce)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(image2);
                }
                break;
            case R.id.image3:
                if(resRan==ran3){
                    PushDownAnim.setPushDownAnimTo(image3);
                    playColorName(resRan);
                    sparkle3.setVisibility(View.VISIBLE);
                    image3.setVisibility(View.INVISIBLE);
                    YoYo.with( SlideOutUp)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(result);
                    setResultArray();
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sparkle3.setVisibility(View.INVISIBLE);

                        }
                    }, 200); // milliseconds: 1 seg.
                }else{
                    YoYo.with(Bounce)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(image3);
                }
                break;
            case R.id.image4:
                if(resRan==ran4){
                    PushDownAnim.setPushDownAnimTo(image4);
                    playColorName(resRan);
                    sparkle4.setVisibility(View.VISIBLE);
                    image4.setVisibility(View.INVISIBLE);
                    YoYo.with( SlideOutUp)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(result);
                    setResultArray();
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sparkle4.setVisibility(View.INVISIBLE);

                        }
                    }, 200); // milliseconds: 1 seg.
                }else{
                    YoYo.with(Bounce)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(image4);
                }
                break;
            case R.id.image5:
                if(resRan==ran5){
                    PushDownAnim.setPushDownAnimTo(image5);
                    playColorName(resRan);
                    sparkle5.setVisibility(View.VISIBLE);
                    image5.setVisibility(View.INVISIBLE);
                    YoYo.with( SlideOutUp)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(result);
                    setResultArray();
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sparkle5.setVisibility(View.INVISIBLE);

                        }
                    }, 200); // milliseconds: 1 seg.
                }else{
                    YoYo.with(Bounce)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(image5);
                }
                break;
            case R.id.image6:
                if(resRan==ran6){
                    PushDownAnim.setPushDownAnimTo(image6);
                    playColorName(resRan);
                    sparkle6.setVisibility(View.VISIBLE);
                    image6.setVisibility(View.INVISIBLE);
                    YoYo.with( SlideOutUp)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(result);
                    setResultArray();
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sparkle6.setVisibility(View.INVISIBLE);

                        }
                    }, 200); // milliseconds: 1 seg.
                }else{
                    YoYo.with(Bounce)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(image6);
                }
                break;
            case R.id.image7:
                if(resRan==ran7){
                    PushDownAnim.setPushDownAnimTo(image7);
                    playColorName(resRan);
                    sparkle7.setVisibility(View.VISIBLE);
                    image7.setVisibility(View.INVISIBLE);
                    YoYo.with( SlideOutUp)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(result);
                    setResultArray();
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sparkle7.setVisibility(View.INVISIBLE);

                        }
                    }, 200); // milliseconds: 1 seg.
                }else{
                    YoYo.with(Bounce)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(image7);
                }
                break;
            case R.id.image8:
                if(resRan==ran8){
                    PushDownAnim.setPushDownAnimTo(image8);
                    playColorName(resRan);
                    sparkle8.setVisibility(View.VISIBLE);
                    image8.setVisibility(View.INVISIBLE);
                    YoYo.with( SlideOutUp)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(result);
                    setResultArray();
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sparkle8.setVisibility(View.INVISIBLE);

                        }
                    }, 200); // milliseconds: 1 seg.
                }else{
                    YoYo.with(Bounce)
                            .duration(700)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(image8);
                }
                break;
        }

    }


    public void playColorName(int res){
        if(res == 1){
            ring.release();
            ring2.release();
            ring2= MediaPlayer.create(GameActivity.this,R.raw.red);
            ring2.start();
            Log.d("Tag","res= "+res);

        } else if(res == 2){
            ring.release();
            ring2.release();
            ring2= MediaPlayer.create(GameActivity.this,R.raw.blue);
            ring2.start();
            Log.d("Tag","res= "+res);

        } else if(res == 3){
            ring.release();
            ring2.release();
            ring2= MediaPlayer.create(GameActivity.this,R.raw.yellow);
            ring2.start();
            Log.d("Tag","res= "+res);

        } else if(res == 4){
            ring.release();
            ring2.release();
            ring2= MediaPlayer.create(GameActivity.this,R.raw.green);
            ring2.start();
            Log.d("Tag","res= "+res);

        } else if(res == 5){
            ring.release();
            ring2.release();
            ring2= MediaPlayer.create(GameActivity.this,R.raw.pink);
            ring2.start();
            Log.d("Tag","res= "+res);

        } else if(res == 6){
            ring.release();
            ring2.release();
            ring2= MediaPlayer.create(GameActivity.this,R.raw.brown);
            ring2.start();
            Log.d("Tag","res= "+res);

        } else if(res == 7){
            ring.release();
            ring2.release();
            ring2= MediaPlayer.create(GameActivity.this,R.raw.purple);
            ring2.start();
            Log.d("Tag","res= "+res);

        } else if(res == 8){
            ring.release();
            ring2.release();
            ring2= MediaPlayer.create(GameActivity.this,R.raw.orange);
            ring2.start();
            Log.d("Tag","res= "+res);

        }
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }


}