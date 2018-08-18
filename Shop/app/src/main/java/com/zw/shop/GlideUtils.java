package com.zw.shop;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideUtils extends Activity {

    public GlideUtils(){
        String url = "";
     Glide.with(this).load(url).into(new ImageView(this));
    }
}
