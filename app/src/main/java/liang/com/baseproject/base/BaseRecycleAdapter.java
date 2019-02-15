package liang.com.baseproject.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 创建日期：2018/12/24 on 11:06
 * 描述: 通用adapter
 * 作者: liangyang
 */
public class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

//    public static final int MAX_ITEM_COUNT = 20;

    protected LayoutInflater layoutInflater;

    protected List<T> dataList;

    protected int layoutId;

    public BaseRecycleAdapter(Context context, List<T> dataList, int layoutId) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.layoutId = layoutId;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(layoutId, parent, false);
        return new BaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder, dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
        //根据项目需求
//        return dataList != null ? Math.min(MAX_ITEM_COUNT, dataList.size()) : 0;
    }

    public void bindData(BaseViewHolder holder, T data) {

    }
}
