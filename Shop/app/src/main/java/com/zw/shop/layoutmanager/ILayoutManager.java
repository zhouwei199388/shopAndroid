package com.zw.shop.layoutmanager;

import android.support.v7.widget.RecyclerView;

import com.zw.shop.base.BaseListAdapter;


public interface ILayoutManager {
    RecyclerView.LayoutManager getLayoutManager();

    int findLastVisiblePosition();

    void setUpAdapter(BaseListAdapter adapter);
}
