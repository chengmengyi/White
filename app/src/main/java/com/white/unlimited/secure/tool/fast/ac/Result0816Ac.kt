package com.white.unlimited.secure.tool.fast.ac

import com.white.unlimited.secure.tool.fast.R
import com.white.unlimited.secure.tool.fast.app.getFlag
import com.white.unlimited.secure.tool.fast.ma.Connect0816Ma
import kotlinx.android.synthetic.main.layout_result.*

class Result0816Ac:Base0816Ac(R.layout.layout_result) {
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
}