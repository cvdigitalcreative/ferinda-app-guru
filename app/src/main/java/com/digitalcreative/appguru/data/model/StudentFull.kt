package com.digitalcreative.appguru.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StudentFull(
    @SerializedName("nis")
    val id: String,

    @SerializedName("nama")
    val name: String,

    @SerializedName("alamat")
    val address: String,

    @SerializedName("jenis_kelamin")
    val gender: String,

    @SerializedName("agama")
    val religion: String,

    val email: String,

    @SerializedName("tempat_lahir")
    val birthplace: String,

    @SerializedName("tanggal_lahir")
    val birthday: String,

    @SerializedName("telepon")
    val phone: String
) : Parcelable