package com.qifan.thepsetest.data.transformer

import com.qifan.thepsetest.data.entity.BookEntity
import com.qifan.thepsetest.data.entity.OfferEntity
import com.qifan.thepsetest.data.mapper.toModel
import com.qifan.thepsetest.domain.model.BookModel
import com.qifan.thepsetest.domain.model.OfferModel
import com.qifan.thepsetest.domain.model.Results
import com.qifan.thepsetest.domain.processApiResponse
import io.reactivex.Single
import io.reactivex.SingleTransformer
import retrofit2.Response


fun bookResponseToModelResult(): SingleTransformer<Response<List<BookEntity>>, Results<List<BookModel>>> {
    return SingleTransformer { upstream ->
        upstream.flatMap { response ->
            when (val book = processApiResponse(response)) {
                is Results.Success -> Single.just(Results.success(result = book.data.map { it.toModel() }))
                is Results.Failure -> Single.just(Results.failure(error = book.error))
            }
        }
    }
}


fun offerResponseToModelResult(): SingleTransformer<Response<OfferEntity>, Results<OfferModel>> {
    return SingleTransformer { upstream ->
        upstream.flatMap { response ->
            when (val offer = processApiResponse(response)) {
                is Results.Success -> Single.just(Results.success(result = offer.data.toModel()))
                is Results.Failure -> Single.just(Results.failure(error = offer.error))
            }
        }
    }
}