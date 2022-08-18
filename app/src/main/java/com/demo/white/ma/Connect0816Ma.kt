package com.demo.white.ma

import com.demo.white.ac.Base0816Ac
import com.demo.white.ac.Home0816Ac
import com.demo.white.en.Server0816En
import com.github.shadowsocks.Core
import com.github.shadowsocks.aidl.IShadowsocksService
import com.github.shadowsocks.aidl.ShadowsocksConnection
import com.github.shadowsocks.bg.BaseService
import com.github.shadowsocks.preference.DataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Connect0816Ma:ShadowsocksConnection.Callback{
    var action=""
    private var context:Base0816Ac?=null
    var current=Server0816En.createFast()
    var last=Server0816En.createFast()
    private var state=BaseService.State.Idle
    private val sc=ShadowsocksConnection(true)
    private var iConnectStateCallback:IConnectStateCallback?=null

    fun setIConnectStateCallback(iConnectStateCallback: IConnectStateCallback){
        this.iConnectStateCallback=iConnectStateCallback
    }

    fun onCreate(context:Base0816Ac){
        this.context=context
        sc.connect(context,this)
    }


    fun isConnected()= state==BaseService.State.Connected

    private fun isStopped()= state==BaseService.State.Stopped

    fun isConnectedOrStoppedSuccess(isConnect:Boolean)=if (isConnect) isConnected() else isStopped()

    override fun stateChanged(state: BaseService.State, profileName: String?, msg: String?) {
        this.state=state
        changeServerBean()
        if (isStopped()){
            iConnectStateCallback?.stoppedCallback()
        }
    }

    override fun onServiceConnected(service: IShadowsocksService) {
        val state = BaseService.State.values()[service.state]
        this.state=state
        changeServerBean()
        if (isConnected()){
            iConnectStateCallback?.connectedCallback()
        }
    }

    private fun changeServerBean(){
        if (isConnected()){
            last= current
        }
    }

    fun connect(){
        state=BaseService.State.Connecting
        GlobalScope.launch {
            if (current.isFast()){
                val f = Server0816Ma.getFastServer()
                if (null!=f){
                    DataStore.profileId = f.getId()
                    Core.startService()
                }
            }else{
                DataStore.profileId = current.getId()
                Core.startService()
            }
        }
    }

    fun disconnect(){
        state=BaseService.State.Stopping
        GlobalScope.launch {
            Core.stopService()
        }
    }

    interface IConnectStateCallback{
        fun connectedCallback()
        fun stoppedCallback()
    }

    override fun onBinderDied() {
        context?.run {
            sc.disconnect(this)
        }
    }

    fun onDestroy(){
        onBinderDied()
        context=null
    }

}