package com.newname.drawwithfun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

public class ColorBookActivity extends AppCompatActivity {
    ImageView close;
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    RelativeLayout colorBookLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_book);
        Log.d("Activity","ColorBookActivity");

        close = findViewById(R.id.close);
        viewPager2 = findViewById(R.id.viewPager);
        colorBookLayout = findViewById(R.id.colorBookLayout);
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.cb_menu_shape));
        sliderItems.add(new SliderItem(R.drawable.cb_menu_bird));
        sliderItems.add(new SliderItem(R.drawable.cb_menu_fish));
        sliderItems.add(new SliderItem(R.drawable.cb_menu_animal));
        sliderItems.add(new SliderItem(R.drawable.cb_menu_flower));
        sliderItems.add(new SliderItem(R.drawable.cb_menu_nature));
        sliderItems.add(new SliderItem(R.drawable.cb_menu_car));
        sliderItems.add(new SliderItem(R.drawable.cb_menu_fruit));

        viewPager2.setAdapter(new SliderAdapter(getApplicationContext(),sliderItems,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r= 1-Math.abs(position);
                page.setScaleX(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        PushDownAnim.setPushDownAnimTo(close);

        viewPager2.setOnTouchListener(new OnSwipeTouchListener(ColorBookActivity.this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                //Toast.makeText(ColorBookActivity.this, "Swipe Left gesture detected", Toast.LENGTH_SHORT).show();
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                //Toast.makeText(ColorBookActivity.this, "Swipe Right gesture detected", Toast.LENGTH_SHORT).show();
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(ColorBookActivity.this,HomeActivity.class);
                startActivity(homeIntent);
                Log.d("close","ColorBookActivityClose");
                finish();
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(slideRunnable);
                sliderHandler.postDelayed(slideRunnable,2000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
    private Runnable slideRunnable = new Runnable() {

        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() +1);
        }
    };

//    @Override
//    public void onBackPressed() {
//        NavUtils.navigateUpFromSameTask(this);
//    }
}