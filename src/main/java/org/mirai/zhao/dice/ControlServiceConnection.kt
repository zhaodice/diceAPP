package org.mirai.zhao.dice

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

open class ControlServiceConnection : ServiceConnection {
    var mBound = false
    var iConsoleService: IConsoleService?=null
    private var ifDisconnectCallback: (() -> Unit?)? =null
    private var ifConnectCallback: (() -> Unit?)? =null
    override fun onServiceConnected(name: ComponentName, service: IBinder) {
        iConsoleService = IConsoleService.Stub.asInterface(service)
        mBound = true
        ifConnectCallback?.let { it() }
        ifConnectCallback=null
    }
    override fun onServiceDisconnected(name: ComponentName) {
        iConsoleService = null
        mBound = false
        ifDisconnectCallback?.let { it() }
    }
    fun ifDisconnect(callback:()->Unit): ControlServiceConnection {
        ifDisconnectCallback = callback
        return this
    }
    fun ifConnect(callback:()->Unit): ControlServiceConnection {
        if(mBound) {
            callback()
        }else{
            ifConnectCallback = callback
        }
        return this
    }
}