package com.demo.white.app

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.demo.white.ac.Home0816Ac
import com.demo.white.ac.Main0816Ac
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Ac0816Listener {
    var is0816Front=true
    private var global0816Scope: Job?=null
    private var reload0816=false
    var refreshHomeNativeAd=true


    fun register(application: Application){
        application.registerActivityLifecycleCallbacks(listener)
    }

    private val listener=object : Application.ActivityLifecycleCallbacks{
        private var num=0
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

        override fun onActivityStarted(activity: Activity) {
            num++
            global0816Scope?.cancel()
            global0816Scope=null
            if (num==1){
                is0816Front=true
                if (reload0816){
                    refreshHomeNativeAd=true
                    if (ActivityUtils.isActivityExistsInStack(Home0816Ac::class.java)){
                        activity.startActivity(Intent(activity, Main0816Ac::class.java))
                    }
                }
                reload0816=false
            }
        }

        override fun onActivityResumed(activity: Activity) {}

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivityStopped(activity: Activity) {
            num--
            if (num<=0){
                is0816Front=false
                global0816Scope= GlobalScope.launch {
                    delay(3000L)
                    reload0816=true
//                    ActivityUtils.finishActivity(Main0810Activity::class.java)
//                    ActivityUtils.finishActivity(AdActivity::class.java)
                }
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {}
    }
}