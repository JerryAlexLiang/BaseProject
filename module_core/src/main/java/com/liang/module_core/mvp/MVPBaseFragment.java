package com.liang.module_core.mvp;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.liang.module_core.R;
import com.liang.module_core.app.BaseApplication;
import com.liang.module_core.constant.Constant;
import com.liang.module_core.widget.refreshWidget.MyRefreshLottieHeader;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.header.DropBoxHeader;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.FunGameBattleCityHeader;
import com.scwang.smartrefresh.header.FunGameHitBlockHeader;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.liang.module_core.utils.SPUtils;
import com.liang.module_core.utils.ToastUtil;
import com.liang.module_core.widget.CustomProgressDialog;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;

import static com.liang.module_core.constant.Constant.FunGameHitBlockHeader;
import static com.liang.module_core.constant.Constant.StoreHouseHeader;


public abstract class MVPBaseFragment<V, T extends MVPBasePresenter<V>> extends Fragment {

    protected T mPresenter;
    private Unbinder unbinder;

    private boolean mIsRequestDataRefresh = false;
    private SwipeRefreshLayout mRefreshLayout;
    private CustomProgressDialog customProgressDialog;
    private String lottieFileName = "";
    private MyRefreshLottieHeader myRefreshLottieHeader;

    protected abstract T createPresenter();

    protected abstract int createViewLayoutId();

    protected abstract void initView(View rootView);

    public Boolean isSetRefresh() {
        return true;
    }

    /**
     * 是否注册事件分发，默认不绑定
     */
//    protected boolean isRegisterEventBus() {
//        return false;
//    }
    protected abstract boolean isRegisterEventBus();

    protected abstract boolean isSetRefreshHeader();

    protected abstract boolean isSetRefreshFooter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //允许为空，不是所有都要实现MVP模式
        if (createPresenter() != null) {
            mPresenter = createPresenter();
            mPresenter.attachView((V) this);
        }
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(createViewLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView(rootView);
        if (isSetRefresh()) {
            setupSwipeRefresh(rootView);
        }

        //初始化Header
        myRefreshLottieHeader = new MyRefreshLottieHeader(BaseApplication.getAppContext());

        SmartRefreshLayout smartRefreshLayout = rootView.findViewById(R.id.smart_refresh_layout);
        if (smartRefreshLayout != null) {
            getSmartRefreshPrimaryColorsTheme(smartRefreshLayout, isSetRefreshHeader(), isSetRefreshFooter());
        }


        return rootView;
    }

    private void setupSwipeRefresh(View view) {
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        if (mRefreshLayout != null) {
            mRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
                    R.color.refresh_progress_2, R.color.refresh_progress_3);
            mRefreshLayout.setProgressViewOffset(true, 0, (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
            mRefreshLayout.setOnRefreshListener(this::requestDataRefresh);
        }
    }

    public void setRefresh(boolean requestDataRefresh) {
        if (mRefreshLayout == null) {
            return;
        }
        if (!requestDataRefresh) {
            mIsRequestDataRefresh = false;
            mRefreshLayout.postDelayed(() -> {
                if (mRefreshLayout != null) {
                    mRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        } else {
            mRefreshLayout.setRefreshing(true);
        }
    }

    public void requestDataRefresh() {
        mIsRequestDataRefresh = true;
    }

    public void getSmartRefreshPrimaryColorsTheme(SmartRefreshLayout smartRefreshLayout, boolean isSetRefreshHeader, boolean isSetRefreshFooter) {
        int actionBarColorInt = (int) SPUtils.get(BaseApplication.getAppContext(), Constant.ACTIONBAR_COLOR_THEME, 0);

        String refreshHeaderStyle = (String) SPUtils.get(BaseApplication.getAppContext(), Constant.REFRESH_HEADER_STYLE, Constant.REFRESH_HEADER_34115_ROCKET_LUNCH);

        if (smartRefreshLayout != null) {

            if (isSetRefreshHeader) {
                setRefreshHeader(smartRefreshLayout, refreshHeaderStyle);
            }

            if (isSetRefreshFooter) {
                //上滑加载更多三点渐变动画底部View
//                smartRefreshLayout.setRefreshFooter(new BallPulseFooter(MyApplication.getAppContext()).setSpinnerStyle(SpinnerStyle.Scale));
                smartRefreshLayout.setRefreshFooter(new ClassicsFooter(BaseApplication.getAppContext()));
            }
        }
        switch (actionBarColorInt) {
            case Constant.ACTIONBAR_COLOR_BLUE:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.colorBlue, R.color.white);
                }
                break;

            case Constant.ACTIONBAR_COLOR_RED:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.accent, R.color.white);
                }
                break;

            case Constant.ACTIONBAR_COLOR_BLACK:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.black, R.color.white);
                }
                break;

