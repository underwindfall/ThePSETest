package com.qifan.thepsetest.data.mapper

import com.qifan.thepsetest.data.entity.BookEntity
import com.qifan.thepsetest.domain.model.BookModel

fun BookEntity.toModel(): BookModel {
    return BookModel(
        isbn = isbn,
        title = title,
        cover = cover,
        price = price,
        synopsis = synopsis
    )
}