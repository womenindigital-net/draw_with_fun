package com.newname.drawwithfun;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.viewpager2.widget.ViewPager2;

import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
   private Context context;
    private List<SliderItem> sliderItems;
   private ViewPager2 viewPager2;

    public SliderAdapter(Context context,List<SliderItem> sliderItems, ViewPager2 viewPager2) {
        this.context = context;
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container,
                        parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, final int position) {
        holder.setImage(sliderItems.get(position));

        PushDownAnim.setPushDownAnimTo(holder.game_menu_image);
        holder.game_menu_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent colorBookItemsIntent = new Intent(context,ColorBookItemActivity.class);
                colorBookItemsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.d("TAG", String.valueOf(position));
                if(position == 0){
                    colorBookItemsIntent.putExtra("type",0);
                    context.startActivity(colorBookItemsIntent);
                }
                else if(position == 1){
                    colorBookItemsIntent.putExtra("type",1);
                    context.startActivity(colorBookItemsIntent);
                }
                else if(position == 2){
                    colorBookItemsIntent.putExtra("type",2);
                    context.startActivity(colorBookItemsIntent);
                }
                else if(position == 3){
                    colorBookItemsIntent.putExtra("type",3);
                    context.startActivity(colorBookItemsIntent);
                }
                else if(position == 4){
                    colorBookItemsIntent.putExtra("type",4);
                    context.startActivity(colorBookItemsIntent);
                }
                else if(position == 5){
                    colorBookItemsIntent.putExtra("type",5);
                    context.startActivity(colorBookItemsIntent);
                }
                else if(position == 6){
                    colorBookItemsIntent.putExtra("type",6);
                    context.startActivity(colorBookItemsIntent);
                }
                else if(position == 7){
                    colorBookItemsIntent.putExtra("type",7);
                    context.startActivity(colorBookItemsIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{
        private ImageView game_menu_image;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            game_menu_image = itemView.findViewById(R.id.game_menu_image);

        }

        void setImage(SliderItem sliderItem){
            game_menu_image.setImageResource(sliderItem.getImage());
        }
    }
//    public void startIntent(){
//    }

}
