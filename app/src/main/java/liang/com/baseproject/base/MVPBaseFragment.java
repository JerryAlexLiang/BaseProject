package liang.com.baseproject.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class MVPBaseFragment<V, T extends BasePresenter<V>> extends Fragment {

    protected T mPresenter;

    protected abstract T createPresenter();

    protected abstract int createViewLayoutId();

    protected void initView(View rootView) {
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
        ButterKnife.bind(this, rootView);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
