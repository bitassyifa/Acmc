package com.bitassyifaproject.acmc.extra.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.bitassyifaproject.acmc.utils.ResponseAPI
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class ExtraRepo @Inject constructor(val extraAPI: ExtraAPI) {
    val resBanner = MutableLiveData<List<BannerModel>>()
    val resMoment = MutableLiveData<List<MomentModel>>()
    val resRating = MutableLiveData<List<RatingModel>>()


    fun banner(context: Context){
        extraAPI.banner().enqueue(object :  Callback<ResponseAPI> {
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
               val res = response.body()
                if (response.code()==200){
                    val resData = res?.data
                    val gson = Gson()
                    val listObj : Type = object : TypeToken<List<BannerModel>>() {}.type
                    val output : List<BannerModel> = gson.fromJson(gson.toJson(resData),listObj)
                    resBanner.value = output
                }else{
                    Toast.makeText(
                        context,
                        "banner not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Error : ${t.printStackTrace()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun moment(context: Context){
        extraAPI.moment().enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()
                if (response.code() == 200){
                    val resData = res?.data
                    val gson = Gson()
                    val listObj : Type = object : TypeToken<List<MomentModel>>() {}.type
                    val output : List<MomentModel> = gson.fromJson(gson.toJson(resData),listObj)
                    resMoment.value = output
                }else{
                    Toast.makeText(
                        context,
                        "moment not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Error : ${t.printStackTrace()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun rating(context: Context){
        extraAPI.rating().enqueue(object : Callback<ResponseAPI> {
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()
                if (response.code() == 200){
                    val resdData  = res?.data
                    val gson = Gson()
                    val listObj : Type = object : TypeToken<List<RatingModel>>() {}.type
                    val output : List<RatingModel> = gson.fromJson(gson.toJson(resdData),listObj)
                    resRating.value = output
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Error : ${t.printStackTrace()}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}