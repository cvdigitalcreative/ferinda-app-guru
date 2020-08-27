package com.digitalcreative.appguru.data.model

import com.google.gson.annotations.SerializedName

data class Answer(
    @SerializedName("id_pilihan_jawaban")
    val id: String,

    @SerializedName("pilihan_jawaban")
    val answer: String
)