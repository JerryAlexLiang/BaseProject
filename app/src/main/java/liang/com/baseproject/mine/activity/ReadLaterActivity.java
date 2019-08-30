package liang.com.baseproject.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import liang.com.baseproject.R;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.base.MVPBasePresenter;

public class ReadLaterActivity extends MVPBaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ReadLaterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean isRegisterEventBus() {
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
    }
}
