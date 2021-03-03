package com.liang.module_bluetooth.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.UUID;

/**
 * 创建日期:2021/2/4 on 3:39 PM
 * 描述: 期: 2021/2/4 on 3:06 PM
 * 描述: 蓝牙连接服务
 * 把蓝牙的相关操作写在了Service中，这样即使应用退出也不会影响蓝牙的连接，
 * 在Service中通过广播的方式通知Activity做相应的处理.
 * 作者: 杨亮
 */
public class BleNormalService extends Service {

    //蓝牙连接状态
    private int mConnectionState = 0;
    // 蓝牙连接已断开
    private final int STATE_DISCONNECTED = 0;
    // 蓝牙正在连接
    private final int STATE_CONNECTING = 1;
    // 蓝牙已连接
    private final int STATE_CONNECTED = 2;

    // 蓝牙已连接
    public final static String ACTION_GATT_CONNECTED = "ACTION_GATT_CONNECTED";
    // 蓝牙已断开
    public final static String ACTION_GATT_DISCONNECTED = "ACTION_GATT_DISCONNECTED";
    // 发现GATT服务
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "ACTION_GATT_SERVICES_DISCOVERED";
    // 收到蓝牙数据
    public final static String ACTION_DATA_AVAILABLE = "ACTION_DATA_AVAILABLE";
    // 连接失败
    public final static String ACTION_CONNECTING_FAIL = "ACTION_CONNECTING_FAIL";
    // 蓝牙数据
    public final static String EXTRA_DATA = "EXTRA_DATA";

    // 服务标识
    private final UUID SERVICE_UUID = UUID.fromString("0000ace0-0000-1000-8000-00805f9b34fb");
    // 特征标识（读取数据）
    private final UUID CHARACTERISTIC_READ_UUID = UUID.fromString("0000ace0-0001-1000-8000-00805f9b34fb");
    // 特征标识（发送数据）
    private final UUID CHARACTERISTIC_WRITE_UUID = UUID.fromString("0000ace0-0003-1000-8000-00805f9b34fb");
    // 描述标识
    private final UUID DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    /**
     * 服务相关
     * 任何一个Service在整个应用程序范围内都是通用的，
     * 即MyService不仅可以和MainActivity建立关联，
     * 还可以和任何一个Activity建立关联，
     * 而且在建立关联时它们都可以获取到相同的MyBinder实例.
     */

    private LocalBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder{
        //获取蓝牙服务
        public BleNormalService getService(){
            return BleNormalService.this;
        }
    }

    public BleNormalService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //释放蓝牙相关资源
//        release();
        return super.onUnbind(intent);
    }
}