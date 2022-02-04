package com.bitassyifaproject.acmc.auth.data

class LoginModel (
    var username: String ,
    var password: String 
        )
{}

class LoginResponse(
    var id_pegawai : String  ,
    var email : String ,
    var nama_pegawai : String  ,
    var foto : String  ,
    var no_telp : String  ,
    var unit : String  ,
    var bidang : String,
    var tgl_lahir : String,
    var alamat_tinggal : String,
    var email_umum : String ,
    var nip : String  
)
{}