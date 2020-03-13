package com.qifan.thepsetest.app.book.offer

import com.qifan.thepsetest.app.base.viewmodel.LoadingViewModel
import com.qifan.thepsetest.domain.model.BookModel
import com.qifan.thepsetest.domain.model.Results
import com.qifan.thepsetest.domain.repository.GetOfferRepository
import io.reactivex.Single

class OfferViewModel(
    private val offerRepository: GetOfferRepository
) : LoadingViewModel<Results<ReductionModel>>() {

    fun getReduction(books: List<BookModel>): Single<Results<ReductionModel>> {
        val isbns = books.map { it.isbn.toString() }
        return offerRepository.getReductionOffers(isbns)
            .map { result ->
                when (result) {
                    is Results.Success -> Results.success(result.data.toReductionModel(books))
                    is Results.Failure -> Results.failure(result.error)
                }
            }
            .bindLoadingState(data)
    }

}