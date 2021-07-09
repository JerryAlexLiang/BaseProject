package liang.com.baseproject.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 创建日期: 2021/7/9 on 5:45 PM
 * 描述: 创建一个服务连接对象
 * 作者: 杨亮
 */
public class MusicServiceConnection implements ServiceConnection {

    private MusicPlayService.MusicBindImpl musicBind;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        //获取：服务绑定成功后，返回的中间帮助类对象
        musicBind = (MusicPlayService.MusicBindImpl) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    /**
     * 暴露方法：对外提供获取中间帮助类对象
     */
    public MusicPlayService.MusicBindImpl getMusicBindImpl() {
        if (musicBind != null) {
            return musicBind;
        }
        return null;
    }
}
