package com.digitalcreative.appguru.data.model

import com.google.gson.annotations.SerializedName

data class Classroom(
    @SerializedName("id_kelas")
    val id: String,

    @SerializedName("nama_kelas")
    val name: String
)