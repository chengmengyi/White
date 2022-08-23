package com.demo.white.ad

import com.demo.white.app.print0816

object Load0816AdMa:Load0816Ad() {
    var showingFullAd=false

    fun doLogic(type0816:String,loadOpenTwo:Boolean=true){
        if (loading(type0816)){
            print0816("$type0816 is loading")
            return
        }
        if (isLoaded(type0816)){
            print0816("$type0816 is cache")
            return
        }

        val readLocalAdList = readLocalAdList(type0816)
        if (readLocalAdList.isEmpty()){
            print0816("no $type0816 list data")
            return
        }
        loading0816List.add(type0816)
        iteratorLoadAd(type0816,readLocalAdList.iterator(),loadOpenTwo)
    }

    private fun iteratorLoadAd(type0816: String, ite: Iterator<Conf0816AdEn>,loadOpenTwo:Boolean=true){
        val next = ite.next()
        loadAd(type0816,next){ success,en->
            if (success){
                loading0816List.remove(type0816)
                if (null!=en.ad){
                    loadedAdMap[type0816]=en
                }
            }else{
                if (ite.hasNext()){
                    iteratorLoadAd(type0816,ite,loadOpenTwo)
                }else{
                    loading0816List.remove(type0816)
                    if (type0816==Ad0816Type.OPEN0816&&loadOpenTwo){
                        doLogic(type0816,loadOpenTwo = false)
                    }
                }
            }
        }
    }

    fun getAd(type0816: String)=loadedAdMap[type0816]
}