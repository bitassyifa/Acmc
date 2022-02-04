package com.bitassyifaproject.acmc.order.data

import com.bitassyifaproject.acmc.utils.ResponseAPI
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface OrderAPI  {
    @GET("order/jenis_layanan")
    fun jenisLayanan():Call<ResponseAPI>

    @GET("order/produk/{id_jl}")
    fun produk( @Path("id_jl")id_jl : String):Call<ResponseAPI>

    @GET("order/order_user/{id_pegawai}")
    fun orderById(@Path("id_pegawai")id_pegawai : String): Call<ResponseAPI>

    @Multipart
    @POST("order")
    fun order(@Part("id_pegawai")id_pegawai : RequestBody,
              @Part("nama_pegawai")nama_pegawai : RequestBody,
              @Part("unit")unit : RequestBody,
              @Part("no_tlp")no_tlp : RequestBody,
              @Part("id_jl")id_jl : RequestBody,
              @Part("id_produk")id_produk : RequestBody,
              @Part("catatan")catatan : RequestBody,
              @Part("estimasi_tgl")estimasi_tgl : RequestBody,
              @Part materi_konten : MultipartBody.Part? = null) :Call<ResponseAPI>
}