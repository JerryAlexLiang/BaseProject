package liang.com.baseproject.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.Unbinder;
import liang.com.baseproject.R;
import liang.com.baseproject.View.ZhihuView;
import liang.com.baseproject.base.MVPBaseFragment;
import liang.com.baseproject.utils.NetUtil;

/**
 * 创建日期：2019/3/7 on 13:23
 * 描述: 知乎日报  MVPBaseFragment<NiceGankView, NiceGankPresenter> implements NiceGankView
 * MVPBaseFragment<IZhihuFgView, ZhihuFgPresenter> implements IZhihuFgView
 * 作者: liangyang
 */
public class ZhiHuContainerFragment extends Fragment {

    @BindView(R.id.recycler_view_zhihu)
    RecyclerView recyclerViewZhihu;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager;

    public ZhiHuContainerFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three,null);
        return view;
    }
}
