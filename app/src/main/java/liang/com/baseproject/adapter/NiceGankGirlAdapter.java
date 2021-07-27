package liang.com.baseproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.liang.module_core.utils.ToastUtil;
import com.liang.module_core.widget.ViewPagerPictureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import liang.com.baseproject.R;
import liang.com.baseproject.activity.SinglePictureActivity;
import liang.com.baseproject.entity.GankGirlRes;
import liang.com.baseproject.entity.GankRes;

/**
 * 创建日期:2021/7/23 on 2:56 PM
 * 描述: 枝干API-颜如玉(福利) api2
 * 作者: 杨亮
 */
public class NiceGankGirlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<GankGirlRes> mGankResList;

    public NiceGankGirlAdapter(Context context, List<GankGirlRes> mGankResList) {
        this.context = context;
        this.mGankResList = mGankResList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_nice_gank, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.bindItem(mGankResList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mGankResList != null ? mGankResList.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_nice_gank)
        ImageView ivNiceGank;
        @BindView(R.id.tv_nice_gank)
        TextView tvNiceGank;
        @BindView(R.id.tv_nice_source)
        TextView tvNiceSource;
        @BindView(R.id.card_nice_gank)
        CardView cardNiceGank;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(GankGirlRes gankRes) {
            tvNiceGank.setText(gankRes.getDesc());
            tvNiceSource.setText("来源: " + gankRes.getAuthor());
            Glide.with(context).load(gankRes.getUrl()).into(ivNiceGank);
//            ImageLoaderUtils.loadRadiusImage(context, true, ivNiceGank, gankRes.getUrl(),
//                    0, 0, 0);
            //点击图片
            ivNiceGank.setOnClickListener(v -> {
                //跳转到大图显示界面
//                SinglePictureActivity.actionStart(context, gankRes.getUrl(), gankRes.getDesc());
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(gankRes.getUrl());
                ViewPagerPictureActivity.actionStart(context, arrayList);
                ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            });

            //点击Card
            cardNiceGank.setOnClickListener(v -> {
                ToastUtil.onShowDefaultToast(context, "卡片");
            });
        }
    }
}
