package com.digitalcreative.appguru.data.model

import com.google.gson.annotations.SerializedName

data class GroupAnswer(
    @SerializedName("id_grup_pilihan")
    val id: String,

    @SerializedName("grup_pilihan")
    val group: String
)