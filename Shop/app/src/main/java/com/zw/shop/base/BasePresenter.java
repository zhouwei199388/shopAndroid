package com.zw.shop.base;

/**
 * Created by Admin on 2016/6/3.
 */
public class BasePresenter<M extends BaseModel, V extends IBaseView> {

    public M mModel;
    public V mView;

    public BasePresenter(M model, V view) {
        this.mModel = model;
        this.mView = view;
    }
}
