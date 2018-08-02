package bluetoothdemo.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import bluetoothdemo.myapplication.R;
import bluetoothdemo.myapplication.utils.DensityUtil;


/**
 * 自定义Dialog
 */
public class BaseDialogBuild extends Dialog {
    private Context mContext;
    private View mCustomView;

    private String mTitle;
    private String mMessage;
    private int mIconResId = -1;

    private boolean mHasMessage = false;
    private boolean mShowTitle = false;

    private int mTextGravity = Gravity.CENTER;

    private String mNegativeMessage, mPositiveMessage, mNeutralMessage;

    public static final int BUTTON_POSITIVE = 1 << 0;
    public static final int BUTTON_NEUTRAL = 1 << 1;
    public static final int BUTTON_NEGATIVE = 1 << 2;
    private int mButtonFlag;

    private OnDialogClickListener mNegativeListener;
    private OnDialogClickListener mPositiveListener;
    private OnDialogClickListener mNeutralListener;

    public interface OnDialogClickListener {
        void onClick(Dialog dialog);
    }

    public BaseDialogBuild(Context context) {
        super(context, R.style.Custom_Dialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

        init(mContext);
    }

    private void init(Context context) {
        View rootView = View.inflate(context, R.layout.layout_dialog_main, null);

        View topView = rootView.findViewById(R.id.topPanel);
        FrameLayout frameLayoutCustomView = (FrameLayout) rootView.findViewById(R.id.customPanel);

        TextView messageView = (TextView) rootView.findViewById(R.id.tv_custom_dialog_message);
        messageView.setGravity(mTextGravity);

        if (mCustomView != null) {
            frameLayoutCustomView.setVisibility(View.VISIBLE);
            if (frameLayoutCustomView.getChildCount() > 0) {
                frameLayoutCustomView.removeAllViews();
            }
            frameLayoutCustomView.addView(mCustomView);
        }

        if (mShowTitle) {
            topView.setVisibility(View.VISIBLE);
            TextView titleView = (TextView) rootView.findViewById(R.id.tv_custom_dialog_title);
            titleView.setText(mTitle);

            ImageView iconView = (ImageView) rootView.findViewById(R.id.icon);
            if (mIconResId != -1) {
                iconView.setImageResource(mIconResId);
            }
        } else {
            topView.setVisibility(View.GONE);
        }

        if (mHasMessage) {
            messageView.setVisibility(View.VISIBLE);
            messageView.setText(mMessage);
            if (!mShowTitle) {
                messageView.setPadding(messageView.getPaddingLeft(),
                        messageView.getPaddingTop() + DensityUtil.dip2px(context, 10),
                        messageView.getPaddingRight(),
                        messageView.getPaddingBottom());
            }
        } else {
            messageView.setVisibility(View.GONE);
        }

        setUpButtons(rootView);
        setContentView(rootView);
    }

    /**
     * 设置Dialog内容文本的位置，居中，居左，还是居右；默认居中
     *
     * @param gravity 居中{@link Gravity#CENTER},居左{@link Gravity#LEFT},居右{@link Gravity#RIGHT}
     */
    public BaseDialogBuild setTextGravity(int gravity) {
        mTextGravity = gravity;
        return this;
    }

    private void setUpButtons(View rootView) {
        if (mButtonFlag == 0) {
            //表示没有添加过按钮
            rootView.findViewById(R.id.button_panel).setVisibility(View.GONE);
        } else {
            Button leftButton = null;
            Button middleButton = null;
            Button rightButton = null;

            if (hasButton(BUTTON_NEGATIVE)) {
                leftButton = (Button) rootView.findViewById(R.id.btn_left);
                leftButton.setText(mNegativeMessage);
                leftButton.setVisibility(View.VISIBLE);
                leftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null == mNegativeListener) {
                            dismiss();
                        } else {
                            mNegativeListener.onClick(BaseDialogBuild.this);
                        }
                    }
                });
            }

            if (hasButton(BUTTON_NEUTRAL)) {
                middleButton = (Button) rootView.findViewById(R.id.btn_middle);
                middleButton.setText(mNeutralMessage);
                middleButton.setVisibility(View.VISIBLE);
                middleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null == mNeutralListener) {
                            dismiss();
                        } else {
                            mNeutralListener.onClick(BaseDialogBuild.this);
                        }
                    }
                });
            }

            if (hasButton(BUTTON_POSITIVE)) {
                rightButton = (Button) rootView.findViewById(R.id.btn_right);
                rightButton.setText(mPositiveMessage);
                rightButton.setVisibility(View.VISIBLE);
                rightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null == mPositiveListener) {
                            dismiss();
                        } else {
                            mPositiveListener.onClick(BaseDialogBuild.this);
                        }
                    }
                });
            }

            View divideOne = rootView.findViewById(R.id.divider_one);
            View divideTwo = rootView.findViewById(R.id.divider_two);
            int count = getButtonCount();
            if (count == 3) {
                divideOne.setVisibility(View.VISIBLE);
                divideTwo.setVisibility(View.VISIBLE);
            } else if (count == 2) {
                if (hasButton(BUTTON_NEGATIVE)) {
                    divideOne.setVisibility(View.VISIBLE);
                } else {
                    divideTwo.setVisibility(View.VISIBLE);
                }
            } else {
                //如果只有一个按钮的情况下，对话框按钮的背景要重新设置，主要是按钮圆角的考虑
                if (mButtonFlag == BUTTON_POSITIVE) {
                    assert rightButton != null;
                    rightButton.setBackgroundResource(R.drawable.dialog_btn_selector);
                } else if (mButtonFlag == BUTTON_NEUTRAL) {
                    assert middleButton != null;
                    middleButton.setBackgroundResource(R.drawable.dialog_btn_selector);
                } else if (mButtonFlag == BUTTON_NEGATIVE) {
                    assert leftButton != null;
                    leftButton.setBackgroundResource(R.drawable.dialog_btn_selector);
                }
            }
        }
    }

    private int getButtonCount() {
        int count = 0;
        if (hasButton(BUTTON_POSITIVE)) {
            count ++;
        }

        if (hasButton(BUTTON_NEGATIVE)) {
            count ++;
        }

        if (hasButton(BUTTON_NEUTRAL)) {
            count ++;
        }
        return count;
    }

    private boolean hasButton(int whichButton) {
        return (mButtonFlag & whichButton) == whichButton;
    }

    public BaseDialogBuild setDialogTitle(CharSequence title) {
        mShowTitle = true;
        mTitle = (String) title;
        return this;
    }

    public BaseDialogBuild setDialogTitle(int resId) {
        String title = mContext.getString(resId);
        return setDialogTitle(title);
    }

    public BaseDialogBuild setDialogMessage(int textResId) {
        String msg = mContext.getString(textResId);
        return setDialogMessage(msg);
    }

    public BaseDialogBuild setDialogMessage(CharSequence msg) {
        mHasMessage = true;
        mMessage = (String) msg;
        return this;
    }

    public BaseDialogBuild setDialogIcon(int drawableResId) {
        mIconResId = drawableResId;
        return this;
    }

    /**
     * @param whichButton 设置是哪个Button，可以是这其中的一个
     *                    {@link #BUTTON_POSITIVE},
     *                    {@link #BUTTON_NEUTRAL}, or
     *                    {@link #BUTTON_NEGATIVE}
     * @param text        按钮文字
     * @param listener    按钮点击监听事件
     */
    public BaseDialogBuild setButton(int whichButton, String text, OnDialogClickListener listener) {
        mButtonFlag |= whichButton;//加上一个按钮
        switch (whichButton) {
            case BUTTON_POSITIVE:
                mPositiveMessage = text;
                mPositiveListener = listener;
                break;
            case BUTTON_NEUTRAL:
                mNeutralMessage = text;
                mNeutralListener = listener;
                break;
            case BUTTON_NEGATIVE:
                mNegativeMessage = text;
                mNegativeListener = listener;
                break;
            default:
                throw new IllegalArgumentException("Button does not exist");
        }

        return this;
    }

    public BaseDialogBuild setButton(int whichButton, int textId, OnDialogClickListener listener) {
        return setButton(whichButton, mContext.getString(textId), listener);
    }

    /**
     * 设定“取消”类型按钮文字和事件
     */
    public BaseDialogBuild setNegativeButton(String text, OnDialogClickListener listener) {
        return setButton(BUTTON_NEGATIVE, text, listener);
    }

    /**
     * 设定“取消”类型按钮文字和事件
     */
    public BaseDialogBuild setNegativeButton(int textId, OnDialogClickListener listener) {
        String text = mContext.getString(textId);
        return setNegativeButton(text, listener);
    }

    /**
     * 设置“确定”类型按钮文字和监听事件
     */
    public BaseDialogBuild setPositiveButton(String text, OnDialogClickListener listener) {
        return setButton(BUTTON_POSITIVE, text, listener);
    }

    /**
     * 设置“确定”类型按钮文字和监听事件
     */
    public BaseDialogBuild setPositiveButton(int textId, OnDialogClickListener listener) {
        String text = mContext.getString(textId);
        return setPositiveButton(text, listener);
    }

    /**
     * 设定中性属性按钮（确定，取消类型以外的）类型文字和监听事件
     */
    public BaseDialogBuild setNeutralButton(String text, OnDialogClickListener listener) {
        return setButton(BUTTON_NEUTRAL, text, listener);
    }

    /**
     * 设定中性属性按钮（确定，取消类型以外的）类型文字和监听事件
     *
     * @param textId
     * @param listener
     */
    public BaseDialogBuild setNeutralButton(int textId, OnDialogClickListener listener) {
        String text = mContext.getString(textId);
        return setPositiveButton(text, listener);
    }

    public BaseDialogBuild setCustomView(int resId, Context context) {
        mCustomView = View.inflate(context, resId, null);
        return this;
    }

    public BaseDialogBuild setCustomView(View view) {
        mCustomView = view;
        return this;
    }

    public BaseDialogBuild setDialogCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        return this;
    }

    @Override
    public void show() {
        super.show();
    }

}