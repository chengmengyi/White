package com.demo.white.ac

import android.animation.ValueAnimator
import android.content.Intent
import android.view.KeyEvent
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import com.blankj.utilcode.util.ActivityUtils
import com.demo.white.R
import kotlinx.android.synthetic.main.activity_main.*

class Main0816Ac : Base0816Ac(R.layout.activity_main) {
    private var animator0816: ValueAnimator?=null

    override fun initView() {
        start()
    }

    private fun start(){
        animator0816 = ValueAnimator.ofInt(0, 100).apply {
            duration=2000L
            interpolator = LinearInterpolator()
            addUpdateListener {
                val pro = it.animatedValue as Int
                progress_bar.progress = pro
            }
            doOnEnd {
                jump()
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