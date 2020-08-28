package com.digitalcreative.appguru.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Submitted(
    @SerializedName("jawaban_soal")
    val data: List<AssignmentSubmitted>
) : Parcelable {
    @Parcelize
    data class AssignmentSubmitted(
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

            @SerializedName("jawaban")
            val choices: Choice
        ) : Parcelable {
            @Parcelize
            data class Choice(
                @SerializedName("id_jawaban_soal")
                val id: String,

                @SerializedName("pilihan_jawaban")
                val choice: String
            ) : Parcelable
        }
    }
}