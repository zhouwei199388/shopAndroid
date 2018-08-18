package bluetoothdemo.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by ZouWei on 2018/6/5.
 */
public class BaseActivity  extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d(MyApplication.TAG,"run this "+MyApplication.Language);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, MyApplication.Language));
    }
}
