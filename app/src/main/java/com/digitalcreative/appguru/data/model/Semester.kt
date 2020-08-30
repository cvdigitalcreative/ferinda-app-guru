package com.digitalcreative.appguru.data.model

import com.google.gson.annotations.SerializedName

data class Semester(
    @SerializedName("id_semester")
    val id: String,

    @SerializedName("semester")
    val name: String
)