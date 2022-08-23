package com.demo.white.ac

import android.animation.ValueAnimator
import android.content.Intent
import android.view.KeyEvent
import android.view.animation.LinearInterpolator
import com.blankj.utilcode.util.ActivityUtils
import com.demo.white.R
import com.demo.white.ad.Ad0816Type
import com.demo.white.ad.Load0816AdMa
import com.demo.white.ad.Show0816FullAd
import kotlinx.android.synthetic.main.activity_main.*

class Main0816Ac : Base0816Ac(R.layout.activity_main) {
    private var animator0816: ValueAnimator?=null
    private val full by lazy { Show0816FullAd(this,Ad0816Type.OPEN0816){ jump() } }

    override fun initView() {
        preLoadAd()
        start()
    }

    private fun preLoadAd(){
        Load0816AdMa.doLogic(Ad0816Type.OPEN0816)
        Load0816AdMa.doLogic(Ad0816Type.HOME0816)
        Load0816AdMa.doLogic(Ad0816Type.RESULT0816)
        Load0816AdMa.doLogic(Ad0816Type.CONNECT0816)
    }

    private fun start(){
        animator0816 = ValueAnimator.ofInt(0, 100).apply {
            duration=10000L
            interpolator = LinearInterpolator()
            addUpdateListener {
                val pro = it.animatedValue as Int
                progress_bar.progress = pro
                val d = (10 * (pro / 100.0F)).toInt()
                if (d in 2..9){
                    if (full.hasAd()){
                        stop()
                        progress_bar.progress = 100
                        full.showFullAd()
                    }
                }else if (d>=10){
                    jump()
                }
            }
            start()
        }
    }
    private fun jump(){
        if (!ActivityUtils.isActivityExistsInStack(Home0816Ac::class.java)){
            startActivity(Intent(this,Home0816Ac::class.java))
        }
        finish()
    }

    private fun stop(){
        animator0816?.removeAllUpdateListeners()
        animator0816?.cancel()
        animator0816=null
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            return true
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
    }
}