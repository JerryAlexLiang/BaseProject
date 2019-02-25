package liang.com.baseproject.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import liang.com.baseproject.R;

public abstract class MVPBaseFragment<V, T extends BasePresenter<V>> extends Fragment {

    protected T mPresenter;
    private Unbinder unbinder;

    private boolean mIsRequestDataRefresh = false;
    private SwipeRefreshLayout mRefreshLayout;

    protected abstract T createPresenter();

    protected abstract int createViewLayoutId();

    protected abstract void initView(View rootView);

    public Boolean isSetRefresh() {
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
