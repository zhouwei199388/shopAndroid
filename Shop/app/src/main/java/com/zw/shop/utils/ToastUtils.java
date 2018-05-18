package com.zw.shop.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ZouWei on 2018/5/9.
 */
public class ToastUtils {

    public static void showToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
