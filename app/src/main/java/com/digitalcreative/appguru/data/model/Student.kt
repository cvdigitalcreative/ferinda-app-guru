package com.digitalcreative.appguru.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Student(
    @SerializedName("nis")
    val id: String,

    @SerializedName("nama")
    val name: String,
) : Parcelable