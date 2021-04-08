package liang.com.baseproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.liang.module_core.constant.Constant;
import com.liang.module_core.jetpack.JetPackActivity;
import com.liang.module_core.mvp.MVPBasePresenter;
import com.liang.module_core.widget.SearchEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.adapter.RefreshHeaderChangeRvAdapter;
import liang.com.baseproject.entity.RefreshHeaderBean;

/**
 * 创建日期:2021/4/8 on 5:54 PM
 * 描述: 设置RefreshHeader
 * 作者: 杨亮
 */
public class RefreshHeaderChangeActivity extends JetPackActivity {

    @BindView(R.id.base_actionbar_left_icon)
    ImageView baseActionbarLeftIcon;
    @BindView(R.id.base_actionbar_left_tv)
    TextView baseActionbarLeftTv;
    @BindView(R.id.base_actionbar_left2_icon)
    ImageView baseActionbarLeft2Icon;
    @BindView(R.id.base_actionbar_rl_left_view)
    RelativeLayout baseActionbarRlLeftView;
    @BindView(R.id.base_actionbar_title)
    TextView baseActionbarTitle;
    @BindView(R.id.edit_search_view)
    SearchEditText editSearchView;
    @BindView(R.id.base_actionbar_right_tv)
    TextView baseActionbarRightTv;
    @BindView(R.id.base_actionbar_right_icon)
    ImageView baseActionbarRightIcon;
    @BindView(R.id.base_actionbar_rl_right_view)
    RelativeLayout baseActionbarRlRightView;
    @BindView(R.id.base_actionbar)
    FrameLayout baseActionbar;
    @BindView(R.id.rvRefreshHeader)
    RecyclerView rvRefreshHeader;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    private List<RefreshHeaderBean> list;
    private RefreshHeaderChangeRvAdapter refreshHeaderChangeRvAdapter;

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
        return R.layout.activity_refresh_header_change;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RefreshHeaderChangeActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intView();
        initData();
        initListener();
    }

    private void initListener() {
        if (refreshHeaderChangeRvAdapter != null && refreshHeaderChangeRvAdapter.getData() != null) {
            refreshHeaderChangeRvAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    RefreshHeaderBean refreshHeaderBean = refreshHeaderChangeRvAdapter.getData().get(position);
                    switch (refreshHeaderBean.getId()) {
                        case 0:
                            //火箭
                            setRefreshHeader(smartRefreshLayout,Constant.REFRESH_HEADER_34115_ROCKET_LUNCH);
                            changeRefreshHeaderStyle(Constant.REFRESH_HEADER_34115_ROCKET_LUNCH);
                            break;

                        case 1:
                            //太阳
                            setRefreshHeader(smartRefreshLayout,Constant.REFRESH_HEADER_28402_TEMPLO);
                            changeRefreshHeaderStyle(Constant.REFRESH_HEADER_28402_TEMPLO);
                            break;

                        default:
                            break;
                    }
                    smartRefreshLayout.autoRefresh();
                }
            });
        }

    }

    private void initData() {
        list = new ArrayList<>();

        refreshHeaderChangeRvAdapter = new RefreshHeaderChangeRvAdapter();
        rvRefreshHeader.setAdapter(refreshHeaderChangeRvAdapter);

        RefreshHeaderBean bean1 = new RefreshHeaderBean(0, R.drawable.rocket_lunch_34115, "火箭");
        RefreshHeaderBean bean2 = new RefreshHeaderBean(1, R.drawable.templo_28402, "太阳");

        list.clear();
        list.add(bean1);
        list.add(bean2);

        refreshHeaderChangeRvAdapter.addData(list);

        //myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                switch (refreshHeaderBean.getId()){
        //                    case 0:
        //
        //                        break;
        //
        //                    case 1:
        //
        //                        break;
        //
        //                    default:
        //                        break;
        //                }
        //            }
        //        });

    }

    private void intView() {
        baseActionbarLeftIcon.setVisibility(View.VISIBLE);
        baseActionbarTitle.setVisibility(View.VISIBLE);
        baseActionbarTitle.setText(getResources().getString(R.string.refresh_header_change));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rvRefreshHeader.setLayoutManager(gridLayoutManager);


    }

    @OnClick(R.id.base_actionbar_left_icon)
    public void onClick() {
        finish();
    }
}