package bluetoothdemo.myapplication.Bluetooth;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bluetoothdemo.myapplication.Bluetooth.callback.OnItemClickListener;
import bluetoothdemo.myapplication.R;

/**
 * Created by ZouWei on 2018/5/31.
 */
public class BlueAdapter extends BaseListAdapter {

    private List<BluetoothDevice> mDevices;

    private OnItemClickListener onItemClickListener;
    /**
     * 一次添加多条
     *
     * @param devices
     */
    public void setDate(List<BluetoothDevice> devices) {
        if (mDevices == null) {
            mDevices = devices;
        } else {
            mDevices.addAll(devices);
        }
    }

    /**
     * 清除devices
     */
    public void clearBlueDevices() {
        if (mDevices != null)
            mDevices.clear();
    }

    /**
     * 添加数据
     *
     * @param device
     */
    public void addDate(BluetoothDevice device) {
        if (mDevices == null) {
            mDevices = new ArrayList<>();
        }
        mDevices.add(device);
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }


    @Override
    protected int getDataCount() {
        return mDevices == null ? 0 : mDevices.size();
    }

    @Override
    protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseViewHolder {
        private TextView mBlueNameTv;


        public ViewHolder(View itemView) {
            super(itemView);
            mBlueNameTv = itemView.findViewById(R.id.blueName_tv);
        }

        @Override
        public void onBindViewHolder(int position) {
            String name = mDevices.get(position).getName()+
                    mDevices.get(position).getAddress();
            mBlueNameTv.setText(name);
        }

        @Override
        public void onBingViewHolder(int position, List<Object> payloads) {
        }

        @Override
        public void onItemClick(View view, int position) {
            if(onItemClickListener!=null){
                onItemClickListener.connect(mDevices.get(position).getAddress());
            }
        }
    }
}
