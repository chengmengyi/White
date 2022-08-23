package com.demo.white.ma

import com.demo.white.app.Conf0816
import com.demo.white.en.Server0816En
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

object Server0816Ma {
    private val local= arrayListOf<Server0816En>()

    fun getServerList() = Read0816Ma.server.ifEmpty { local }

    fun getFastServer():Server0816En?{
        val serverList = getServerList()
        if (!Read0816Ma.city.isNullOrEmpty()){
            val filter = serverList.filter { Read0816Ma.city.contains(it.city_0816) }
            if (!filter.isNullOrEmpty()){
                return filter.randomOrNull()
            }
        }
        return serverList.randomOrNull()
    }
    
    fun initLocalServer(){
        local.clear()
        try {
            val jsonArray = JSONObject(Conf0816.LOCAL_0816_SERVER).getJSONArray("white_server_0816")
            for (index in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(index)
                local.add(
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
                local.forEach {
                    it.write()
                }
            }
        }catch (e:Exception){ }
    }
}