package com.zw.shop.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

/**
 * Created by ZouWei on 2017/10/24.
 */

public class TestView extends View {
    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    VelocityTracker velocityTracker;
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("tag", "down");
                velocityTracker = VelocityTracker.obtain();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("tag", "move");
                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000);
                int xVelocity = (int) velocityTracker.getXVelocity();
                int yVelocity = (int) velocityTracker.getYVelocity();
                Log.d("tag", "xVelocity:" + xVelocity + "   yVelocity:" + yVelocity);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("tag", "up");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("tag", "onTouchEvent");

        return super.onTouchEvent(event);
    }
}
