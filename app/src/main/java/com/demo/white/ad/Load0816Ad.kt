package com.demo.white.ad

import com.demo.white.app.Conf0816
import com.demo.white.app.mWhite0816
import com.demo.white.app.print0816
import com.demo.white.ma.Read0816Ma
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.tencent.mmkv.MMKV
import org.json.JSONObject

abstract class Load0816Ad {
    protected val loading0816List= arrayListOf<String>()
    protected val loadedAdMap= hashMapOf<String,Loaded0816AdEn>()

    protected fun loadAd(type0816:String,next:Conf0816AdEn,loadResult:(success:Boolean,Loaded0816AdEn)->Unit) {
        print0816("start load ad,type=$type0816  id=${next.id_0816}  type=${next.type_0816}  sort=${next.sort_0816}")
        when(next.type_0816){
            "kaip"->loadKaiP(type0816, next, loadResult)
            "chap"->loadChaP(type0816, next, loadResult)
            "yuans"->loadYuans(type0816, next, loadResult)
        }
    }

    private fun loadKaiP(type0816:String,next:Conf0816AdEn,loadResult:(success:Boolean,Loaded0816AdEn)->Unit){
        AppOpenAd.load(
            mWhite0816,
            next.id_0816,
            AdRequest.Builder().build(),
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback(){
                override fun onAdLoaded(p0: AppOpenAd) {
                    super.onAdLoaded(p0)
                    print0816("load $type0816 success")
                    loadResult.invoke(true, Loaded0816AdEn(System.currentTimeMillis(),p0))
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    print0816("load $type0816 fail,reason:${p0.message}")
                    loadResult.invoke(false,Loaded0816AdEn())
                }
            }
        )
    }


    private fun loadChaP(type0816:String,next:Conf0816AdEn,loadResult:(success:Boolean,Loaded0816AdEn)->Unit){
        InterstitialAd.load(
            mWhite0816,
            next.id_0816,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    print0816("load $type0816 fail,reason:${p0.message}")
                    loadResult.invoke(false,Loaded0816AdEn())
                }

                override fun onAdLoaded(p0: InterstitialAd) {
                    print0816("load $type0816 success")
                    loadResult.invoke(true, Loaded0816AdEn(System.currentTimeMillis(),p0))
                }
            }
        )
    }

    private fun loadYuans(type0816:String,next:Conf0816AdEn,loadResult:(success:Boolean,Loaded0816AdEn)->Unit){
        AdLoader.Builder(
            mWhite0816,
            next.id_0816
        ).forNativeAd {
            print0816("load $type0816 success")
            loadResult.invoke(true, Loaded0816AdEn(System.currentTimeMillis(),it))
        }
            .withAdListener(object : AdListener(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    print0816("load $type0816 fail,reason:${p0.message}")
                    loadResult.invoke(false,Loaded0816AdEn())
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .setAdChoicesPlacement(
                        NativeAdOptions.ADCHOICES_TOP_LEFT
                    )
                    .build()
            )
            .build()
            .loadAd(AdRequest.Builder().build())
    }

    protected fun loading(t0816:String) = loading0816List.contains(t0816)

    protected fun isLoaded(t0816:String):Boolean{
        if (loadedAdMap.containsKey(t0816)){
            val loaded0816AdEn = loadedAdMap[t0816]
            return if (null!=loaded0816AdEn?.ad&&!loaded0816AdEn.checkIsEx()){
                true
            }else{
                removeCache(t0816)
                false
            }
        }
        return false
    }

    protected fun readLocalAdList(t0816: String):List<Conf0816AdEn>{
        val list= arrayListOf<Conf0816AdEn>()
        try {
            val jsonArray = JSONObject(readAdJson()).getJSONArray(t0816)
            for (index in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(index)
                val conf0816AdEn = Conf0816AdEn(
                    jsonObject.optString("white_source"),
                    jsonObject.optString("white_id"),
                    jsonObject.optString("white_type"),
                    jsonObject.optInt("white_sort"),
                )
                list.add(conf0816AdEn)
            }
        }catch (e:Exception){}
        return list.filter { it.source_0816 == "admob" }.sortedByDescending { it.sort_0816 }
    }

    private fun readAdJson():String{
        val ad = MMKV.defaultMMKV().decodeString(Read0816Ma.AD_TAG)
        return if (ad.isNullOrEmpty()) Conf0816.LOCAL_0816_AD else ad
    }

    fun removeCache(t0816: String){
        loadedAdMap.remove(t0816)
    }
}