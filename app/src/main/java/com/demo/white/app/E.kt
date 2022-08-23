package com.demo.white.app

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.util.Log
import android.view.View
import com.demo.white.R
import com.demo.white.en.Server0816En


fun Context.jumpGooglePlay() {
    val packName = getPackInfo().packageName
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(
            "https://play.google.com/store/apps/details?id=$packName"
        )
    }
    startActivity(intent)
}

private fun Context.getPackInfo(): PackageInfo {
    val pm = packageManager
    return pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
}

fun Context.shareAppDownPath() {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(
        Intent.EXTRA_TEXT,
        "https://play.google.com/store/apps/details?id=${getPackInfo().packageName}"
    )
    startActivity(Intent.createChooser(intent, "share"))
}


fun getNetWorkStatus(context: Context): Int {
    val connectivityManager = context
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
        if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
            return 2
        } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
            return 0
        }
    } else {
        return 1
    }
    return 1
}


fun View.show(show:Boolean){
    visibility=if (show) View.VISIBLE else View.GONE
}

fun getFlag(c:String)=when(c){
    "United States"->R.drawable.icon_usa
    "Japan"->R.drawable.icon_japan
    else-> R.drawable.icon_fast
}

fun print0816(string: String){
    Log.e("qwer0816",string)
}

fun getServerName(en:Server0816En)=if (en.isFast()) "Faster server" else "${en.country_0816} - ${en.city_0816}"

