package com.zw.shop.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


import com.zw.shop.R;
import com.zw.shop.base.BaseListAdapter;
import com.zw.shop.layoutmanager.ILayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 2016/6/3.
 */
public class TRecyclerView extends LinearLayout implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerVeiw;
    public static final int ACTION_PULL_TO_REFRESH = 1;
    public static final int ACTION_LOAD_MORE_REFRESH = 2;
    public static final int ACTION_IDLE = 0;
    private OnRecyclerRefreshListener listener;
    /**
     * 滑动状态
     */
    private int mCurrentState = ACTION_IDLE;
    private boolean isLoadMoreEnabled = false;
    private boolean isPullToRefreshEnabled = true;
    private ILayoutManager mLayoutManager;
    private BaseListAdapter adapter;

    public TRecyclerView(Context context) {
        super(context);
        init();
    }

    public TRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_recyclerview, this, true);
        ButterKnife.bind(this, view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerVeiw.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mCurrentState == ACTION_IDLE && isLoadMoreEnabled
                        && checkIfNeedLoadMore()) {
                    mCurrentState = ACTION_LOAD_MORE_REFRESH;
                    adapter.onLoadMoreStateChanged(true);
                    mSwipeRefreshLayout.setEnabled(true);
                    listener.onRefresh(ACTION_LOAD_MORE_REFRESH);
                }
            }
        });
    }

    /**
     * 是否需要加载更多
     */
    private boolean checkIfNeedLoadMore() {
        int lastVisibleItemPosition = mLayoutManager.findLastVisiblePosition();
        int totalCount = mLayoutManager.getLayoutManager().getItemCount();
        return totalCount - lastVisibleItemPosition < 2;
    }


    /**
     * 设置是否上拉加载
     *
     * @param enable
     */
    public void enableLoadMore(boolean enable) {
        isLoadMoreEnabled = enable;
    }


    /**
     * 下拉刷新是否开启
     *
     * @param enable
     */
    public void enablePullToRefresh(boolean enable) {
        isPullToRefreshEnabled = enable;
        mSwipeRefreshLayout.setEnabled(enable);
    }


    /**
     * 设置recyclerview ManagerＦ
     *
     * @param layoutManager
     */
    public void setLayoutManager(ILayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        mRecyclerVeiw.setLayoutManager(layoutManager.getLayoutManager());
    }

    /**
     * 设置recyclerview分割线
     *
     * @param itemDecoration
     */
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        if (itemDecoration != null) {
            mRecyclerVeiw.addItemDecoration(itemDecoration);
        }
    }


    /**
     * set adapter
     *
     * @param adapter
     */
    public void setAdapter(BaseListAdapter adapter) {
        this.adapter = adapter;
        mRecyclerVeiw.setAdapter(adapter);
        mLayoutManager.setUpAdapter(adapter);
    }


    public void setRefreshing() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }


    public void onRefreshCompleted() {
        switch (mCurrentState) {
            case ACTION_PULL_TO_REFRESH:
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case ACTION_LOAD_MORE_REFRESH:
                adapter.onLoadMoreStateChanged(false);
//                if (isPullToRefreshEnabled) {
//                    mSwipeRefreshLayout.setEnabled(false);
//                }
                break;
        }
        mCurrentState = ACTION_IDLE;
    }


    public void setSelection(int position) {
        if (position != -1) {
            mRecyclerVeiw.smoothScrollToPosition(position);
        }
    }

    public RecyclerView getRecyclerVeiw() {
        return mRecyclerVeiw;
    }


    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }


    public void setOnRefreshListener(OnRecyclerRefreshListener listener) {
        this.listener = listener;
    }

    @Override
    public void onRefresh() {
        Log.d("tag", "onRefresh:  onrefresh");
        mCurrentState = ACTION_PULL_TO_REFRESH;
        listener.onRefresh(ACTION_PULL_TO_REFRESH);
    }

    public interface OnRecyclerRefreshListener {
        void onRefresh(int action);
    }
}
