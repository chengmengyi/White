package com.demo.white.ad

class Loaded0816AdEn(
    val loadTime:Long=0L,
    val ad:Any?=null
) {

    fun checkIsEx():Boolean{
        return (System.currentTimeMillis() - loadTime) >= 60L * 60L * 1000L
    }
}