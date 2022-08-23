package com.demo.white.ad

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback

class ShowFullAdCallback(private val type0816:String,private val callback:()->Unit):FullScreenContentCallback() {
    override fun onAdDismissedFullScreenContent() {
        super.onAdDismissedFullScreenContent()
        Load0816AdMa.showingFullAd=false
        if (Ad0816Type.CONNECT0816==type0816){
            Load0816AdMa.doLogic(type0816)
        }
        callback.invoke()
    }

    override fun onAdShowedFullScreenContent() {
        super.onAdShowedFullScreenContent()
        Load0816AdMa.showingFullAd=true
        Load0816AdMa.removeCache(type0816)
    }

    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
        super.onAdFailedToShowFullScreenContent(p0)
        Load0816AdMa.showingFullAd=false
        Load0816AdMa.removeCache(type0816)
        callback.invoke()
    }
}