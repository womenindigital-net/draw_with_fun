package com.newname.drawwithfun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.daimajia.androidanimations.library.YoYo;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.daimajia.androidanimations.library.Techniques.DropOut;
import static com.daimajia.androidanimations.library.Techniques.ZoomInLeft;
import static com.daimajia.androidanimations.library.Techniques.ZoomInRight;

public class ColorBookItemDrawActivity extends AppCompatActivity implements View.OnClickListener{
    int itemType;
    int itemNo;
    DrawingView drawView;
    ImageButton currPaint,saveButton,newButton,undoButton,redoButton,deleteButton,
            sizeButton,pencilButton,brushButton,backgroundButton,eraseButton,red,
            blue,purple,yellow,green,orange,pink,skyblue,brown,black,white,lightgreen;
    PopupWindow popupWindow;
    LinearLayout boardView,linearLayout1,canvasLayout,leftMenu,rightMenu;
    ImageView back,setItemImage;


    float smallBrush, mediumBrush, largeBrush;

    private static final int STORAGE_CODE = 1000;


    String mFileName;
    private final int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_book_item_draw);

        Log.d("Activity","ColorBookItemDrawActivity");
        Intent itemDrawIntent = getIntent();
        itemType = itemDrawIntent.getExtras().getInt("itemType");
        itemNo = itemDrawIntent.getExtras().getInt("itemNo");
        Log.d("TAG",itemType+"   "+itemNo);

        //initialization
        drawView=findViewById(R.id.drawingView);
        {
            saveButton = findViewById(R.id.saveButtonId);
            newButton = findViewById(R.id.newButtonId);
            undoButton = findViewById(R.id.undoButtonId);
            redoButton = findViewById(R.id.redoButtonId);
            deleteButton = findViewById(R.id.deleteButtonId);
            sizeButton = findViewById(R.id.sizeButtonId);
            pencilButton = findViewById(R.id.pencilButtonId);
            brushButton = findViewById(R.id.brushButtonId);
            backgroundButton = findViewById(R.id.backgroundId);
            eraseButton = findViewById(R.id.eraseButtonId);
            linearLayout1 = (LinearLayout) findViewById(R.id.CanvasViewXML);
            back = findViewById(R.id.colorBack);
            PushDownAnim.setPushDownAnimTo(back);
            canvasLayout = findViewById(R.id.canvasLayout);
            leftMenu = findViewById(R.id.leftMenu);
            rightMenu = findViewById(R.id.rightMenu);
            boardView = findViewById(R.id.boardView);
            setItemImage = findViewById(R.id.setItemImage);
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //balloon.setVisibility(View.VISIBLE);
                    canvasLayout.setVisibility(View.VISIBLE);
                    leftMenu.setVisibility(View.VISIBLE);
                    rightMenu.setVisibility(View.VISIBLE);
                    setItemImage.setVisibility(View.VISIBLE);
                    YoYo.with(ZoomInLeft)
                            .duration(1500)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(leftMenu);
                    YoYo.with(ZoomInRight)
                            .duration(1500)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(rightMenu);
                    YoYo.with(DropOut)
                            .duration(1500)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(canvasLayout);


                }
            }, 200); // milliseconds: 1 seg.

        }

        //animation on button click
        {
            PushDownAnim.setPushDownAnimTo(saveButton);
            PushDownAnim.setPushDownAnimTo(newButton);
            PushDownAnim.setPushDownAnimTo(undoButton);
            PushDownAnim.setPushDownAnimTo(redoButton);
            PushDownAnim.setPushDownAnimTo(deleteButton);
            PushDownAnim.setPushDownAnimTo(sizeButton);
            PushDownAnim.setPushDownAnimTo(pencilButton);
            PushDownAnim.setPushDownAnimTo(brushButton);
            PushDownAnim.setPushDownAnimTo(backgroundButton);
            PushDownAnim.setPushDownAnimTo(eraseButton);

        }

        //set on click listener
        {
            newButton.setOnClickListener(this);
            undoButton.setOnClickListener(this);
            redoButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            sizeButton.setOnClickListener(this);
            pencilButton.setOnClickListener(this);
            brushButton.setOnClickListener(this);
            backgroundButton.setOnClickListener(this);
            eraseButton.setOnClickListener(this);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        //sizes from dimensions
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);
        //set initial size
        drawView.setBrushSize(mediumBrush);
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //we need to handle runtime permission for devices with marshmallow and above
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    //system OS >= Marshmallow(6.0), check if permission is enabled or not
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        //permission was not granted, request it
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);
                    } else {
                        //permission already granted, call save pdf method
                        layoutToImage(canvasLayout);

                    }
                } else {
                    //system OS < Marshmallow, call save pdf method
                    layoutToImage(canvasLayout);
                }
            }
        });

        //shapes
        if(itemType == 0){
            if(itemNo == 0){
                setItemImage.setImageResource(R.drawable.game_menu_shapes_one);
            }
            else if(itemNo == 1){
                setItemImage.setImageResource(R.drawable.game_menu_shapes_two);
            }
            else if(itemNo == 2){
                setItemImage.setImageResource(R.drawable.game_menu_shapes_three);
            }
            else if(itemNo == 3){
                setItemImage.setImageResource(R.drawable.game_menu_shapes_four);
            }
            else if(itemNo == 4){
                setItemImage.setImageResource(R.drawable.game_menu_shapes_five);
            }
            else if(itemNo == 5){
                setItemImage.setImageResource(R.drawable.game_menu_shapes_six);
            }
        }
        else if(itemType == 1){
            if(itemNo == 0){
                setItemImage.setImageResource(R.drawable.game_menu_bird_one);
            }
            else if(itemNo == 1){
                setItemImage.setImageResource(R.drawable.game_menu_bird_two);
            }
            else if(itemNo == 2){
                setItemImage.setImageResource(R.drawable.game_menu_bird_three);
            }
            else if(itemNo == 3){
                setItemImage.setImageResource(R.drawable.game_menu_bird_four);
            }
            else if(itemNo == 4){
                setItemImage.setImageResource(R.drawable.game_menu_bird_five);
            }
            else if(itemNo == 5){
                setItemImage.setImageResource(R.drawable.game_menu_bird_six);
            }

        }
        else if(itemType == 2){
            if(itemNo == 0){
                setItemImage.setImageResource(R.drawable.game_menu_fish_one);
            }
            else if(itemNo == 1){
                setItemImage.setImageResource(R.drawable.game_menu_fish_two);
            }
            else if(itemNo == 2){
                setItemImage.setImageResource(R.drawable.game_menu_fish_three);
            }
            else if(itemNo == 3){
                setItemImage.setImageResource(R.drawable.game_menu_fish_four);
            }
            else if(itemNo == 4){
                setItemImage.setImageResource(R.drawable.game_menu_fish_five);
            }
            else if(itemNo == 5){
                setItemImage.setImageResource(R.drawable.game_menu_fish_six);
            }
        }
        else if(itemType == 3){
            if(itemNo == 0){
                setItemImage.setImageResource(R.drawable.game_menu_animals_cow);
            }
            else if(itemNo == 1){
                setItemImage.setImageResource(R.drawable.game_menu_animals_doggy);
            }
            else if(itemNo == 2){
                setItemImage.setImageResource(R.drawable.game_menu_animals_elephant);
            }
            else if(itemNo == 3){
                setItemImage.setImageResource(R.drawable.game_menu_animals_deer);
            }
            else if(itemNo == 4){
                setItemImage.setImageResource(R.drawable.game_menu_animals_dynosor);
            }
            else if(itemNo == 5){
                setItemImage.setImageResource(R.drawable.game_menu_animals_horse);
            }

        }
        else if(itemType == 4){
            if(itemNo == 0){
                setItemImage.setImageResource(R.drawable.game_menu_flower_one);
            }
            else if(itemNo == 1){
                setItemImage.setImageResource(R.drawable.game_menu_flower_two);
            }
            else if(itemNo == 2){
                setItemImage.setImageResource(R.drawable.game_menu_flower_three);
            }
            else if(itemNo == 3){
                setItemImage.setImageResource(R.drawable.game_menu_flower_four);
            }
            else if(itemNo == 4){
                setItemImage.setImageResource(R.drawable.game_menu_flower_five);
            }
            else if(itemNo == 5){
                setItemImage.setImageResource(R.drawable.game_menu_flower_six);
            }

        }
        else if(itemType == 5){
            if(itemNo == 0){
                setItemImage.setImageResource(R.drawable.game_menu_nature_one);
            }
            else if(itemNo == 1){
                setItemImage.setImageResource(R.drawable.game_menu_nature_two);
            }
            else if(itemNo == 2){
                setItemImage.setImageResource(R.drawable.game_menu_nature_three);
            }
            else if(itemNo == 3){
                setItemImage.setImageResource(R.drawable.game_menu_nature_four);
            }
            else if(itemNo == 4){
                setItemImage.setImageResource(R.drawable.game_menu_nature_five);
            }
            else if(itemNo == 5){
                setItemImage.setImageResource(R.drawable.game_menu_nature_six);
            }
        }
        else if(itemType == 6){
            if(itemNo == 0){
                setItemImage.setImageResource(R.drawable.game_menu_car_one);
            }
            else if(itemNo == 1){
                setItemImage.setImageResource(R.drawable.game_menu_car_two);
            }
            else if(itemNo == 2){
                setItemImage.setImageResource(R.drawable.game_menu_car_three);
            }
            else if(itemNo == 3){
                setItemImage.setImageResource(R.drawable.game_menu_car_four);
            }
            else if(itemNo == 4){
                setItemImage.setImageResource(R.drawable.game_menu_car_five);
            }
            else if(itemNo == 5){
                setItemImage.setImageResource(R.drawable.game_menu_car_six);
            }

        }
        else if(itemType == 7){
            if(itemNo == 0){
                setItemImage.setImageResource(R.drawable.game_menu_fruit_one);
            }
            else if(itemNo == 1){
                setItemImage.setImageResource(R.drawable.game_menu_fruit_two);
            }
            else if(itemNo == 2){
                setItemImage.setImageResource(R.drawable.game_menu_fruit_three);
            }
            else if(itemNo == 3){
                setItemImage.setImageResource(R.drawable.game_menu_fruit_four);
            }
            else if(itemNo == 4){
                setItemImage.setImageResource(R.drawable.game_menu_fruit_five);
            }
            else if(itemNo == 5){
                setItemImage.setImageResource(R.drawable.game_menu_fruit_six);
            }

        }
    }
    //user clicked paint
    public void paintClicked(View view) {
        //use chosen color

        //set erase false
        drawView.setErase(false);
        drawView.setPaintAlpha(100);
        drawView.setBrushSize(drawView.getLastBrushSize());

        if (view != currPaint) {
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressedt));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paintt));
            currPaint = (ImageButton) view;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sizeButtonId) {
            //draw button clicked
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Brush size:");
            brushDialog.setContentView(R.layout.brush_choosert);
            //listen for clicks on size buttons
            ImageButton smallBtn = (ImageButton) brushDialog.findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(false);
                    drawView.setBrushSize(smallBrush);
                    drawView.setLastBrushSize(smallBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton mediumBtn = (ImageButton) brushDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(false);
                    drawView.setBrushSize(mediumBrush);
                    drawView.setLastBrushSize(mediumBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton largeBtn = (ImageButton) brushDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(false);
                    drawView.setBrushSize(largeBrush);
                    drawView.setLastBrushSize(largeBrush);
                    brushDialog.dismiss();
                }
            });
            //show and wait for user interaction
            brushDialog.show();

        }
        else if(v.getId() == R.id.pencilButtonId){
            final Dialog pencilDialog = new Dialog(this);
            pencilDialog.setContentView(R.layout.pop_up_pencil);
            //listen for clicks on size buttons

            red = pencilDialog.findViewById(R.id.red);
            blue = pencilDialog.findViewById(R.id.blue);
            skyblue = pencilDialog.findViewById(R.id.skyblue);
            yellow = pencilDialog.findViewById(R.id.yellow);
            green = pencilDialog.findViewById(R.id.green);
            lightgreen = pencilDialog.findViewById(R.id.lightgreen);
            purple = pencilDialog.findViewById(R.id.purple);
            pink = pencilDialog.findViewById(R.id.pink);
            orange = pencilDialog.findViewById(R.id.orange);
            brown = pencilDialog.findViewById(R.id.brown);
            black = pencilDialog.findViewById(R.id.black);
            white = pencilDialog.findViewById(R.id.white);
            red.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#F90404");
                    pencilDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "red!", Toast.LENGTH_SHORT).show();
                }
            });
            blue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#040CF9");
                    pencilDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "blue!", Toast.LENGTH_SHORT).show();
                }
            });
            skyblue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#0099ff");
                    pencilDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "skyblue!", Toast.LENGTH_SHORT).show();
                }
            });
            yellow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#ffff00");
                    pencilDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "yellow!", Toast.LENGTH_SHORT).show();
                }
            });
            green.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#006400");
                    pencilDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "green!", Toast.LENGTH_SHORT).show();
                }
            });
            lightgreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#32CD32");
                    pencilDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "lightgreen!", Toast.LENGTH_SHORT).show();
                }
            });
            purple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#ED04F9");
                    pencilDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "purple!", Toast.LENGTH_SHORT).show();
                }
            });
            pink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#e6b3cc");
                    pencilDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "pink!", Toast.LENGTH_SHORT).show();
                }
            });
            orange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#FF4500");
                    pencilDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "orange!", Toast.LENGTH_SHORT).show();
                }
            });
            brown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#b35900");
                    pencilDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "brown!", Toast.LENGTH_SHORT).show();
                }
            });
            black.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#000000");
                    pencilDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "black!", Toast.LENGTH_SHORT).show();
                }
            });
            white.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#ffffff");
                    pencilDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "white!", Toast.LENGTH_SHORT).show();
                }
            });


            //set erase false
            drawView.setErase(false);
            drawView.setPaintAlpha(100);
            drawView.setBrushSize(drawView.getLastBrushSize());

            pencilDialog.show();
        }
        else if (v.getId() == R.id.brushButtonId){
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setContentView(R.layout.pop_up_brush);
            //listen for clicks on size buttons

            red = brushDialog.findViewById(R.id.red);
            blue = brushDialog.findViewById(R.id.blue);
            skyblue = brushDialog.findViewById(R.id.skyblue);
            yellow = brushDialog.findViewById(R.id.yellow);
            green = brushDialog.findViewById(R.id.green);
            lightgreen = brushDialog.findViewById(R.id.lightgreen);
            purple = brushDialog.findViewById(R.id.purple);
            pink = brushDialog.findViewById(R.id.pink);
            orange = brushDialog.findViewById(R.id.orange);
            brown = brushDialog.findViewById(R.id.brown);
            black = brushDialog.findViewById(R.id.black);
            white = brushDialog.findViewById(R.id.white);
            red.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#F90404");
                    brushDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "red!", Toast.LENGTH_SHORT).show();
                }
            });
            blue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#040CF9");
                    brushDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "blue!", Toast.LENGTH_SHORT).show();
                }
            });
            skyblue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#0099ff");
                    brushDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "skyblue!", Toast.LENGTH_SHORT).show();
                }
            });
            yellow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#ffff00");
                    brushDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "yellow!", Toast.LENGTH_SHORT).show();
                }
            });
            green.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#006400");
                    brushDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "green!", Toast.LENGTH_SHORT).show();
                }
            });
            lightgreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#32CD32");
                    brushDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "lightgreen!", Toast.LENGTH_SHORT).show();
                }
            });
            purple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#ED04F9");
                    brushDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "purple!", Toast.LENGTH_SHORT).show();
                }
            });
            pink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#e6b3cc");
                    brushDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "pink!", Toast.LENGTH_SHORT).show();
                }
            });
            orange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#FF4500");
                    brushDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "orange!", Toast.LENGTH_SHORT).show();
                }
            });
            brown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#b35900");
                    brushDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "brown!", Toast.LENGTH_SHORT).show();
                }
            });
            black.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#000000");
                    brushDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "black!", Toast.LENGTH_SHORT).show();
                }
            });
            white.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setColor("#ffffff");
                    brushDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "white!", Toast.LENGTH_SHORT).show();
                }
            });


            //set erase false
            drawView.setErase(false);
            drawView.setPaintAlpha(100);
            drawView.setBrushSize(drawView.getLastBrushSize());

            brushDialog.show();


        }
        else if (v.getId() == R.id.backgroundId){
            final Dialog canvasDialog = new Dialog(this);
            canvasDialog.setContentView(R.layout.pop_up_canvas);
            //listen for clicks on size buttons

            red = canvasDialog.findViewById(R.id.red);
            blue = canvasDialog.findViewById(R.id.blue);
            skyblue = canvasDialog.findViewById(R.id.skyblue);
            yellow = canvasDialog.findViewById(R.id.yellow);
            green = canvasDialog.findViewById(R.id.green);
            lightgreen = canvasDialog.findViewById(R.id.lightgreen);
            purple = canvasDialog.findViewById(R.id.purple);
            pink = canvasDialog.findViewById(R.id.pink);
            orange = canvasDialog.findViewById(R.id.orange);
            brown = canvasDialog.findViewById(R.id.brown);
            black = canvasDialog.findViewById(R.id.black);
            white = canvasDialog.findViewById(R.id.white);
            red.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    boardView.setBackgroundColor();
//                    drawView.canvasColorChange("#F90404");
                    canvasChange("#F90404");
                    canvasDialog.dismiss();
                    ////Toast.makeText(getApplicationContext(), "red!", //Toast.LENGTH_SHORT).show();
                }
            });
            blue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //drawView.canvasColorChange("#040CF9");
                    canvasChange("#040CF9");
                    canvasDialog.dismiss();
                    ////Toast.makeText(getApplicationContext(), "blue!", //Toast.LENGTH_SHORT).show();
                }
            });
            skyblue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //drawView.canvasColorChange("#0099ff");
                    canvasChange("#0099ff");
                    canvasDialog.dismiss();
                    ////Toast.makeText(getApplicationContext(), "skyblue!", //Toast.LENGTH_SHORT).show();
                }
            });
            yellow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //drawView.canvasColorChange("#ffff00");
                    canvasChange("#ffff00");
                    canvasDialog.dismiss();
                    ////Toast.makeText(getApplicationContext(), "yellow!", //Toast.LENGTH_SHORT).show();
                }
            });
            green.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //drawView.canvasColorChange("#006400");
                    canvasChange("#006400");
                    canvasDialog.dismiss();
                    ////Toast.makeText(getApplicationContext(), "green!", //Toast.LENGTH_SHORT).show();
                }
            });
            lightgreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //drawView.canvasColorChange("#32CD32");
                    canvasChange("#32CD32");
                    canvasDialog.dismiss();
                    ////Toast.makeText(getApplicationContext(), "lightgreen!", //Toast.LENGTH_SHORT).show();
                }
            });
            purple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //drawView.canvasColorChange("#ED04F9");
                    canvasChange("#ED04F9");
                    canvasDialog.dismiss();
                    ////Toast.makeText(getApplicationContext(), "purple!", //Toast.LENGTH_SHORT).show();
                }
            });
            pink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //drawView.canvasColorChange("#e6b3cc");
                    canvasChange("#e6b3cc");
                    canvasDialog.dismiss();
                    ////Toast.makeText(getApplicationContext(), "pink!", //Toast.LENGTH_SHORT).show();
                }
            });
            orange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //drawView.canvasColorChange("#FF4500");
                    canvasChange("#FF4500");
                    canvasDialog.dismiss();
                    ////Toast.makeText(getApplicationContext(), "orange!", //Toast.LENGTH_SHORT).show();
                }
            });
            brown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //drawView.canvasColorChange("#b35900");
                    canvasChange("#b35900");
                    canvasDialog.dismiss();
                    ////Toast.makeText(getApplicationContext(), "brown!", //Toast.LENGTH_SHORT).show();
                }
            });
            black.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //drawView.canvasColorChange("#000000");
                    canvasChange("#000000");
                    canvasDialog.dismiss();
                    ////Toast.makeText(getApplicationContext(), "black!", //Toast.LENGTH_SHORT).show();
                }
            });
            white.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //drawView.canvasColorChange("#ffffff");
                    canvasChange("#ffffff");
                    canvasDialog.dismiss();
                    ////Toast.makeText(getApplicationContext(), "white!", //Toast.LENGTH_SHORT).show();
                }
            });


            //set erase false
            drawView.setErase(false);
            drawView.setPaintAlpha(100);
            drawView.setBrushSize(drawView.getLastBrushSize());

            canvasDialog.show();

        }
        else if(v.getId() == R.id.eraseButtonId){
            //switch to erase - choose size
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Eraser size:");
            brushDialog.setContentView(R.layout.brush_choosert);
            //size buttons
            ImageButton smallBtn = (ImageButton) brushDialog.findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(smallBrush);
                    brushDialog.dismiss();

                }
            });
            ImageButton mediumBtn = (ImageButton) brushDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(mediumBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton largeBtn = (ImageButton) brushDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(largeBrush);
                    brushDialog.dismiss();
                }
            });
            brushDialog.show();

        }

        else if(v.getId() == R.id.newButtonId){
            newAlertDialog();
        }
        else if(v.getId() == R.id.undoButtonId){
            drawView.onClickUndo();
        }
        else if(v.getId() == R.id.redoButtonId){
            drawView.onClickRedo();
        }
        else if(v.getId() == R.id.deleteButtonId){
            deleteAlertDialog();

        }
    }

    public void newAlertDialog(){
        AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
        newDialog.setTitle("New drawing");
        newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
        newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                boardView.setBackgroundColor(Color.WHITE);
                drawView.startNew();
                dialog.dismiss();
            }
        });
        newDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        newDialog.show();
    }
    public void deleteAlertDialog(){
        AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
        newDialog.setTitle("Deleting ...");
        newDialog.setMessage("Are you sure, You will delete?");
        newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                drawView.deleteDrawing();
                dialog.dismiss();
            }
        });
        newDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        newDialog.show();
    }
    public void layoutToImage(View view) {
        mFileName = new SimpleDateFormat("yyyyMMdd_HHmm",
                Locale.getDefault()).format(System.currentTimeMillis());
        Log.d("Tag",mFileName);
        // get view group using reference
        //relativeLayout = (RelativeLayout)findViewById(R.id.print);
        //pri=findViewById(R.id.pri);



        int totalHeight = boardView.getChildAt(0).getHeight();
        int totalWidth = boardView.getChildAt(0).getWidth();
        //first.setText(myEditText.getText().toString());
        // convert view group to bitmap
        boardView.setDrawingCacheEnabled(true);
        boardView.buildDrawingCache();
        Bitmap bm = getBitmapFromView(boardView, totalHeight, totalWidth);
        //Bitmap bm = pri.getDrawingCache();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DrawWithFun";
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();

        File f = new File(dir + File.separator + mFileName + ".jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        MediaScannerConnection.scanFile(this,
                new String[] { path }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.d("SAVE", "scanned : " + path);
                    }
                });
        Toast.makeText(getApplicationContext(), "Drawing saved. Preview it in Album", Toast.LENGTH_SHORT).show();

    }
    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_top,R.anim.slide_in_top);
    }
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
        if(itemType == 0){
            if(itemNo == 0){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 1){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 2){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 3){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 4){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 5){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
        }
        else if(itemType == 1){
            if(itemNo == 0){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 1){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 2){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 3){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 4){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 5){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }

        }
        else if(itemType == 2){
            if(itemNo == 0){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 1){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 2){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 3){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 4){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 5){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
        }
        else if(itemType == 3){
            if(itemNo == 0){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 1){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 2){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 3){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 4){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 5){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }

        }
        else if(itemType == 4){
            if(itemNo == 0){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 1){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 2){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 3){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 4){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 5){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }

        }
        else if(itemType == 5){
            if(itemNo == 0){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 1){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 2){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 3){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 4){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 5){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
        }
        else if(itemType == 6){
            if(itemNo == 0){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 1){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 2){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 3){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 4){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 5){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }

        }
        else if(itemType == 7){
            if(itemNo == 0){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 1){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 2){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 3){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 4){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }
            else if(itemNo == 5){
                Intent colorBookItemsIntent = new Intent(getApplicationContext(),ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                colorBookItemsIntent.putExtra("type",itemType);
                startActivity(colorBookItemsIntent);
            }

        }
    }
    //canvas color change
    public void canvasChange(String newColor) {
        int  paintColor = Color.parseColor(newColor);
        boardView.setBackgroundColor(paintColor);

    }
}