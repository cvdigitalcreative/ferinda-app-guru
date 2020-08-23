package com.digitalcreative.appguru.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Classroom(
    @SerializedName("id_kelas")
    val id: String,

    @SerializedName("nama_kelas")
    val name: String
) : Parcelable