package com.qifan.thepsetest.domain.repository

import com.qifan.thepsetest.domain.model.OfferModel
import com.qifan.thepsetest.domain.model.Results
import io.reactivex.Single

interface GetOfferRepository {
    fun getReductionOffers(isbns: List<String>): Single<Results<OfferModel>>
}