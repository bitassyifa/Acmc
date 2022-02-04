package com.bitassyifaproject.acmc.extra.data

import com.bitassyifaproject.acmc.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.GET

interface ExtraAPI {

    @GET("extra/banner")
    fun banner():Call<ResponseAPI>

    @GET("extra/moment")
    fun moment():Call<ResponseAPI>

    @GET("extra/rating")
    fun rating():Call<ResponseAPI>



}