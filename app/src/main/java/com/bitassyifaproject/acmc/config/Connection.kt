package com.bitassyifaproject.acmc.config

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Connection {
    companion object {

        val BASE_URL_LOGIN = "http://202.62.9.138:1234/"
        val BASE_URL = "http://202.62.9.138/acmc_api/"

        var okHttpClient: OkHttpClient? = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        fun urlLogin():Retrofit{
            val connection = Retrofit
                .Builder()
                .baseUrl(BASE_URL_LOGIN)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return connection
        }

        fun urlCon():Retrofit{
            val connection = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return connection
        }
    }
}



