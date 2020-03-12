package com.qifan.thepsetest.app.book.list

import com.qifan.thepsetest.app.base.viewmodel.LoadingViewModel
import com.qifan.thepsetest.domain.model.BookModel
import com.qifan.thepsetest.domain.model.Results
import com.qifan.thepsetest.domain.repository.GetBooksRepository
import io.reactivex.Single

class BookListViewModel(
    private val booksRepository: GetBooksRepository
) : LoadingViewModel<Results<List<BookModel>>>() {

    fun getBooks(): Single<Results<List<BookModel>>> {
        return booksRepository.getBooks()
            .bindLoadingState(data)
    }

}
