package com.bitassyifaproject.acmc.order.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.bitassyifaproject.acmc.utils.ResponseAPI
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.reflect.Type
import javax.inject.Inject

class OrderRepo @Inject constructor(val orderAPI: OrderAPI) {
    var resJl = MutableLiveData<List<JenisLayananModel>>()
    var resProduk = MutableLiveData<List<ProdukModel>>()
    var resOrderById = MutableLiveData<List<OrderModel>>()
    var resApi = MutableLiveData<ResponseAPI>()

    fun JenisLayanan(context: Context){
        orderAPI.jenisLayanan().enqueue(object :Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
               val res = response.body()
                if (response.code() == 200){
                    val resData = res?.data
                    val gson = Gson()
                    val listObj : Type = object : TypeToken<List<JenisLayananModel>>() {}.type
                    val output : List<JenisLayananModel> = gson.fromJson(gson.toJson(resData),listObj)
                    resJl.value = output
                }else{
                    Toast.makeText(
                        context,
                        "Jenis layanan tidak ditemukan",
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

    fun produk(id_jl : String ,context: Context){
        orderAPI.produk(id_jl).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()
                if (response.code() == 200){
                    val resData = res?.data
                    val gson = Gson()
                    val listObj : Type = object : TypeToken<List<ProdukModel>>() {}.type
                    val output : List<ProdukModel> = gson.fromJson(gson.toJson(resData),listObj)
                    resProduk.value = output
                }else{
                    Toast.makeText(
                        context,
                        "Produk tidak di temukan",
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

    fun orderById(id_pegawai : String,context: Context){
        orderAPI.orderById(id_pegawai).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()
                if (response.code() == 200){
                    val resData = res?.data
                    val gson = Gson()
                    val listObj : Type = object : TypeToken<List<OrderModel>>() {}.type
                    val output : List<OrderModel> = gson.fromJson(gson.toJson(resData),listObj)
                    resOrderById.value = output

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

    fun order(context: Context,orderInsertModel: OrderInsertModel,file : File){
        val id_pegawai = convert(orderInsertModel.id_pegawai)
        val nama_pegawai = convert(orderInsertModel.nama_pegawai)
        val unit = convert(orderInsertModel.unit)
        val no_tlp = convert(orderInsertModel.no_tlp)
        val id_jl = convert(orderInsertModel.id_jl)
        val id_produk = convert(orderInsertModel.id_produk)
        val catatan = convert(orderInsertModel.catatan)
        val estimasi_tgl = convert(orderInsertModel.estimasi_tgl)
        val materiKonten = convertFile(file)

        orderAPI.order(id_pegawai,nama_pegawai,unit,no_tlp,id_jl,id_produk, catatan, estimasi_tgl,materiKonten).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()
                println(response.message())
                println(response.code())
                if (res != null){
                    if (res.status){
                        resApi.value = res
                    } else {
                        Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Tejadi kesalahan ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Rquest Failed : ${t.printStackTrace()}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun convert (string:String) : RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(),string)
    }
    private fun convertFile(file: File): MultipartBody.Part{
        val reqFile : RequestBody = RequestBody.create("application/*".toMediaTypeOrNull(),file)
        return MultipartBody.Part.createFormData("materi_konten",file.name,reqFile)
    }



}