package com.demo.white.ad

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import com.blankj.utilcode.util.SizeUtils
import com.demo.white.R
import com.demo.white.ac.Base0816Ac
import com.demo.white.app.Ac0816Listener
import com.demo.white.app.show
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import kotlinx.coroutines.*

class Show0816NativeAd (
    private val context0816:Base0816Ac,
    private val type0816:String){


    private var nativeAd:NativeAd?=null
    private var globalScope:Job?=null

    fun checkNativeAdLoadFinish(){
        Load0816AdMa.doLogic(type0816)
        globalScope= GlobalScope.launch(Dispatchers.Main) {
            delay(300L)
            while (true){
                if (!isActive) {
                    break
                }
                val adAny = Load0816AdMa.getAd(type0816)?.ad
                if (null!=adAny&&context0816.isResume){
                    cancel()
                    if (adAny is NativeAd){
                        nativeAd?.destroy()
                        nativeAd=adAny
                        showNative(adAny)
                    }
                }
                delay(1000L)
            }
        }
    }

    private fun showNative(adAny: NativeAd) {
        val native_ad=context0816.findViewById<NativeAdView>(R.id.native_ad)
        native_ad.mediaView=context0816.findViewById(R.id.native_ad_media)
        if (null!=adAny.mediaContent){
            native_ad.mediaView?.apply {
                setMediaContent(adAny.mediaContent)
                setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                outlineProvider = provider
            }
        }
        native_ad.headlineView=context0816.findViewById(R.id.native_ad_title)
        (native_ad.headlineView as AppCompatTextView).text=adAny.headline

        native_ad.bodyView=context0816.findViewById(R.id.native_ad_desc)
        (native_ad.bodyView as AppCompatTextView).text=adAny.body

        native_ad.iconView=context0816.findViewById(R.id.native_ad_logo)
        (native_ad.iconView as ImageFilterView).setImageDrawable(adAny.icon?.drawable)

        native_ad.callToActionView=context0816.findViewById(R.id.native_ad_btn)
        (native_ad.callToActionView as AppCompatTextView).text=adAny.callToAction

        native_ad.setNativeAd(adAny)
        context0816.findViewById<AppCompatImageView>(R.id.native_ad_cover).show(false)

        if (Ad0816Type.HOME0816==type0816){
            Ac0816Listener.refreshHomeNativeAd=false
        }
        Load0816AdMa.removeCache(type0816)
        Load0816AdMa.doLogic(type0816)
    }

    private val provider=object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            if (view == null || outline == null) return
            outline.setRoundRect(
                0,
                0,
                view.width,
                view.height,
                SizeUtils.dp2px(10F).toFloat()
            )
            view.clipToOutline = true
        }
    }

    fun onDestroy(){
        globalScope?.cancel()
        globalScope=null
    }
}