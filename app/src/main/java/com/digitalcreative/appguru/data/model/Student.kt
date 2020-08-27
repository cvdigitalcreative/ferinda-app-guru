package com.digitalcreative.appguru.data.model

import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("nis")
    val id: String,

    @SerializedName("nama")
    val name: String,
)