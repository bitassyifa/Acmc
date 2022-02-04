package com.bitassyifaproject.acmc.extra.data

class ExtraModel(

)
{}
class BannerModel(
    val id_banner : String,
    val name_image : String,
    val link : String
){}

class MomentModel(
    val id_moment : String ,
    val moment : String ,
    val name_image : String ,
    val status : String
){}

class RatingModel(
    val id_rating : String,
    val id_pegawai : String ,
    val nama_pegawai : String ,
    val unit : String ,
    val star : String ,
    val pesan : String ,
    val foto : String ,
    val created_date : String
){}

