package com.digitalcreative.appguru.data.model

import com.google.gson.annotations.SerializedName

data class Religion(
    @SerializedName("id_agama")
    val id: String,

    @SerializedName("agama")
    val name: String
)