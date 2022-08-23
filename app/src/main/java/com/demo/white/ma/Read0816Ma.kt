package com.demo.white.ma

import com.demo.white.BuildConfig
import com.demo.white.app.Conf0816
import com.demo.white.en.Server0816En
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

object Read0816Ma {
    const val AD_TAG="white_ad_0816"
    val city= arrayListOf<String>()
    val server= arrayListOf<Server0816En>()

    fun r0816(){
//        if (BuildConfig.DEBUG){
            city0816(Conf0816.LOCAL_0816_CITY)
            server0816(Conf0816.LOCAL_0816_SERVER)
            ad0816(Conf0816.LOCAL_0816_AD)
//        }else{
//            val remoteConfig = Firebase.remoteConfig
//            remoteConfig.fetchAndActivate().addOnCompleteListener {
//                if (it.isSuccessful){
//                    city0816(remoteConfig.getString("white_city_0816"))
//                    server0816(remoteConfig.getString("white_server_0816"))
//                    ad0816(remoteConfig.getString("white_ad_0816"))
//                }
//            }
//        }
    }

    private fun city0816(s:String){
        try {
            city.clear()
            val jsonArray = JSONObject(s).getJSONArray("white_city_0816")
            for (index in 0 until jsonArray.length()){
                city.add(jsonArray.optString(index))
            }
        }catch (e:Exception){}
    }

    private fun server0816(s:String) {
        server.clear()
        try {
            val jsonArray = JSONObject(s).getJSONArray("white_server_0816")
            for (index in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(index)
                server.add(
                    Server0816En(
                        jsonObject.optString("pwd_0816"),
                        jsonObject.optString("method_0816"),
                        jsonObject.optInt("port_0816"),
                        jsonObject.optString("country_0816"),
                        jsonObject.optString("city_0816"),
                        jsonObject.optString("host_0816"),
                    )
                )
            }
            GlobalScope.launch {
                server.forEach {
                    it.write()
                }
            }
        }catch (e:Exception){ }
    }

    private fun ad0816(s: String){
        MMKV.defaultMMKV().encode(AD_TAG,s)
    }
}