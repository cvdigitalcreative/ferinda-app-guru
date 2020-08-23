package com.digitalcreative.appguru.data.model

import com.google.gson.annotations.SerializedName

data class Gender(
    @SerializedName("id_jenis_kelamin")
    val id: String,

    @SerializedName("jenis_kelamin")
    val name: String
)