package com.demo.white.ma

import kotlinx.coroutines.*
import java.lang.Exception

object TimeMa {
    private var time=0L
    private var global0816Scope:Job?=null
    private var listener= arrayListOf<IConnectTimeCallback>()


    fun start(){
        global0816Scope= GlobalScope.launch(Dispatchers.Main) {
            while (true){
                listener.forEach { it.connectTimeCallback(transTime()) }
                time++
                delay(1000L)
            }
        }
    }

    fun stop(){
        time=0L
        global0816Scope?.cancel()
        global0816Scope=null
    }

    fun addCallback(call:IConnectTimeCallback){
        if (!listener.contains(call)){
            listener.add(call)
        }
    }

    fun removeCallback(call:IConnectTimeCallback){
        if (listener.contains(call)){
            listener.remove(call)
        }
    }

    private fun transTime():String{
        try {
            val shi=time/3600
            val fen= (time % 3600) / 60
            val miao= (time % 3600) % 60
            val s=if (shi<10) "0${shi}" else shi
            val f=if (fen<10) "0${fen}" else fen
            val m=if (miao<10) "0${miao}" else miao
            return "${s}:${f}:${m}"
        }catch (e: Exception){}
        return "00:00:00"
    }

    interface IConnectTimeCallback{
        fun connectTimeCallback(time:String)
    }
}