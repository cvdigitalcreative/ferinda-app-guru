package com.digitalcreative.appguru.data.model

import com.google.gson.annotations.SerializedName

data class Indicator(
    @SerializedName("id_indikator")
    val id: String,

    @SerializedName("indikator")
    val name: String
)