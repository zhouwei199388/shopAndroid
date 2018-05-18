package com.zw.shop.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.zw.shop.R;
import com.zw.shop.divider.HorizontalDividerItemDecoration;
import com.zw.shop.layoutmanager.ILayoutManager;
import com.zw.shop.layoutmanager.MyLinearLayoutManager;
import com.zw.shop.view.TRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2016/6/3.
 */
public abstract class BaseListFrgment<T> extends Fragment implements TRecyclerView.OnRecyclerRefreshListener {
    protected TRecyclerView mTRecyclerView;
    protected BaseListAdapter mAdapter;
    protected List<T> mListData = new ArrayList<>();


    protected boolean isLoadMore = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTRecyclerView = (TRecyclerView) view.findViewById(R.id.recyclerView);
        setUpAdapter();
        mTRecyclerView.setOnRefreshListener(this);
        mTRecyclerView.setLayoutManager(getLayoutManager());
        mTRecyclerView.addItemDecoration(getItemDecoration());
        mTRecyclerView.setAdapter(mAdapter);
    }


    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getActivity().getApplicationContext());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(getContext())
                .colorResId(R.color.colorGray)
                .size(1)
                .build();
    }

    private void setUpAdapter() {
        mAdapter = new ListAdapter();
    }

    protected void setLoadMore(boolean enable) {
        isLoadMore = enable;
    }

    protected boolean isLoadMore() {
        return isLoadMore;
    }

    @Override
    public void onRefresh(int action) {

    }


    class ListAdapter extends BaseListAdapter {

        @Override
        protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        }

        @Override
        protected int getDateCount() {
            return mListData != null ? mListData.size() : 0;
        }

        @Override
        protected int getDateViewType(int position) {
            return getItemType(position);
        }

        @Override
        public boolean isSectionHeader(int position) {
            return BaseListFrgment.this.isSectionHeader(position);
        }
    }

    protected boolean isSectionHeader(int position) {
        return false;
    }

    protected int getItemType(int position) {
        return 0;
    }

    protected abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);

    protected abstract void reload();
}
