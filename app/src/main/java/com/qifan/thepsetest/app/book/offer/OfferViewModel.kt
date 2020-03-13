package com.qifan.thepsetest.app.book.offer

import com.qifan.thepsetest.app.base.viewmodel.LoadingViewModel
import com.qifan.thepsetest.domain.model.OfferModel
import com.qifan.thepsetest.domain.model.Results
import com.qifan.thepsetest.domain.repository.GetOfferRepository
import io.reactivex.Single

class OfferViewModel(
    private val offerRepository: GetOfferRepository
) : LoadingViewModel<Results<OfferModel>>() {

    fun getOffer(isbns: List<String>): Single<Results<OfferModel>> {
        return offerRepository.getReductionOffers(isbns)
            .bindLoadingState(data)
    }


}