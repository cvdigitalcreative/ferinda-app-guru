package com.digitalcreative.appguru.data.model

import com.google.gson.annotations.SerializedName

data class Indicator(
    @SerializedName("id_indikator")
    val id: String,

    @SerializedName("indikator")
    val name: String,

    @SerializedName("detail_indikator")
    val items: List<IndicatorItem>
) {
    data class IndicatorItem(
        @SerializedName("id_detail_indikator")
        val id: String,

        @SerializedName("detail_indikator")
        val name: String,

        @SerializedName("pilihan_jawaban")
        val choices: List<Choice>
    ) {
        data class Choice(
            @SerializedName("id_pilihan_jawaban_indikator")
            val id: String,

            @SerializedName("pilihan_jawaban")
            val answer: String
        )
    }
}