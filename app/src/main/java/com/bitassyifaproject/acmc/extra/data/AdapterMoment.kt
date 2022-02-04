package com.bitassyifaproject.acmc.extra.data

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bitassyifaproject.acmc.R
import com.bumptech.glide.Glide

class AdapterMoment (var listMoment : List<MomentModel>,var activity: Activity) : RecyclerView.Adapter<MomentVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_moment,parent,false)
        return MomentVH(view)
    }

    override fun onBindViewHolder(holder: MomentVH, position: Int) {
        var list = listMoment[position]
        holder.moment.text = list.moment
        val linkFoto = "http://202.62.9.138/acmc_api/uploads/moment/${list.name_image}"
        Glide.with(holder.itemView)
            .load(linkFoto)
            .centerCrop()
            .into(holder.imageUrl)
    }

    override fun getItemCount(): Int {
       return listMoment.size
    }
}

class MomentVH (v: View) : RecyclerView.ViewHolder(v){
    var imageUrl = v.findViewById<ImageView>(R.id.imageUrl)
    var moment = v.findViewById<TextView>(R.id.moment)

}
