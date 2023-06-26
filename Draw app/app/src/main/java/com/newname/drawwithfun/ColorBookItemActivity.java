package com.newname.drawwithfun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.YoYo;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.daimajia.androidanimations.library.Techniques.DropOut;
import static com.daimajia.androidanimations.library.Techniques.ZoomInLeft;

public class ColorBookItemActivity extends AppCompatActivity implements View.OnClickListener {
    public  int  itemType;
    ImageView image0,image1,image2,image3,image4,image5;
    ImageView back;
    LinearLayout colorBox;
    Bitmap mDrawBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_book_item);
        Log.d("Activity","ColorBookItemActivity");

        image0 = findViewById(R.id.image0);
        image1 = findViewById(R.id.imageOne);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);

        back = findViewById(R.id.back);
        PushDownAnim.setPushDownAnimTo(back);
        colorBox = findViewById(R.id.colorBox);
        colorBox.setVisibility(View.INVISIBLE);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                colorBox.setVisibility(View.VISIBLE);
                YoYo.with(DropOut)
                        .duration(1200)
                        .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                        .interpolate(new AccelerateDecelerateInterpolator())
                        .playOn(colorBox);

            }
        }, 200); // milliseconds: 1 seg.


        unbindDrawables(image0);
        unbindDrawables(image1);
        unbindDrawables(image2);
        unbindDrawables(image3);
        unbindDrawables(image4);
        unbindDrawables(image5);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent colorBookItemActivity = getIntent();
        itemType = colorBookItemActivity.getExtras().getInt("type");
        if(itemType == 0){
            releaseBitmap();
            image0.setImageResource(R.drawable.game_menu_shapes_one);
            releaseBitmap();
            image1.setImageResource(R.drawable.game_menu_shapes_two);
            releaseBitmap();
            image2.setImageResource(R.drawable.game_menu_shapes_three);
            releaseBitmap();
            image3.setImageResource(R.drawable.game_menu_shapes_four);
            releaseBitmap();
            image4.setImageResource(R.drawable.game_menu_shapes_five);
            releaseBitmap();
            image5.setImageResource(R.drawable.game_menu_shapes_six);

        }
        else if(itemType == 1){
            releaseBitmap();
            image0.setImageResource(R.drawable.game_menu_bird_one);
            image1.setImageResource(R.drawable.game_menu_bird_two);
            image2.setImageResource(R.drawable.game_menu_bird_three);
            image3.setImageResource(R.drawable.game_menu_bird_four);
            image4.setImageResource(R.drawable.game_menu_bird_five);
            image5.setImageResource(R.drawable.game_menu_bird_six);

        }
        else if(itemType == 2){
            releaseBitmap();
            image0.setImageResource(R.drawable.game_menu_fish_one);
            image1.setImageResource(R.drawable.game_menu_fish_two);
            image2.setImageResource(R.drawable.game_menu_fish_three);
            image3.setImageResource(R.drawable.game_menu_fish_four);
            image4.setImageResource(R.drawable.game_menu_fish_five);
            image5.setImageResource(R.drawable.game_menu_fish_six);

        }
        else if(itemType == 3){
            releaseBitmap();
            image0.setImageResource(R.drawable.game_menu_animals_cow);
            image1.setImageResource(R.drawable.game_menu_animals_doggy);
            image2.setImageResource(R.drawable.game_menu_animals_elephant);
            image3.setImageResource(R.drawable.game_menu_animals_deer);
            image4.setImageResource(R.drawable.game_menu_animals_dynosor);
            image5.setImageResource(R.drawable.game_menu_animals_horse);

        }
        else if(itemType == 4){
            releaseBitmap();
            image0.setImageResource(R.drawable.game_menu_flower_one);
            image1.setImageResource(R.drawable.game_menu_flower_two);
            image2.setImageResource(R.drawable.game_menu_flower_three);
            image3.setImageResource(R.drawable.game_menu_flower_four);
            image4.setImageResource(R.drawable.game_menu_flower_five);
            image5.setImageResource(R.drawable.game_menu_flower_six);

        }
        else if(itemType == 5){
            releaseBitmap();
            image0.setImageResource(R.drawable.game_menu_nature_one);
            image1.setImageResource(R.drawable.game_menu_nature_two);
            image2.setImageResource(R.drawable.game_menu_nature_three);
            image3.setImageResource(R.drawable.game_menu_nature_four);
            image4.setImageResource(R.drawable.game_menu_nature_five);
            image5.setImageResource(R.drawable.game_menu_nature_six);

        }
        else if(itemType == 6){
            releaseBitmap();
            image0.setImageResource(R.drawable.game_menu_car_one);
            image1.setImageResource(R.drawable.game_menu_car_two);
            image2.setImageResource(R.drawable.game_menu_car_three);
            image3.setImageResource(R.drawable.game_menu_car_four);
            image4.setImageResource(R.drawable.game_menu_car_five);
            image5.setImageResource(R.drawable.game_menu_car_six);

        }
        else if(itemType == 7){
            releaseBitmap();
            image0.setImageResource(R.drawable.game_menu_fruit_one);
            image1.setImageResource(R.drawable.game_menu_fruit_two);
            image2.setImageResource(R.drawable.game_menu_fruit_three);
            image3.setImageResource(R.drawable.game_menu_fruit_four);
            image4.setImageResource(R.drawable.game_menu_fruit_five);
            image5.setImageResource(R.drawable.game_menu_fruit_six);
        }
        {

            image0.setOnClickListener(this);
            image1.setOnClickListener(this);
            image2.setOnClickListener(this);
            image3.setOnClickListener(this);
            image4.setOnClickListener(this);
            image5.setOnClickListener(this);

            PushDownAnim.setPushDownAnimTo(image0);
            PushDownAnim.setPushDownAnimTo(image1);
            PushDownAnim.setPushDownAnimTo(image2);
            PushDownAnim.setPushDownAnimTo(image3);
            PushDownAnim.setPushDownAnimTo(image4);
            PushDownAnim.setPushDownAnimTo(image5);
        }

    }

    @Override
    public void onClick(View v) {
        Intent itemDrawIntent = new Intent(ColorBookItemActivity.this,ColorBookItemDrawActivity.class);
        if(v.getId()==R.id.image0){
            itemDrawIntent.putExtra("itemType",itemType);
            itemDrawIntent.putExtra("itemNo",0);
            startActivity(itemDrawIntent);

        } else if(v.getId() == R.id.imageOne){
            itemDrawIntent.putExtra("itemType",itemType);
            itemDrawIntent.putExtra("itemNo",1);
            startActivity(itemDrawIntent);

        } else if(v.getId() == R.id.image2){
            itemDrawIntent.putExtra("itemType",itemType);
            itemDrawIntent.putExtra("itemNo",2);
            startActivity(itemDrawIntent);

        } else if(v.getId() == R.id.image3){
            itemDrawIntent.putExtra("itemType",itemType);
            itemDrawIntent.putExtra("itemNo",3);
            startActivity(itemDrawIntent);

        } else if(v.getId() == R.id.image4){
            itemDrawIntent.putExtra("itemType",itemType);
            itemDrawIntent.putExtra("itemNo",4);
            startActivity(itemDrawIntent);

        } else if(v.getId() == R.id.image5){
            itemDrawIntent.putExtra("itemType",itemType);
            itemDrawIntent.putExtra("itemNo",5);
            startActivity(itemDrawIntent);

        }

    }
    public void releaseBitmap() {
        if (mDrawBitmap != null) {
            mDrawBitmap.recycle();
            mDrawBitmap = null;
        }
    }
    private void unbindDrawables(View view)
    {
        if (view.getBackground() != null)
        {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView))
        {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

}