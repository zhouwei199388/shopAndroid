package com.zw.shop.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

import com.zw.shop.R

/**
 * Created by ZouWei on 2018/8/24.
 */
class ProgressBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val DEFAULT_MAX_NUM = 100
    private val DEFAULT_MIN_NUM = 0
    private val DEFAULT_FIRST_NUM = 0
    private val DEFAULT_WIDHT = 630 //默认宽度
    private val DEFAULT_HEIGHT = 40 //默认高度

    private var mWidth = DEFAULT_WIDHT
    private var mHeight = DEFAULT_HEIGHT
    private val mRoundWidth = 20f
    private var mProgressX = 0 + mRoundWidth
    //进度条最大值
    private var mMaxNum = DEFAULT_MAX_NUM
    //进度条最小值
    private var mMinNum = DEFAULT_MIN_NUM
    //进度条初始值
    private var mFirstNum = DEFAULT_FIRST_NUM

    private var mBgPaint = Paint(Paint.ANTI_ALIAS_FLAG)//背景画笔
    private var mProPaint = Paint(Paint.ANTI_ALIAS_FLAG)//进度画笔

    init {
        initBgPaint()
        mProPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBarView)
        if (typedArray != null) {
            mFirstNum = typedArray.getInteger(R.styleable.ProgressBarView_firstNum, DEFAULT_FIRST_NUM)
            mMinNum = typedArray.getInteger(R.styleable.ProgressBarView_minNum, DEFAULT_MIN_NUM)
            mMaxNum = typedArray.getInteger(R.styleable.ProgressBarView_maxNum, DEFAULT_MAX_NUM)
            typedArray.recycle()
        }
    }


    private fun initBgPaint() {
        mBgPaint.color = ContextCompat.getColor(context, R.color.colorGray)
        mBgPaint.strokeCap = Paint.Cap.ROUND
        mBgPaint.strokeWidth = 40f
        System.out.println()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.d("tag", "width:$width height:$height")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = getSize(DEFAULT_WIDHT, widthMeasureSpec)
        mHeight = getSize(DEFAULT_HEIGHT, heightMeasureSpec)
        setMeasuredDimension(mWidth, mHeight)
    }

    private fun getSize(defaultSize: Int, measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        var size = MeasureSpec.getSize(measureSpec)
        if (mode != MeasureSpec.EXACTLY) size = defaultSize
        return size
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val lineY = mHeight.toFloat() / 2
        val startLineX = 0 + mRoundWidth
        val endLineX = mWidth - mRoundWidth
        canvas.drawLine(startLineX, lineY, endLineX, lineY, mBgPaint)
        mBgPaint.color = ContextCompat.getColor(context, R.color.colorPrimary)
        canvas.drawLine(startLineX, lineY, mProgressX, lineY, mBgPaint)
        canvas.drawCircle(mProgressX, lineY, lineY, mProPaint)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event==null)return super.onTouchEvent(event)
        Log.d("tag", "eventX:${event.x}   eventY:${event.y}")
        return super.onTouchEvent(event)
    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        Log.d("tag", "eventX:${event.x}   eventY:${event.y}")
//        val eventX = event.x
//        val eventY = event.y
////        if (mProgressX + mRoundWidth > eventX && eventX > mProgressX - mRoundWidth
////                && eventY < mProgressX + mRoundWidth && eventY > mProgressX - mRoundWidth) {
////        mProgressX = eventX + mRoundWidth
////        invalidate()
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//
//            }
//            MotionEvent.ACTION_MOVE -> {
//                Log.d("tag", "eventX:${event.x}   eventY:${event.y}")
////                    val pro = (mWidth - mRoundWidth * 2) / (mMaxNum - mMinNum)
////                    eventX.toInt() - eventX.toInt() % pro
////                    mProgressX = eventX
////                    invalidate()
//            }
//            MotionEvent.ACTION_UP -> {
//
//            }
//        }
//        return super.onTouchEvent(event)
//    }
}
