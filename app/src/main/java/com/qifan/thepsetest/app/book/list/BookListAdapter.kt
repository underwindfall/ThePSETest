package com.qifan.thepsetest.app.book.list

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.qifan.thepsetest.R
import com.qifan.thepsetest.domain.model.BookModel
import com.qifan.thepsetest.extension.inflateLayout
import com.squareup.picasso.Picasso
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor

class BookListAdapter :
    RecyclerView.Adapter<BookListAdapter.ViewHolder>() {
    private val books = mutableListOf<BookModel>()
    private val mSelectedList = PublishProcessor.create<List<BookModel>>()

    val selectedList: Flowable<List<BookModel>> = mSelectedList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflateLayout(R.layout.item_book))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(books[position]) {
            Picasso.get()
                .load(cover)
                .into(holder.coverImageView)

            holder.titleTextView.text = title
            holder.priceTextView.text = "$price €"
            holder.buttonAddRemoveButton.apply {
                setOnClickListener {
                    selected = !selected
                    text = if (selected) {
                        context.getString(R.string.remove)
                    } else {
                        context.getString(R.string.add)
                    }
                    mSelectedList.onNext(books.filter { it.selected })
                }
            }
        }
    }

    override fun getItemCount(): Int = books.size

    fun setData(data: List<BookModel>) {
        books.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val coverImageView: AppCompatImageView = itemView.findViewById(R.id.cover)
        internal val titleTextView: AppCompatTextView = itemView.findViewById(R.id.title)
        internal val priceTextView: AppCompatTextView = itemView.findViewById(R.id.price)
        internal val buttonAddRemoveButton: AppCompatButton =
            itemView.findViewById(R.id.button_add_remove)
    }

}