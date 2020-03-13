package com.qifan.thepsetest.data

import com.qifan.thepsetest.data.api.BookApi
import com.qifan.thepsetest.data.transformer.offerResponseToModelResult
import com.qifan.thepsetest.domain.model.OfferModel
import com.qifan.thepsetest.domain.model.Results
import com.qifan.thepsetest.domain.repository.GetOfferRepository
import com.qifan.thepsetest.extension.reactive.io
import io.reactivex.Single

class GetOfferRepositoryImpl(private val api: BookApi) : GetOfferRepository {
    override fun getReductionOffers(isbns: List<String>): Single<Results<OfferModel>> {
        return api.getReductionOffers(isbns.joinToString())
            .compose(offerResponseToModelResult())
            .io()
    }
}