package com.bitassyifaproject.acmc.order.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bitassyifaproject.acmc.R

class JenisLayananAdapter (var dataSource : List<JenisLayananModel>,val context: Context): BaseAdapter(){
    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var idJl : String? = null
    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(p0: Int): Any {
       return dataSource[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View
        val vh : ItemHolder
        if (convertView == null){
            view = inflater.inflate(R.layout.item_jl,parent,false)
            vh = ItemHolder(view)
            view?.tag =vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.nameJl.text = dataSource.get(position).jenis_layanan
        idJl = dataSource.get(position).id_jl
        return view
    }

    class ItemHolder (row : View?){
        val nameJl : TextView
        init {
            nameJl = row?.findViewById(R.id.jl_name) as TextView
        }
    }
}