package com.demo.white.ac

import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.demo.white.R
import com.gyf.immersionbar.ImmersionBar

abstract class Base0816Ac(
    private val layoutId:Int
) : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        density()
        setContentView(layoutId)
        ImmersionBar.with(this).apply {
            fitsSystemWindows(true)
            statusBarColor(R.color.color_f7f9ff)
            statusBarDarkFont(true)
            init()
        }
        initView()
    }

    abstract fun initView()

    private fun density(){
        val metrics: DisplayMetrics = resources.displayMetrics
        val td = metrics.heightPixels / 760f
        val dpi = (160 * td).toInt()
        metrics.density = td
        metrics.scaledDensity = td
        metrics.densityDpi = dpi
    }

    protected fun toast(s: String){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

}