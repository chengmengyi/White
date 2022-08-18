package com.demo.white.ac

import com.demo.white.R
import com.demo.white.app.Conf0816
import kotlinx.android.synthetic.main.layout_web.*

class Web0816Ac:Base0816Ac(R.layout.layout_web) {
    override fun initView() {
        web_view.apply {
            settings.javaScriptEnabled=true
            loadUrl(Conf0816.U)
        }

        iv_back.setOnClickListener { finish() }
    }
}