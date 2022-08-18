package com.white.unlimited.secure.tool.fast.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.white.unlimited.secure.tool.fast.R
import com.white.unlimited.secure.tool.fast.app.getFlag
import com.white.unlimited.secure.tool.fast.app.getServerName
import com.white.unlimited.secure.tool.fast.en.Server0816En
import com.white.unlimited.secure.tool.fast.ma.Connect0816Ma
import com.white.unlimited.secure.tool.fast.ma.Server0816Ma
import kotlinx.android.synthetic.main.layout_server_item.view.*

class Server0816Adapter(
    private val context: Context,
    private val onclick:(en:Server0816En)->Unit
):RecyclerView.Adapter<Server0816Adapter.MyView>() {
    private val list= arrayListOf<Server0816En>()

    init {
        list.add(Server0816En.createFast())
        list.addAll(Server0816Ma.getServerList())
    }

    inner class MyView(view:View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                onclick.invoke(list[layoutPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(LayoutInflater.from(context).inflate(R.layout.layout_server_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        with(holder.itemView){
            val server0816En = list[position]
            tv_server_name.text= getServerName(server0816En)
            iv_server_flag.setImageResource(getFlag(server0816En.country_0816))
            item_layout.isSelected=server0816En.host_0816==Connect0816Ma.current.host_0816
        }
    }

    override fun getItemCount(): Int = list.size
}