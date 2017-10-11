package com.sdl.app.donate;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static com.sdl.app.donate.R.id.image_in_Viewpager;


public class ViewPager_Adapter extends PagerAdapter {

    private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3,
                            R.drawable.image1, R.drawable.image2, R.drawable.image3};

    private LayoutInflater layoutInflater;
    private Context context;

    public ViewPager_Adapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.swipe, container, false);
        ImageView img = (ImageView) v.findViewById(image_in_Viewpager);
        img.setImageResource(images[position]);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}
