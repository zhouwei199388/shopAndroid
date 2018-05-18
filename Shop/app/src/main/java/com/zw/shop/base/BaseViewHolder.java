package com.zw.shop.base;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by Admin on 2016/6/3.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "onClick: " + getLayoutPosition());
                onItemClick(v, getLayoutPosition());
            }
        });
    }

    public abstract void onBindViewHolder(int position);

    public abstract void onItemClick(View view, int position);
}
