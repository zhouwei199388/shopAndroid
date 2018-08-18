package bluetoothdemo.myapplication.Bluetooth;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import bluetoothdemo.myapplication.BaseActivity;
import bluetoothdemo.myapplication.MyApplication;
import bluetoothdemo.myapplication.MyContextWrapper;
import bluetoothdemo.myapplication.R;

/**
 * Created by ZouWei on 2018/6/2.
 */
public class BluetoothActivity extends BaseActivity {

    private TextView mDirectionTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        mDirectionTv = findViewById(R.id.tv_direction);
        mDirectionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDirectionTv.setSelected(true);
            }
        });
    }
}