            case Constant.ACTIONBAR_COLOR_WHITE:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.black, R.color.white);
                }
                break;

            case Constant.ACTIONBAR_COLOR_TRANSLATE:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.transparent, R.color.white);
                }
                break;

            case Constant.ACTIONBAR_COLOR_GREEN:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.assist, R.color.white);
                }
                break;
        }
    }

    /**
     * 替换RefreshHeader
     */
    public void setRefreshHeader(SmartRefreshLayout smartRefreshLayout, String refreshHeaderStyle) {
        switch (refreshHeaderStyle) {
            case Constant.REFRESH_HEADER_34115_ROCKET_LUNCH:
                lottieFileName = "lottie/34115-rocket-lunch.json";
                myRefreshLottieHeader.setAnimationViewJson(lottieFileName);
                setLottieRefreshHeader(smartRefreshLayout);
                break;

            case Constant.REFRESH_HEADER_28402_TEMPLO:
                lottieFileName = "lottie/28402-templo.json";
                myRefreshLottieHeader.setAnimationViewJson(lottieFileName);
                setLottieRefreshHeader(smartRefreshLayout);
                break;

            case Constant.MaterialHeader:
                smartRefreshLayout.setRefreshHeader(new MaterialHeader(BaseApplication.getAppContext()));
                break;

            case Constant.WaterDropHeader:
                smartRefreshLayout.setRefreshHeader(new WaterDropHeader(BaseApplication.getAppContext()));
                break;

            case Constant.WaveSwipeHeader:
                smartRefreshLayout.setRefreshHeader(new WaveSwipeHeader(BaseApplication.getAppContext()));
                break;

            case Constant.BezierCircleHeader:
                smartRefreshLayout.setRefreshHeader(new BezierCircleHeader(BaseApplication.getAppContext()));
                break;

            case Constant.PhoenixHeader:
                smartRefreshLayout.setRefreshHeader(new PhoenixHeader(BaseApplication.getAppContext()));
                break;

            case Constant.TaurusHeader:
                smartRefreshLayout.setRefreshHeader(new TaurusHeader(BaseApplication.getAppContext()));
                break;

            case Constant.FlyRefreshHeader:
                smartRefreshLayout.setRefreshHeader(new FlyRefreshHeader(BaseApplication.getAppContext()));
                break;

            case Constant.ClassicsHeader:
                smartRefreshLayout.setRefreshHeader(new ClassicsHeader(BaseApplication.getAppContext()));
                break;

            case Constant.BezierRadarHeader:
                smartRefreshLayout.setRefreshHeader(new BezierRadarHeader(BaseApplication.getAppContext()));
                break;

            case Constant.DeliveryHeader:
                smartRefreshLayout.setRefreshHeader(new DeliveryHeader(BaseApplication.getAppContext()));
                break;

            case Constant.DropBoxHeader:
                smartRefreshLayout.setRefreshHeader(new DropBoxHeader(BaseApplication.getAppContext()));
                break;

            case Constant.FunGameBattleCityHeader:
                smartRefreshLayout.setRefreshHeader(new FunGameBattleCityHeader(BaseApplication.getAppContext()));
                break;

            case Constant.FunGameHitBlockHeader:
                smartRefreshLayout.setRefreshHeader(new FunGameHitBlockHeader(BaseApplication.getAppContext()));
                break;

            case Constant.StoreHouseHeader:
                smartRefreshLayout.setRefreshHeader(new StoreHouseHeader(BaseApplication.getAppContext()));
                break;

            case Constant.TwoLevelHeader:
                smartRefreshLayout.setRefreshHeader(new TwoLevelHeader(BaseApplication.getAppContext()));
                break;
        }
    }

    /**
     * 设置自定义RefreshHeader
     */
    private void setLottieRefreshHeader(SmartRefreshLayout smartRefreshLayout) {
        smartRefreshLayout.setHeaderMaxDragRate(2);
        smartRefreshLayout.setRefreshHeader(myRefreshLottieHeader);
    }

    /**
     * 全局替换RefreshHeader
     */
    public void changeGlobalRefreshHeaderStyle(String refreshHeaderStyle) {
        switch (refreshHeaderStyle) {
            case Constant.REFRESH_HEADER_34115_ROCKET_LUNCH:
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.REFRESH_HEADER_34115_ROCKET_LUNCH);
                break;

            case Constant.REFRESH_HEADER_28402_TEMPLO:
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.REFRESH_HEADER_28402_TEMPLO);
                break;

            case Constant.MaterialHeader:
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.MaterialHeader);
                break;

            case Constant.WaterDropHeader:
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.WaterDropHeader);
                break;

            case Constant.WaveSwipeHeader:
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.WaveSwipeHeader);
                break;

            case Constant.BezierCircleHeader:
                //水滴下拉
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.BezierCircleHeader);
                break;

            case Constant.PhoenixHeader:
                //大楼动画
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.PhoenixHeader);
                break;

            case Constant.TaurusHeader:
                //飞机滑翔动画效果
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.TaurusHeader);
                break;

            case Constant.FlyRefreshHeader:
                //飞机效果2
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.FlyRefreshHeader);
                break;

            case Constant.ClassicsHeader:
                //经典带时间的刷新
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.ClassicsHeader);
                break;

            case Constant.BezierRadarHeader:
                //雷达动画
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.BezierRadarHeader);
                break;

            case Constant.DeliveryHeader:
                //快递交付动画
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.DeliveryHeader);
                break;

            case Constant.DropBoxHeader:
                //礼物盒子动画效果
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.DropBoxHeader);
                break;

            case Constant.FalsifyHeader:
                //无动画效果
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.FalsifyHeader);
                break;

            case Constant.FunGameBattleCityHeader:
                //子弹游戏效果
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.FunGameBattleCityHeader);
                break;

            case Constant.FunGameHitBlockHeader:
                //碰球游戏效果
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.FunGameHitBlockHeader);
                break;

            case Constant.StoreHouseHeader:
                //StoreHouse文字渐变效果
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.StoreHouseHeader);
                break;


            case Constant.TwoLevelHeader:
                //二楼
                SPUtils.put(getContext(), Constant.REFRESH_HEADER_STYLE, Constant.TwoLevelHeader);
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    public void showProgressDialog(String content, boolean cancelable) {
        if (customProgressDialog == null) {
            customProgressDialog = new CustomProgressDialog(getActivity(), content, cancelable);
        }
        if (!customProgressDialog.isShow()) {
            customProgressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (customProgressDialog != null && customProgressDialog.isShow()) {
            customProgressDialog.dismiss();
        }
    }

    public void onShowToast(String content) {
        ToastUtil.setCustomToast(BaseApplication.getAppContext(), BitmapFactory.decodeResource(getResources(), R.drawable.core_icon_true),
                false, content, getResources().getColor(R.color.toast_bg), getResources().getColor(R.color.text_invert), Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    public void onShowTrueToast(String content) {
        ToastUtil.setCustomToast(BaseApplication.getAppContext(), BitmapFactory.decodeResource(getResources(), R.drawable.core_icon_true),
                true, content, getResources().getColor(R.color.toast_bg), getResources().getColor(R.color.text_invert), Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    public void onShowErrorToast(String content) {
        ToastUtil.setCustomToast(BaseApplication.getAppContext(), BitmapFactory.decodeResource(getResources(), R.drawable.core_icon_wrong),
                true, content, getResources().getColor(R.color.toast_bg), getResources().getColor(R.color.text_invert), Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    public void finishPage() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
