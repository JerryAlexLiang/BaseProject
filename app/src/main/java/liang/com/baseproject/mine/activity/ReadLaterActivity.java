package liang.com.baseproject.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.helperDao.ReadLaterBeanDaoHelpter;
import liang.com.baseproject.main.activity.AgentWebActivity;
import liang.com.baseproject.mine.adapter.ReadLaterAdapter;
import liang.com.baseproject.mine.entity.ReadLaterBean;
import liang.com.baseproject.utils.CopyUtils;
import liang.com.baseproject.utils.GsonUtils;
import liang.com.baseproject.utils.IntentUtils;
import liang.com.baseproject.utils.LogUtil;
import liang.com.baseproject.utils.ToastUtil;

public class ReadLaterActivity extends MVPBaseActivity {

    private static final String TAG = ReadLaterActivity.class.getSimpleName();
    @BindView(R.id.base_actionbar_left_icon)
    ImageView baseActionbarLeftIcon;
    @BindView(R.id.base_actionbar_left_tv)
    TextView baseActionbarLeftTv;
    @BindView(R.id.base_actionbar_left2_icon)
    ImageView baseActionbarLeft2Icon;
    @BindView(R.id.base_actionbar_title)
    TextView baseActionbarTitle;
    @BindView(R.id.base_actionbar_right_tv)
    TextView baseActionbarRightTv;
    @BindView(R.id.base_actionbar_right_icon)
    ImageView baseActionbarRightIcon;
    @BindView(R.id.base_actionbar)
    FrameLayout baseActionbar;
    @BindView(R.id.rv_read_later)
    RecyclerView rvReadLater;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.iv_empty)
    ImageView ivWebViewError;
    @BindView(R.id.rl_empty_container)
    RelativeLayout rlEmptyContainer;
    private ReadLaterAdapter readLaterAdapter;

    private static final int PAGE_START = 0;
    private int currPage = PAGE_START;
    private int perPageCount = 5;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ReadLaterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected boolean isSetRefreshHeader() {
        return true;
    }

    @Override
    protected boolean isSetRefreshFooter() {
        return false;
    }

    @Override
    protected MVPBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_read_later;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_read_later);

        initView();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvReadLater.setLayoutManager(linearLayoutManager);
        //初始化适配器
        readLaterAdapter = new ReadLaterAdapter();
        //开启动画
        readLaterAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //绑定适配器
        rvReadLater.setAdapter(readLaterAdapter);

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //一次查询所有数据
                getArticleList();
            }
        });

        readLaterAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                //一次查询所有数据
                getArticleList();
            }
        }, rvReadLater);

        //自动刷新(替代第一次请求数据)
        smartRefreshLayout.autoRefresh();

        if (readLaterAdapter.getData().size() > 0) {
            TextView textView = new TextView(this);
            //此处相当于布局文件中的Android:layout_gravity属性
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 10, 10, 10);//4个参数按顺序分别是左上右下mView.setLayoutParams(layoutParams);
            textView.setLayoutParams(layoutParams);
            textView.setText("我是有底线的");
            //此处相当于布局文件中的Android：gravity属性
            textView.setGravity(Gravity.CENTER);
            readLaterAdapter.addFooterView(textView);
        }

        readLaterAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                readLaterAdapter.closeAll(null);

                ReadLaterBean item = readLaterAdapter.getItem(position);
                if (item == null) {
                    return;
                }

                switch (view.getId()) {
                    case R.id.rl_page_container:
                        AgentWebActivity.actionStart(ReadLaterActivity.this, item.getTitle(), item.getLink());
                        break;

                    case R.id.tv_delete:
                        //本地数据库删除操作
                        ReadLaterBeanDaoHelpter.removeReaderLaterBean(item);
                        //UI
                        readLaterAdapter.remove(position);
                        onShowToast("删除成功");
                        List<ReadLaterBean> allReadLaters = ReadLaterBeanDaoHelpter.findAllReadLaters();
                        if (allReadLaters.isEmpty()) {
                            rlEmptyContainer.setVisibility(View.VISIBLE);
                        } else {
                            rlEmptyContainer.setVisibility(View.GONE);
                        }
                        break;

                    case R.id.tv_edit:
                        ToastUtil.showShortToast("编辑");
                        break;

                    case R.id.tv_open_browser:
                        if (TextUtils.isEmpty(item.getLink())) {
                            onShowToast("链接为空");
                            break;
                        }
                        IntentUtils.openBrowser(ReadLaterActivity.this, item.getLink());
//                        if (getActivity()!=null){
//                            IntentUtils.openBrowser(getActivity(), item.getLink());
//                        }
                        break;

                    case R.id.tv_copy:
                        CopyUtils.copyText(item.getLink());
                        onShowToast("复制成功");
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void initView() {
        baseActionbarLeftIcon.setVisibility(View.VISIBLE);
        baseActionbarTitle.setVisibility(View.VISIBLE);
        baseActionbarTitle.setText("稍后阅读");
    }

    @OnClick({R.id.base_actionbar_left_icon, R.id.rl_empty_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.base_actionbar_left_icon:
                finish();
                break;

            case R.id.rl_empty_container:
                //一次查询所有数据
                getArticleList();
                break;
        }

    }


    public void getArticleList() {
        //查询所有
        List<ReadLaterBean> allReadLaters = ReadLaterBeanDaoHelpter.findAllReadLaters();
        readLaterAdapter.setNewData(allReadLaters);
        if (allReadLaters.isEmpty()) {
            rlEmptyContainer.setVisibility(View.VISIBLE);
            onShowToast("没有数据~");
        } else {
            rlEmptyContainer.setVisibility(View.GONE);
        }

        LogUtil.d(TAG, "稍后阅读文章列表:  " + GsonUtils.toJson(allReadLaters));

        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();

        readLaterAdapter.loadMoreEnd();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //一次查询所有数据
        getArticleList();
    }
}
