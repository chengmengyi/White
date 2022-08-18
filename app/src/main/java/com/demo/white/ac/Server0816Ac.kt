package com.demo.white.ac

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.white.R
import com.demo.white.en.Server0816En
import com.demo.white.ma.Connect0816Ma
import com.demo.white.recyclerview.Server0816Adapter
import kotlinx.android.synthetic.main.layout_server.*

class Server0816Ac:Base0816Ac(R.layout.layout_server) {
    override fun initView() {
        iv_back.setOnClickListener { finish() }

        recycler_view.apply {
            layoutManager=LinearLayoutManager(this@Server0816Ac)
            adapter=Server0816Adapter(this@Server0816Ac){
                click(it)
            }
        }
    }

    private fun click(en:Server0816En){
        var click=""
        val current = Connect0816Ma.current
        val connected = Connect0816Ma.isConnected()
        if (current.host_0816==en.host_0816){
            if (!connected){
                click="connect"
            }
        }else{
            click=if (connected) "disconnect" else "connect"
        }
        if (click=="disconnect"){
            showDialog { back(click,en) }
        }else{
            back(click,en)
        }
    }

    private fun back(click:String,en: Server0816En){
        Connect0816Ma.current=en
        Connect0816Ma.action=click
        finish()
    }

    private fun showDialog(sure:()->Unit){
        AlertDialog.Builder(this).apply {
            setMessage("You are currently connected and need to disconnect before manually connecting to the server.")
            setPositiveButton("sure", { dialog, which ->
                sure.invoke()
            })
            setNegativeButton("cancel",null)
            show()
        }
    }
}