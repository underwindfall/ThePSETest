package com.qifan.thepsetest.data.mapper

import com.qifan.thepsetest.data.entity.BookEntity
import com.qifan.thepsetest.data.entity.OfferEntity
import com.qifan.thepsetest.domain.model.BookModel
import com.qifan.thepsetest.domain.model.OfferModel

fun BookEntity.toModel(): BookModel {
    return BookModel(
        isbn = isbn,
        title = title,
        cover = cover,
        price = price,
        synopsis = synopsis
    )
}


fun OfferEntity.toModel(): OfferModel {
    return OfferModel(
        offers = offers?.map { it.toModel() }
    )
}

fun OfferEntity.OffersItem.toModel(): OfferModel.OffersModelItem {
    return OfferModel.OffersModelItem(
        sliceValue = sliceValue,
        type = type,
        value = value
    )
}