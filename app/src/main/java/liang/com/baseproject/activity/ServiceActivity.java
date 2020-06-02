package liang.com.baseproject.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import liang.com.baseproject.R;
import liang.com.baseproject.base.MVPBaseActivity;
import liang.com.baseproject.base.MVPBasePresenter;
import liang.com.baseproject.service.MyService;
import liang.com.baseproject.widget.SearchEditText;

public class ServiceActivity extends MVPBaseActivity {

    @BindView(R.id.start_service)
    Button startService;
    @BindView(R.id.stop_service)
    Button stopService;
    @BindView(R.id.base_actionbar_left_icon)
    ImageView baseActionbarLeftIcon;
    @BindView(R.id.base_actionbar_left_tv)
    TextView baseActionbarLeftTv;
    @BindView(R.id.base_actionbar_left2_icon)
    ImageView baseActionbarLeft2Icon;
    @BindView(R.id.base_actionbar_title)
    TextView baseActionbarTitle;
    @BindView(R.id.edit_search_view)
    SearchEditText editSearchView;
    @BindView(R.id.base_actionbar_right_tv)
    TextView baseActionbarRightTv;
    @BindView(R.id.base_actionbar_right_icon)
    ImageView baseActionbarRightIcon;
    @BindView(R.id.base_actionbar)
    FrameLayout baseActionbar;
    @BindView(R.id.bind_service)
    Button btnBindService;
    @BindView(R.id.unbind_service)
    Button btnUnbindService;

    private MyService.MyBinder myBinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ServiceActivity.class);
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
        return R.layout.activity_service;
    }

    /**
     * 首先创建了一个ServiceConnection的匿名类，在里面重写了onServiceConnected()方法和onServiceDisconnected()方法，
     * 这两个方法分别会在Activity与Service建立关联和解除关联的时候调用。
     * 在onServiceConnected()方法中，我们又通过向下转型得到了MyBinder的实例，
     * 有了这个实例，Activity和Service之间的关系就变得非常紧密了。
     * 现在我们可以在Activity中根据具体的场景来调用MyBinder中的任何public方法，
     * 即实现了Activity指挥Service干什么Service就去干什么的功能。
     */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //在onServiceConnected()方法中，我们又通过向下转型得到了MyBinder的实例
            myBinder = (MyService.MyBinder) service;
            //有了这个实例，Activity和Service之间的关系就变得非常紧密了
            //根据具体的场景来调用MyBinder中的任何public方法
            //一个Service必须要在既没有和任何Activity关联又处理停止状态的时候才会被销毁
            myBinder.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        baseActionbarLeftIcon.setVisibility(View.VISIBLE);
        baseActionbarRightTv.setVisibility(View.GONE);
        editSearchView.setVisibility(View.GONE);
        baseActionbarTitle.setVisibility(View.VISIBLE);
        baseActionbarTitle.setText("Service");
    }

    @OnClick({R.id.base_actionbar_left_icon, R.id.start_service, R.id.stop_service, R.id.bind_service, R.id.unbind_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.base_actionbar_left_icon:
                finish();
                break;

            case R.id.start_service:
                Intent startIntent = new Intent(ServiceActivity.this, MyService.class);
                startService(startIntent);
                break;

            case R.id.stop_service:
                Intent stopIntent = new Intent(ServiceActivity.this, MyService.class);
                //点击Stop Service按钮只会让Service停止
                stopService(stopIntent);
                break;

            case R.id.bind_service:
                //bindService()方法接收三个参数，第一个参数就是刚刚构建出的Intent对象，
                //第二个参数是前面创建出的ServiceConnection的实例，
                //第三个参数是一个标志位，这里传入BIND_AUTO_CREATE
                //表示在Activity和Service建立关联后自动创建Service，
                //这会使得MyService中的onCreate()方法得到执行，但onStartCommand()方法不会执行。
                Intent bindIntent = new Intent(ServiceActivity.this, MyService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                break;

            case R.id.unbind_service:
                //点击Unbind Service按钮只会让Service和Activity解除关联
                unbindService(connection);
                break;
        }
    }
}
