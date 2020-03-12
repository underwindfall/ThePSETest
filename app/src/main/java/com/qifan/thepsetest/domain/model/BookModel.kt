package com.qifan.thepsetest.domain.model

data class BookModel(
    val isbn: String?,
    val title: String?,
    val cover: String?,
    val price: Int?,
    val synopsis: List<String>?,
    var selected: Boolean = false
)