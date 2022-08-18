package com.demo.white.en

import com.github.shadowsocks.database.Profile
import com.github.shadowsocks.database.ProfileManager

class Server0816En(
    val pwd_0816:String="",
    val method_0816:String="",
    val port_0816:Int=0,
    val country_0816:String="",
    val city_0816:String="",
    val host_0816:String="",
) {
    fun write(){
        val profile = Profile(
            id = 0L,
            name = "$country_0816 - $city_0816",
            host = host_0816,
            remotePort = port_0816,
            password = pwd_0816,
            method = method_0816
        )

        var id:Long?=null
        ProfileManager.getActiveProfiles()?.forEach {
            if (it.remotePort==profile.remotePort&&it.host==profile.host){
                id=it.id
                return@forEach
            }
        }
        if (null==id){
            ProfileManager.createProfile(profile)
        }else{
            profile.id=id!!
            ProfileManager.updateProfile(profile)
        }
    }

    fun getId():Long{
        ProfileManager.getActiveProfiles()?.forEach {
            if (it.host==host_0816&&it.remotePort==port_0816){
                return it.id
            }
        }
        return 0L
    }

    fun isFast():Boolean{
        return country_0816=="Faster server"&&host_0816.isEmpty()
    }

    companion object{
        fun createFast()=Server0816En(country_0816 = "Faster server")
    }
}