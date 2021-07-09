package liang.com.baseproject.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.OnClick;
import liang.com.baseproject.MyAIDLService;
import liang.com.baseproject.R;

import com.liang.module_core.mvp.MVPBaseActivity;
import com.liang.module_core.mvp.MVPBasePresenter;
import com.liang.module_core.utils.ToastUtil;
import com.liang.module_core.widget.SearchEditText;

import liang.com.baseproject.service.MusicPlayService;
import liang.com.baseproject.service.MusicServiceConnection;
import liang.com.baseproject.service.MyRemoteService;
import liang.com.baseproject.service.MyService;

public class ServiceActivity extends MVPBaseActivity {

    public static final String TAG = "ServiceActivity";

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
    @BindView(R.id.bind_aidl_service)
    Button btnAidlService;
    @BindView(R.id.unbind_aidl_service)
    Button btnUnBindAidlService;

    @BindView(R.id.btnPlayer)
    Button btnPlayer;
    @BindView(R.id.btnPause)
    Button btnPause;
    @BindView(R.id.btnRePlayer)
    Button btnRePlayer;
    @BindView(R.id.btnSignOutMethod)
    Button btnSignOutMethod;

    private MyService.MyBinder myBinder;

    private MyAIDLService myAIDLService;

    private Intent appServiceIntent;
    private MusicServiceConnection musicServiceConnection;

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
            //可以在onServiceConnected方法里拿到了MyService服务的内部类MyBinder的对象，
            //通过这个内部类对象，只要强转一下，我们可以调用这个内部类的非私有成员对象和方法。
            myBinder = (MyService.MyBinder) service;
            //有了这个实例，Activity和Service之间的关系就变得非常紧密了
            //根据具体的场景来调用MyBinder中的任何public方法
            //一个Service必须要在既没有和任何Activity关联又处理停止状态的时候才会被销毁
            myBinder.startDownload();
            Log.d(TAG, "onServiceConnected() executed");
            Log.d("MyService", "onServiceConnected() executed");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected() executed");
            Log.d("MyService", "onServiceDisconnected() executed");
        }
    };

    /**
     * MyAIDLService.Stub.asInterface()方法将传入的IBinder对象传换成了MyAIDLService对象，
     * 接下来就可以调用在MyAIDLService.aidl文件中定义的所有接口了。
     */
    private ServiceConnection aidlConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myAIDLService = MyAIDLService.Stub.asInterface(service);
            try {
                int result = myAIDLService.plus(5, 7);
                String upperStr = myAIDLService.toUpperCase("hello aidl service");
                Log.d(TAG, "onServiceConnected: " + "result is " + result);
                Log.d(TAG, "onServiceConnected: " + "upperStr is " + upperStr);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected() executed");
            Log.d("MyService", "onServiceDisconnected() executed");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        //Android服务之混合方式开启服务
        startAppService();
    }

    private void initView() {
        baseActionbarLeftIcon.setVisibility(View.VISIBLE);
        baseActionbarRightTv.setVisibility(View.GONE);
        editSearchView.setVisibility(View.GONE);
        baseActionbarTitle.setVisibility(View.VISIBLE);
        baseActionbarTitle.setText("Service");
    }

    /**
     * Android服务之混合方式开启服务
     */
    private void startAppService() {
        //1、使用startService()方式开启服务
        appServiceIntent = new Intent(ServiceActivity.this, MusicPlayService.class);
        startService(appServiceIntent);

        //2、通过bindService()的方式绑定该服务（注意:使用同一个Intent对象）
        if (musicServiceConnection == null) {
            //保证ServiceConnection连接对象的唯一性
            musicServiceConnection = new MusicServiceConnection();
        }
        bindService(appServiceIntent, musicServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @OnClick({R.id.base_actionbar_left_icon, R.id.start_service, R.id.stop_service, R.id.bind_service,
            R.id.unbind_service, R.id.bind_aidl_service, R.id.unbind_aidl_service, R.id.btnPlayer,
            R.id.btnPause, R.id.btnRePlayer, R.id.btnSignOutMethod})
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

            case R.id.bind_aidl_service:
                Intent bindService2 = new Intent(ServiceActivity.this, MyRemoteService.class);
                bindService(bindService2, aidlConnection, BIND_AUTO_CREATE);
                break;

            case R.id.unbind_aidl_service:
                unbindService(aidlConnection);
                break;

            case R.id.btnPlayer:
                //播放
                if (musicServiceConnection != null) {
                    MusicPlayService.MusicBindImpl musicBind = musicServiceConnection.getMusicBindImpl();
                    if (musicBind != null) {
                        musicBind.callPlayer();
                        ToastUtil.onShowTrueToast(ServiceActivity.this, "继续服务模拟播放音乐...");
                    }
                } else {
                    startAppService();
                    MusicPlayService.MusicBindImpl musicBind = musicServiceConnection.getMusicBindImpl();
                    if (musicBind != null) {
                        musicBind.callPlayer();
                        ToastUtil.onShowTrueToast(ServiceActivity.this, "继续服务模拟播放音乐...");
                    }
                }
                break;

            case R.id.btnPause:
                //暂停
                if (musicServiceConnection != null) {
                    MusicPlayService.MusicBindImpl musicBind = musicServiceConnection.getMusicBindImpl();
                    if (musicBind != null) {
                        musicBind.callPausePlayer();
                    }
                } else {
                    ToastUtil.onShowErrorToast(ServiceActivity.this, "音乐服务还未开启");
                }
                break;

            case R.id.btnRePlayer:
                //继续
                if (musicServiceConnection != null) {
                    MusicPlayService.MusicBindImpl musicBind = musicServiceConnection.getMusicBindImpl();
                    if (musicBind != null) {
                        musicBind.callRePlayer();
                        ToastUtil.onShowTrueToast(ServiceActivity.this, "开始服务模拟播放音乐...");
                    }
                } else {
                    ToastUtil.onShowErrorToast(ServiceActivity.this, "音乐服务还未开启");
                }
                break;

            case R.id.btnSignOutMethod:
                //解绑服务并停止服务
                if (musicServiceConnection != null) {
                    //退出(先暂停播放，在解绑，最后应用退出)
                    musicServiceConnection.getMusicBindImpl().callPausePlayer();
                    unbindService(musicServiceConnection);
                    //解绑后切记把ServiceConnection对象置null，避免运行异常
                    musicServiceConnection = null;
                } else {
                    ToastUtil.onShowErrorToast(ServiceActivity.this, "音乐服务还未开启");
                }

                if (appServiceIntent != null) {
                    stopService(appServiceIntent);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (musicServiceConnection != null) {
            unbindService(musicServiceConnection);
            musicServiceConnection = null;
        }
        appServiceIntent = null;
    }
}
