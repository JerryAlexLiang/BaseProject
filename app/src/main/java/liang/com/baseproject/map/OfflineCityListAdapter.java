package liang.com.baseproject.map;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.offlinemap.OfflineMapCity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import liang.com.baseproject.R;

public class OfflineCityListAdapter extends RecyclerView.Adapter<OfflineCityListAdapter.MyViewHolder> implements View.OnClickListener {

    private List<OfflineMapCity> dataList;
    private OnItemClickListener mOnItemClickListener = null;

    /**
     * 响应点击事件
     */
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (OfflineMapCity) v.getTag());
        }
    }

    /**
     * 定义点击事件的接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, OfflineMapCity dataBean);
    }

    /**
     * 设置listener事件并初始化
     */
    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public OfflineCityListAdapter(List<OfflineMapCity> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_city_list, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.tvCityName.setText(dataList.get(position).getCity());
        //将city保存在itemView的tag中，以便点击时进行获取
        myViewHolder.itemView.setTag(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_city_name)
        TextView tvCityName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
