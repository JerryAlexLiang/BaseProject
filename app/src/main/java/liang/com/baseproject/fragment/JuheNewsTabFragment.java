package liang.com.baseproject.fragment;


import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import liang.com.baseproject.Constant.Constant;
import liang.com.baseproject.R;
import liang.com.baseproject.View.NewsView;
import liang.com.baseproject.adapter.JuheNewsAdapter;
import liang.com.baseproject.entity.NewsRes;
import liang.com.baseproject.interactor.NewsInteractor;
import liang.com.baseproject.presenter.NewsPresenter;
import liang.com.baseproject.retrofit.RetrofitHelper;
import liang.com.baseproject.retrofit.UrlConstants;
import liang.com.baseproject.utils.ToastUtil;
import liang.com.baseproject.widget.CustomProgressDialog;

/**
 * 创建日期：2019/2/13 on 10:25
 * 描述: 国际新闻-测试RecyclerView 通用适配器1- BaseRecycleAdapter
 * 作者: liangyang
 */
public class JuheNewsTabFragment extends Fragment implements NewsView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private CustomProgressDialog progressDialog;
    private NewsPresenter presenter;

    public JuheNewsTabFragment() {
        // Required empty public constructor
    }

    public static JuheNewsTabFragment newInstance(Bundle args) {
        JuheNewsTabFragment fragment = new JuheNewsTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_juhe_news_tab, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initModule();
        getData();
        initView();
    }

    private void initModule() {
        presenter = new NewsPresenter(this, new NewsInteractor());
    }

    private void getData() {
        if (getArguments() != null) {
            String newsKey = getArguments().getString("newsKey");
            presenter.getNewsData(UrlConstants.NEWS_URL, newsKey, Constant.NEWS_APPKEY);
//            presenter.getNewsData(UrlConstants.NEWS_URL, "guoji", Constant.NEWS_APPKEY);
        }
    }

    private void initView() {
        //初始化CustomProgressDialog
        progressDialog = new CustomProgressDialog(getContext(), getString(R.string.loading));
        //初始化RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        //改变SwipeRefreshLayout加载显示的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);
        //改变初始时的大小
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        //设置监听
        swipeRefreshLayout.setOnRefreshListener(this);
        //设置向下拉多少出现刷新
        swipeRefreshLayout.setDistanceToTriggerSync(100);
        //设置刷新出现的位置
        swipeRefreshLayout.setProgressViewEndTarget(false, 100);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        RetrofitHelper.deleteInstance();
    }

    @Override
    public void showProgress() {
        if (progressDialog != null && !progressDialog.isShow()) {
            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShow()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void setToastShow(String str) {
        ToastUtil.setCustomToast(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.icon_true),
                true, str, Color.WHITE, Color.BLACK, Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    @Override
    public void getNewsSuccess(List<NewsRes.ResultBean.DataBean> data) {
        //适配器
        JuheNewsAdapter adapter = new JuheNewsAdapter(getContext(), data);
        recyclerView.setAdapter(adapter);
    }

    /**
     * SwipeRefreshLayout加载监听
     */
    @Override
    public void onRefresh() {
        //读取数据
        getData();
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
        setToastShow("刷新成功~");
    }

}
