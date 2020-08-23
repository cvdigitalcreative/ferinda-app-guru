package com.digitalcreative.appguru.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Assignment(
    @SerializedName("id_tugas")
    val id: String,

    @SerializedName("nama_tugas")
    val title: String,

    @SerializedName("deskripsi_tugas")
    val description: String
) : Parcelable {
    @Parcelize
    data class Section(
        @SerializedName("id_grup_soal")
        val id: String,

        @SerializedName("grup_soal")
        val section: String,

        @SerializedName("soal")
        val questions: List<Question>
    ) : Parcelable {
        @Parcelize
        data class Question(
            @SerializedName("id_soal")
            val id: String,

            @SerializedName("soal")
            val question: String,

            @SerializedName("pilihan_jawab")
            val choices: List<Choice>
        ) : Parcelable {
            @Parcelize
            data class Choice(
                @SerializedName("id_pilihan_jawaban")
                val id: String,

                @SerializedName("pilihan_jawaban")
                val choice: String
            ) : Parcelable
        }
    }
}
