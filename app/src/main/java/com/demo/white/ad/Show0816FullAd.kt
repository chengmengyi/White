package com.demo.white.ad

import com.demo.white.ac.Base0816Ac
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Show0816FullAd (
    private val context0816:Base0816Ac,
    private val type0816:String,
    private val next:()->Unit
){

    fun showFullAd(){
        if (Load0816AdMa.showingFullAd||!context0816.isResume){
            next.invoke()
            return
        }
        val ad = Load0816AdMa.getAd(type0816)
        if (ad?.ad != null){
            Load0816AdMa.showingFullAd=true
            val adAny = ad.ad
            if (adAny is AppOpenAd){
                showOpenAd(adAny)
            }
            if (adAny is InterstitialAd){
                showInterstitialAdAd(adAny)
            }
        }
    }

    private fun showOpenAd(ad:AppOpenAd){
        ad.fullScreenContentCallback=ShowFullAdCallback(type0816){
            showFinish()
        }
        ad.show(context0816)
    }

    private fun showInterstitialAdAd(ad:InterstitialAd){
        ad.fullScreenContentCallback=ShowFullAdCallback(type0816){
            showFinish()
        }
        ad.show(context0816)
    }

    private fun showFinish(){
        GlobalScope.launch(Dispatchers.Main) {
            delay(200L)
            if (context0816.isResume){
                next.invoke()
            }
        }
    }

    fun hasAd():Boolean{
        return Load0816AdMa.getAd(type0816)?.ad!=null
    }
}