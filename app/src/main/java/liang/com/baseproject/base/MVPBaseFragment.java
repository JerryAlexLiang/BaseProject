package liang.com.baseproject.base;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import liang.com.baseproject.R;
import liang.com.baseproject.app.MyApplication;
import liang.com.baseproject.utils.SPUtils;
import liang.com.baseproject.utils.ToastUtil;
import liang.com.baseproject.widget.CustomProgressDialog;

import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_BLACK;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_BLUE;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_GREEN;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_RED;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_THEME;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_TRANSLATE;
import static liang.com.baseproject.Constant.Constant.ACTIONBAR_COLOR_WHITE;

public abstract class MVPBaseFragment<V, T extends MVPBasePresenter<V>> extends Fragment {

    protected T mPresenter;
    private Unbinder unbinder;

    private boolean mIsRequestDataRefresh = false;
    private SwipeRefreshLayout mRefreshLayout;
    private CustomProgressDialog customProgressDialog;

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
        int actionBarColorInt = (int) SPUtils.get(MyApplication.getAppContext(), ACTIONBAR_COLOR_THEME, 0);

        if (smartRefreshLayout != null) {
            if (isSetRefreshHeader) {
                //下拉刷新沉浸式水滴头部View
                smartRefreshLayout.setRefreshHeader(new BezierCircleHeader(MyApplication.getAppContext()));
            }
            if (isSetRefreshFooter) {
                //上滑加载更多三点渐变动画底部View
                smartRefreshLayout.setRefreshFooter(new BallPulseFooter(MyApplication.getAppContext()).setSpinnerStyle(SpinnerStyle.Scale));
            }
        }
        switch (actionBarColorInt) {
            case ACTIONBAR_COLOR_BLUE:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.colorBlue, R.color.white);
                }
                break;

            case ACTIONBAR_COLOR_RED:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.red, R.color.white);
                }
                break;

            case ACTIONBAR_COLOR_BLACK:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.black, R.color.white);
                }
                break;

            case ACTIONBAR_COLOR_WHITE:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.black, R.color.white);
                }
                break;

            case ACTIONBAR_COLOR_TRANSLATE:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.translate, R.color.white);
                }
                break;

            case ACTIONBAR_COLOR_GREEN:
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setPrimaryColorsId(R.color.palegreen, R.color.white);
                }
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

    public void onShowToast(String content){
        ToastUtil.setCustomToast(MyApplication.getAppContext(), BitmapFactory.decodeResource(getResources(), R.drawable.icon_true),
                true, content, getResources().getColor(R.color.toast_bg), Color.WHITE, Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    public void finishPage() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
