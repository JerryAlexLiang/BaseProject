package com.yl.recyclerview.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.yl.recyclerview.R;
import com.yl.recyclerview.bean.VideoBeanRes;
import com.yl.recyclerview.retrofit.BaseObserver;
import com.yl.recyclerview.retrofit.RetrofitHelper;
import com.yl.recyclerview.retrofit.UrlConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoadMoreNetActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private int page = 1;
    private List<VideoBeanRes.ResultsBean> resultsBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more_net);
        ButterKnife.bind(this);
        // 使用Toolbar替换ActionBar
        setSupportActionBar(toolbar);
        initView();
        //获取数据
        getData();
    }

    private void getData() {
        RetrofitHelper
                .getInstance(UrlConstants.GANK_BASE_URL)
                .getMyService()
                .getVideoData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<VideoBeanRes>() {
                    @Override
                    public void onNext(VideoBeanRes videoBeanRes) {
                        super.onNext(videoBeanRes);
                        resultsBeanList.addAll(videoBeanRes.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Toast.makeText(LoadMoreNetActivity.this, "数据错误", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initView() {
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));
        //初始化适配器

    }
}
