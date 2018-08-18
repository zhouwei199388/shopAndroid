package bluetoothdemo.myapplication.wifi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bluetoothdemo.myapplication.R;

/**
 * Created by ZouWei on 2018/5/23.
 */
public class TestActivity extends AppCompatActivity {
    private EditText msg_et;
    private Button send_bt;
    private TextView info_tv;
    private static final String TAG = "MainAct";
    private UDPClient client;
    private String sendInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tes);
        msg_et = findViewById(R.id.edit_msg);
        send_bt = findViewById(R.id.sent_bt);
        info_tv = findViewById(R.id.receive_msg);
        info_tv.setText("source");
        // 开启服务器
        ExecutorService exec = Executors.newCachedThreadPool();
        UdpServer server = new UdpServer(mHandler);
        server.setIp(WifiHelper.getWifiIP(this));
        exec.execute(server);
        // 发送消息

        send_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myThread1 thread = new myThread1("22");
                new Thread(thread).start();

            }
        });
    }

    final Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            //super.handleMessage(msg);
            info_tv.setText(sendInfo);

            Log.d(TAG, "client.send()=");
        }
    };

    class myThread1 implements Runnable {
        private String threadName;

        public myThread1(String name) {
            this.threadName = name;
        }
        public void run() {
            Log.d(TAG, "MyThread  execu" + msg_et.getText().toString());
            client = new UDPClient();
            Log.d(TAG, WifiHelper.getWifiIP(getApplicationContext()));
            byte[] bytes = new byte[]{104, 115, 1, 0, 1, 0, 2, 0, 115, 104};
            sendInfo = client.send(WifiHelper.getWifiIP(getApplicationContext()), bytes);

            Message msg = mHandler.obtainMessage();
            msg.arg1 = 1;
            mHandler.sendMessage(msg);
            Log.d(TAG, "client.send()=");
        }
    }
}
