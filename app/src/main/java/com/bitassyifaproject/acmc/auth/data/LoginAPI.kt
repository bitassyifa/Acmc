package com.bitassyifaproject.acmc.auth.data


import com.bitassyifaproject.acmc.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {
    @POST("hrd/api/")
    fun login(@Body loginModel : LoginModel) : Call<ResponseAPI>
}