package com.white.unlimited.secure.tool.fast.ac

import android.animation.ValueAnimator
import android.content.Intent
import android.net.VpnService
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.util.SizeUtils.dp2px
import com.white.unlimited.secure.tool.fast.R
import com.white.unlimited.secure.tool.fast.app.*
import com.white.unlimited.secure.tool.fast.ma.Connect0816Ma
import com.white.unlimited.secure.tool.fast.ma.TimeMa
import com.github.shadowsocks.bg.BaseService
import com.github.shadowsocks.utils.StartService
import kotlinx.android.synthetic.main.layout_home.*

class Home0816Ac:Base0816Ac(R.layout.layout_home), TimeMa.IConnectTimeCallback,
    Connect0816Ma.IConnectStateCallback {
    private var canClick=true
    private var permission = false
    private var connect=false
    private val instance=dp2px(98F)
    private var loopCheckAnimator:ValueAnimator?=null


    private val launcher = registerForActivityResult(StartService()) {
        if (!it && permission) {
            permission = false
            connectServer()
        } else {
            canClick=true
            toast("Connected fail")
        }
    }


    override fun initView() {
        Connect0816Ma.onCreate(this)
        TimeMa.addCallback(this)
        Connect0816Ma.setIConnectStateCallback(this)
        setOnClick()
    }

    private fun setOnClick(){
        iv_center_view.setOnClickListener {
            if (canClick){
                clickConnectBtn()
            }
        }
        iv_set.setOnClickListener {
            if (canClick){
                startActivity(Intent(this,Set0816Ac::class.java))
            }
        }
        view_choose_server.setOnClickListener {
            if (canClick){
                startActivity(Intent(this,Server0816Ac::class.java))
            }
        }
    }

    private fun clickConnectBtn(connected:Boolean=Connect0816Ma.isConnected()){
        canClick=false
        if (connected){
            disconnectServer()
        }else{
            if (!checkNet()) {
                canClick=true
                return
            }
            if (!checkPermission()){
                return
            }
            connectServer()
        }
    }


    private fun disconnectServer(){
        Connect0816Ma.disconnect()
        updateConnectingUI()
        loopCheckResult(false)
    }

    private fun connectServer(){
        Connect0816Ma.connect()
        updateConnectingUI()
        loopCheckResult(true)
    }

    private fun loopCheckResult(connect: Boolean){
        this.connect=connect
        loopCheckAnimator = ValueAnimator.ofInt(0, 100).apply {
            duration=10000L
            interpolator = LinearInterpolator()
            addUpdateListener {
                val pro = it.animatedValue as Int
                iv_center_view.translationY=getTranslationY(connect, pro)
                val duration = (10 * (pro / 100.0F)).toInt()
                if (duration in 2..9){
                    if (Connect0816Ma.isConnectedOrStoppedSuccess(connect)){
                        stopLoopAnimator()
                        iv_center_view.translationY=getTranslationY(connect, 100)
                        checkResult()
                    }
                }else if (duration>=10){
                    checkResult()
                }
            }
            start()
        }
    }

    private fun checkResult(jump:Boolean=true){
        if (Connect0816Ma.isConnectedOrStoppedSuccess(connect)){
            if (connect){
                updateConnectedUI()
            }else{
                updateStoppedUI()
                updateCurrentServerInfo()
            }
            if (jump){
                jump()
            }
            canClick=true
        }else{
            toast(if (connect) "Connect Fail" else "Disconnect Fail")
            updateStoppedUI()
            canClick=true
        }
    }

    private fun stopLoopAnimator(){
        loopCheckAnimator?.removeAllUpdateListeners()
        loopCheckAnimator?.cancel()
        loopCheckAnimator=null
    }


    private fun updateConnectingUI(){
        iv_center_connect_btn.show(false)
        tv_connect_time.show(true)
        view_center_bg.setBackgroundResource(R.drawable.bg_d0cce0_50dp)
    }

    private fun updateConnectedUI(){
        iv_center_connect_btn.show(false)
        tv_connect_time.show(true)
        TimeMa.start()
        view_center_bg.setBackgroundResource(R.drawable.bg_8164ff_50dp)
    }

    private fun updateStoppedUI(){
        tv_connect_time.text="00:00:00"
        iv_center_connect_btn.show(true)
        tv_connect_time.show(false)
        TimeMa.stop()
        view_center_bg.setBackgroundResource(R.drawable.bg_d0cce0_50dp)
    }

    private fun updateCurrentServerInfo(){
        val current = Connect0816Ma.current
        tv_server_name.text= getServerName(current)
        iv_server_flag.setImageResource(getFlag(current.country_0816))
    }

    private fun jump(){
        if (Ac0816Listener.is0816Front){
            val intent = Intent(this, Result0816Ac::class.java)
            intent.putExtra("connect",connect)
            startActivity(intent)
        }
    }

    private fun checkNet():Boolean{
        if (getNetWorkStatus(this) ==1){
            AlertDialog.Builder(this).apply {
                setMessage("You are not currently connected to the network")
                setPositiveButton("sure", null)
                show()
            }
            return false
        }
        return true
    }

    private fun checkPermission():Boolean{
        if (VpnService.prepare(this) != null) {
            permission = true
            launcher.launch(null)
            return false
        }
        return true
    }

    private fun getTranslationY(connect: Boolean,pro:Int):Float{
        val fl = instance/ 100F * pro
        return if (connect) fl else instance-fl
    }

    override fun onResume() {
        super.onResume()
        if (Connect0816Ma.action.isNotEmpty()){
            val action = Connect0816Ma.action
            Connect0816Ma.action=""
            if (action=="connect"){
                updateCurrentServerInfo()
                clickConnectBtn(false)
            }else if (action=="disconnect"){
                clickConnectBtn(true)
            }
        }
    }

    override fun connectTimeCallback(time: String) {
        tv_connect_time.text=time
    }

    override fun onDestroy() {
        super.onDestroy()
        Connect0816Ma.onDestroy()
        stopLoopAnimator()
        TimeMa.removeCallback(this)
    }

    override fun connectedCallback() {
        updateConnectedUI()
    }

    override fun stoppedCallback() {
        if (canClick){
            updateStoppedUI()
        }
    }
}