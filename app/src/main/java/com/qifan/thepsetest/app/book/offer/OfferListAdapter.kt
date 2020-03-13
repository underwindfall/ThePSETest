package com.qifan.thepsetest.app.book.offer

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.qifan.thepsetest.R
import com.qifan.thepsetest.domain.model.BookModel
import com.qifan.thepsetest.extension.inflateLayout
import com.squareup.picasso.Picasso

class OfferListAdapter : RecyclerView.Adapter<OfferListAdapter.ViewHolder>() {
    private val books = mutableListOf<BookModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflateLayout(R.layout.item_book_offer))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(books[position]) {
            Picasso.get()
                .load(cover)
                .into(holder.coverImageView)
            holder.titleTextView.text = title
            holder.priceTextView.text = "$price €"
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
        internal val coverImageView: AppCompatImageView =
            itemView.findViewById(R.id.selection_cover)
        internal val titleTextView: AppCompatTextView = itemView.findViewById(R.id.selection_title)
        internal val priceTextView: AppCompatTextView = itemView.findViewById(R.id.selection_price)
    }

}