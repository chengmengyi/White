package com.demo.white.app

import android.app.ActivityManager
import android.app.Application
import com.demo.white.ac.Home0816Ac
import com.demo.white.ma.Read0816Ma
import com.demo.white.ma.Server0816Ma
import com.github.shadowsocks.Core
import com.tencent.mmkv.MMKV

lateinit var mWhite0816:White0816
class White0816:Application() {
    override fun onCreate() {
        super.onCreate()
        mWhite0816=this
        Core.init(this,Home0816Ac::class)
        if (!packageName.equals(getCurrentProcessName(this))){
            return
        }
        MMKV.initialize(this)
        Ac0816Listener.register(this)
        Server0816Ma.initLocalServer()
        Read0816Ma.r0816()
    }

    private fun getCurrentProcessName(applicationContext: Application): String {
        val pid = android.os.Process.myPid()
        var processName = ""
        val manager = applicationContext.getSystemService(Application.ACTIVITY_SERVICE) as ActivityManager
        for (process in manager.runningAppProcesses) {
            if (process.pid === pid) {
                processName = process.processName
            }
        }
        return processName
    }
}