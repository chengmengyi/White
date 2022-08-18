package com.white.unlimited.secure.tool.fast.ac

import android.content.Intent
import android.net.Uri
import com.white.unlimited.secure.tool.fast.R
import com.white.unlimited.secure.tool.fast.app.Conf0816
import com.white.unlimited.secure.tool.fast.app.jumpGooglePlay
import com.white.unlimited.secure.tool.fast.app.shareAppDownPath
import kotlinx.android.synthetic.main.layout_set.*

class Set0816Ac:Base0816Ac(R.layout.layout_set) {
    override fun initView() {
        setOnClick()
    }

    private fun setOnClick(){
        iv_back.setOnClickListener { finish() }

        ll_contact.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data= Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_EMAIL,Conf0816.E)
                startActivity(intent)
            }catch (e:Exception){
                toast("Contact us by email：${Conf0816.E}")
            }
        }

        ll_update.setOnClickListener {
            jumpGooglePlay()
        }

        ll_share.setOnClickListener {
            shareAppDownPath()
        }

        ll_privacy.setOnClickListener {
            startActivity(Intent(this,Web0816Ac::class.java))
        }
    }
}