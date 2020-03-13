package com.qifan.thepsetest.app.book.offer

import com.qifan.thepsetest.domain.model.BookModel
import com.qifan.thepsetest.domain.model.OfferModel
import kotlin.math.truncate

data class ReductionModel(
    val totalPrice: Int,
    val reduction: String,
    val result: String
)


fun OfferModel.toReductionModel(books: List<BookModel>): ReductionModel {
    val totalPrice = books.sumBy { it.price }
    val reductionPrices: List<Double> = offers.map { offerItem ->
        when (offerItem.type) {
            OfferModel.Type.PERCENTAGE -> totalPrice * (offerItem.value / 100.00)
            OfferModel.Type.MINUS -> offerItem.value.toDouble()
            OfferModel.Type.SLICE -> {
                offerItem.sliceValue?.run {
                    if (totalPrice >= this) {
                        offerItem.value.toDouble()
                    } else {
                        0.00
                    }
                } ?: 0.00
            }
            else -> 0.00
        }
    }

    val minReduction = reductionPrices.max() ?: 0.00
    return ReductionModel(
        totalPrice,
        truncate(minReduction).toString(),
        (totalPrice - truncate(minReduction)).toString()
    )

}