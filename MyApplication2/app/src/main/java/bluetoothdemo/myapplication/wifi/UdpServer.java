package bluetoothdemo.myapplication.wifi;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by ZouWei on 2018/5/23.
 */
public class UdpServer implements Runnable {
    private static final int PORT = 6000;
    private byte[] msg = new byte[2048];
    private boolean life = true;
    private Handler mHandler;
    private String mIp;

    public UdpServer(Handler handler) {
        this.mHandler = handler;
    }

    public boolean isLife() {
        return life;
    }

    public void setLife(boolean life) {
        this.life = life;
    }


    public void setIp(String ip) {
        this.mIp = ip;
    }

    @Override
    public void run() {
        DatagramSocket dSocket = null;
        DatagramPacket dPacket = new DatagramPacket(msg, msg.length);
        try {
            dSocket = new DatagramSocket(PORT);
            while (life) {
                try {
                    dSocket.receive(dPacket);
                    InetAddress address = dPacket.getAddress();
                    Log.d("ceshi", address.getHostAddress());
                    Log.d("port1", dSocket.getLocalPort()+"");
                    Log.d("tihsIP", mIp);
                    if(!mIp.equals(address.getHostAddress())){
                        Log.d("tian msg sever received",
                                new String(dPacket.getData(), dPacket.getOffset(),
                                        dPacket.getLength())
                                        + "dPacket.getLength()="
                                        + dPacket.getLength());
                    }
//                    Message message = new Message();
//                    message.what = 1;
//                    message.obj = new String(dPacket.getData());
//                    mHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
