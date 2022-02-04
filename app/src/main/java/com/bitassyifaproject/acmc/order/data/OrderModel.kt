package com.bitassyifaproject.acmc.order.data

class OrderModel(
    val id_order : String,
    val id_pegawai : String,
    val nama_pegawai : String,
    val unit : String,
    val no_tlp : String,
    val id_jl : String,
    val id_produk : String,
    val materi_konten : String,
    val catatan : String,
    val estimasi_tgl : String,
    val status : String,
) {
}

class OrderInsertModel(
    val id_pegawai : String,
    val nama_pegawai : String,
    val unit : String,
    val no_tlp : String,
    val id_jl : String,
    val id_produk : String,
    val catatan : String,
    val estimasi_tgl : String,
) {
}

class JenisLayananModel(
    val id_jl : String ,
    val jenis_layanan : String
){}

class ProdukModel(
    val id_produk : String ,
    val nama_produk : String ,
)
{}

class OrderByIdModel(
    val id_order : String ,
    val nama_pegawai : String ,
    val nama_produk : String ,
    val catatan : String ,
    val status : String ,
    val nama_pic : String ,
    val tgl_pesan : String
){}
