package com.newname.drawwithfun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.daimajia.androidanimations.library.YoYo;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.File;
import java.io.IOException;

import static com.daimajia.androidanimations.library.Techniques.Bounce;
import static com.daimajia.androidanimations.library.Techniques.BounceInLeft;
import static com.daimajia.androidanimations.library.Techniques.BounceInRight;
import static com.daimajia.androidanimations.library.Techniques.DropOut;
import static com.daimajia.androidanimations.library.Techniques.Landing;
import static com.daimajia.androidanimations.library.Techniques.Pulse;
import static com.daimajia.androidanimations.library.Techniques.Shake;
import static com.daimajia.androidanimations.library.Techniques.SlideInDown;
import static com.daimajia.androidanimations.library.Techniques.SlideInLeft;
import static com.daimajia.androidanimations.library.Techniques.SlideInUp;
import static com.daimajia.androidanimations.library.Techniques.Swing;
import static com.daimajia.androidanimations.library.Techniques.Tada;
import static com.daimajia.androidanimations.library.Techniques.Wave;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView learnButton,freeHandButton,albumButton,game,logo,balloon,kid;
    ImageView musicButton;
    LinearLayout board;
    String SCAN_PATH;
    File[] allFiles ;
    boolean infinite = true;
    MediaPlayer ring;
    LinearLayout firstGridLeft,secondGridRight,lastGridLeft;
    public final String[] EXTERNAL_PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    public final int EXTERNAL_REQUEST = 138;
    public static int  button01pos = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d("Activity","HomeActivity");
        firstGridLeft = findViewById(R.id.firstGridLeft);
        secondGridRight = findViewById(R.id.secondGridRight);
        lastGridLeft = findViewById(R.id.lastGridLeft);

        learnButton =findViewById(R.id.learnButton);
        freeHandButton=findViewById(R.id.freeHandButton);
        albumButton=findViewById(R.id.albumButton);
        game = findViewById(R.id.game);
        musicButton = findViewById(R.id.musicButton);
        logo = findViewById(R.id.logo);
        balloon = findViewById(R.id.balloon);
        kid = findViewById(R.id.kid);
        board = findViewById(R.id.board);

        balloon.setVisibility(View.INVISIBLE);
        kid.setVisibility(View.INVISIBLE);
        board.setVisibility(View.INVISIBLE);
        //musicButton.setChecked(true);

//        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
//
//        // Use bounce interpolator with amplitude 0.2 and frequency 20
//        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
//        myAnim.setInterpolator(interpolator);
//
//        learnButton.startAnimation(myAnim);
//        freeHandButton.startAnimation(myAnim);
//        albumButton.startAnimation(myAnim);
//        game.startAnimation(myAnim);
        ring= MediaPlayer.create(HomeActivity.this,R.raw.thememusic);
        ring.start();
        musicButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (button01pos == 0) {
                    musicButton.setImageResource(R.drawable.music_button);
                    ring.release();
                    ring= MediaPlayer.create(HomeActivity.this,R.raw.thememusic);
                    ring.start();
                    button01pos = 1;
                } else if (button01pos == 1) {
                    musicButton.setImageResource(R.drawable.music_off_button);
                    ring.stop();
                    button01pos = 0;
                }
            }
        });
        logo.setVisibility(View.VISIBLE);
        YoYo.with(Landing)
                .duration(1200)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(logo);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //balloon.setVisibility(View.VISIBLE);
                board.setVisibility(View.VISIBLE);
                kid.setVisibility(View.VISIBLE);
                YoYo.with(SlideInLeft)
                        .duration(1200)
                        .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                        .interpolate(new AccelerateDecelerateInterpolator())
                        .playOn(kid);
                YoYo.with(DropOut)
                        .duration(1200)
                        .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                        .interpolate(new AccelerateDecelerateInterpolator())
                        .playOn(board);


            }
        }, 100); // milliseconds: 1 seg.

        YoYo.with(Pulse)
                .duration(1500)
                .repeat(YoYo.INFINITE)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(musicButton);
        YoYo.with(Pulse)
                .duration(1500)
                .repeat(YoYo.INFINITE)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(logo);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                balloon.setVisibility(View.VISIBLE);
                YoYo.with(SlideInDown)
                        .duration(1500)
                        .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                        .interpolate(new AccelerateDecelerateInterpolator())
                        .playOn(balloon);
            }
        }, 800); // milliseconds: 1 seg.

        YoYo.with(Bounce)
                .duration(2000)
                .repeat(YoYo.INFINITE)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(balloon);
        PushDownAnim.setPushDownAnimTo(game);
        PushDownAnim.setPushDownAnimTo(learnButton);
        PushDownAnim.setPushDownAnimTo(freeHandButton);
        PushDownAnim.setPushDownAnimTo(albumButton);

        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,GameActivity.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

            }
        });

        learnButton.setOnClickListener(this);
        freeHandButton.setOnClickListener(this);
        albumButton.setOnClickListener(this);
        File folder = new File(Environment.getExternalStorageDirectory().getPath()+"/DrawWithFun");
        allFiles = folder.listFiles();

//        File[] files = folder.listFiles();
//        if(files != null) {
//            for(File file : files) {
//                System.out.println(file.getName());
//            }
//        }


    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.learnButton){
            Intent intent=new Intent(HomeActivity.this,ColorBookActivity.class);
            startActivity(intent);
            overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

        }
        else if (v.getId()==R.id.freeHandButton){
            Intent intent=new Intent(HomeActivity.this,MyCanvas.class);
            startActivity(intent);
            overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
        }
        else if(v.getId()==R.id.albumButton){
            Boolean permission = requestForPermission();
            if(permission==true){
                if( allFiles==null || allFiles.length == 0 ){
                    Toast.makeText(getApplicationContext(),"You have to save your drawing first!!",Toast.LENGTH_SHORT).show();
                }else{
                    new SingleMediaScanner(HomeActivity.this, allFiles[allFiles.length-1]);
                }
            }
            overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
        }
    }
    public void openFolder(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DrawWithFun/");
        //String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DrawWithFun";
        intent.setDataAndType(uri, "image/jpeg");
        startActivity(Intent.createChooser(intent, "Open folder"));

    }
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_from_top,R.anim.slide_in_top);
//    }

    public class SingleMediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {

        private MediaScannerConnection mMs;
        private File mFile;

        public SingleMediaScanner(Context context, File f) {
            mFile = f;
            mMs = new MediaScannerConnection(context, this);
            mMs.connect();
        }

        public void onMediaScannerConnected() {
            mMs.scanFile(mFile.getAbsolutePath(), null);
        }

        public void onScanCompleted(String path, Uri uri) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);
            mMs.disconnect();
        }

    }
    public boolean requestForPermission() {

        boolean isPermissionOn = true;
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            if (!canAccessExternalSd()) {
                isPermissionOn = false;
                requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
        }

        return isPermissionOn;
    }

    public boolean canAccessExternalSd() {
        return (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm));

    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ring.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ring.pause();
    }
    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
            if (ring.isPlaying()) {
                ring.stop();
            }
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }



}


