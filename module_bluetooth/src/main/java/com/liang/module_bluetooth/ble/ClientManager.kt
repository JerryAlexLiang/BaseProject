package com.liang.module_bluetooth.ble

import com.inuker.bluetooth.library.BluetoothClient
import com.liang.module_bluetooth.BleApplication

object ClientManager {

    private var mClient: BluetoothClient? = null

    val client: BluetoothClient?
        get() {
            if (mClient == null) {
                synchronized(ClientManager::class.java) {
                    if (mClient == null) {
                        mClient = BluetoothClient(BleApplication.context)
                    }
                }
            }
            return mClient
        }
}