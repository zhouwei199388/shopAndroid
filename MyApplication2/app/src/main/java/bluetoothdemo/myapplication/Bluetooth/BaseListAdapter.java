package bluetoothdemo.myapplication.Bluetooth;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ZouWei on 2018/5/31.
 */
public abstract class BaseListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateNormalViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
        if(payloads.isEmpty()){
            holder.onBindViewHolder(position);
        }else{
            holder.onBingViewHolder(position,payloads);
        }
    }
    @Override
    public int getItemCount() {
        return getDataCount();
    }

    protected abstract int getDataCount();

    protected abstract BaseViewHolder onCreateNormalViewHolder(ViewGroup parent,int viewType);

}
