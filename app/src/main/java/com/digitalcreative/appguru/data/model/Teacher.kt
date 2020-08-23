package com.digitalcreative.appguru.data.model

import com.google.gson.annotations.SerializedName

data class Teacher(
    @SerializedName("nip")
    val id: String,

    @SerializedName("nama")
    val name: String,

    val email: String
)