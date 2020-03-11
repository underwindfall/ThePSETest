package com.qifan.thepsetest.data.entity

import com.google.gson.annotations.SerializedName

data class BookEntity(
    @SerializedName("isbn")
    val isbn: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("cover")
    val cover: String?,

    @SerializedName("price")
    val price: Int?,

    @SerializedName("synopsis")
    val synopsis: List<String>?
)