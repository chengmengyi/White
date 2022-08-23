package com.demo.white.ac

import com.demo.white.R
import com.demo.white.ad.Ad0816Type
import com.demo.white.ad.Show0816NativeAd
import com.demo.white.app.getFlag
import com.demo.white.ma.Connect0816Ma
import kotlinx.android.synthetic.main.layout_result.*

class Result0816Ac:Base0816Ac(R.layout.layout_result) {
    private val resultAd by lazy { Show0816NativeAd(this,Ad0816Type.RESULT0816) }

    override fun initView() {
        val connect = intent.getBooleanExtra("connect", false)
        if (connect){
            tv_result.text="Connected succeeded"
            iv_bg.setImageResource(R.drawable.bg_connect_success)
        }else{
            iv_bg.setImageResource(R.drawable.bg_disconnect_success)
            tv_result.text="Disconnected succeeded"
        }
        tv_server_flag.setImageResource(getFlag(Connect0816Ma.last.country_0816))

        iv_back.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        resultAd.checkNativeAdLoadFinish()
    }

    override fun onDestroy() {
        super.onDestroy()
        resultAd.onDestroy()
    }
}