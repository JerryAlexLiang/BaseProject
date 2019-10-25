package liang.com.baseproject.testlaboratory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import liang.com.baseproject.R;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.base.MVPBasePresenter;

public class FiltrateActivity extends MVPBaseActivity {

    public static void actionStart(Context context){
        Intent intent = new Intent(context,FiltrateActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected boolean isSetRefreshHeader() {
        return false;
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
        return R.layout.activity_filtrate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
