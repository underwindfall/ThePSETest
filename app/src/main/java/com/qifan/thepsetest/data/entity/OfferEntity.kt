package com.qifan.thepsetest.data.entity

import com.google.gson.annotations.SerializedName

data class OfferEntity(
    @SerializedName("offers")
    val offers: List<OffersItem>?
) {
    data class OffersItem(
        @SerializedName("sliceValue")
        val sliceValue: Int?,

        @SerializedName("type")
        val type: String?,

        @SerializedName("value")
        val value: Int?
    )
}