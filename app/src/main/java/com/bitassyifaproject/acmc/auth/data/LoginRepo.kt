package com.bitassyifaproject.acmc.auth.data

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

class LoginRepo @Inject constructor(val loginAPI: LoginAPI){
    var resAPI = MutableLiveData<ResponseAPI>()
    var resLogin = MutableLiveData<LoginResponse>()


    fun login(loginModel: LoginModel,context: Context){
        loginAPI.login(loginModel).enqueue(object :  Callback<ResponseAPI> {
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()
                if (response.code() == 200){

                    val resData = res?.data
                    val gson = Gson()
                    val listObj : Type = object : TypeToken<List<LoginResponse>>() {}.type
                    val output : List<LoginResponse> = gson.fromJson(gson.toJson(resData),listObj)
                    resLogin.value = output[0]
                    resAPI.value = res
                } else {
                    Toast.makeText(
                        context,
                        "Id atau Password salah!  Cobalagi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}