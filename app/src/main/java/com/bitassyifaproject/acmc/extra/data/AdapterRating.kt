package com.bitassyifaproject.acmc.extra.data

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bitassyifaproject.acmc.R
import com.bumptech.glide.Glide

class AdapterRating (val listRating : List<RatingModel>,var activity: Activity) : RecyclerView.Adapter<RatingVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rating,parent,false)
        return RatingVH(view)
    }

    override fun onBindViewHolder(holder: RatingVH, position: Int) {
        val listPosition = listRating[position]
        holder.nama.text = listPosition.nama_pegawai
        holder.not.text = "'${listPosition.pesan}'"
        var linkFoto= "http://202.62.9.138:1234/hrd/php/foto/${listPosition.foto}"
        Glide.with(activity)
            .load(linkFoto)
            .centerCrop()
            .into(holder.img)
        holder.rate.rating = listPosition.star.toFloat()
    }

    override fun getItemCount(): Int {
        return listRating.size
    }


}

class RatingVH(v:View) : RecyclerView.ViewHolder(v) {

    var img = v.findViewById<ImageView>(R.id.imageProfil)
    var nama = v.findViewById<TextView>(R.id.penilai)
    var not = v.findViewById<TextView>(R.id.notice)
    var rate = v.findViewById<RatingBar>(R.id.ratingstar)

}
