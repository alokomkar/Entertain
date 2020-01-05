package com.alokomkar.entertainment.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alokomkar.entertainment.R
import com.alokomkar.entertainment.data.local.Bookmark
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_bookmarked_show.view.*
import kotlinx.android.synthetic.main.item_feature_show.view.*
import kotlinx.android.synthetic.main.item_feature_show.view.cbBookmark
import kotlinx.android.synthetic.main.item_feature_show.view.ivFeature
import kotlinx.android.synthetic.main.item_feature_show.view.tvDate
import kotlinx.android.synthetic.main.item_feature_show.view.tvSubTitle
import kotlinx.android.synthetic.main.item_feature_show.view.tvTitle
import javax.inject.Inject

class BookmarksAdapter @Inject constructor() : ListAdapter<Bookmark, BookmarksAdapter.BookmarkViewHolder>(FeatureDiffCallback()) {

    var onItemClickListener : OnItemClickListener ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder
            = BookmarkViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_bookmarked_show, parent, false),
        onItemClickListener)

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class BookmarkViewHolder(itemView: View, private val onItemClickListener: OnItemClickListener? )
        : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                if( adapterPosition != RecyclerView.NO_POSITION )
                    onItemClickListener?.onItemClick(getItem(adapterPosition))
            }
            itemView.cbBookmark.setOnClickListener {
                if( adapterPosition != RecyclerView.NO_POSITION ) {
                    val item = getItem(adapterPosition)
                    item.isBookmarked = false
                    onItemClickListener?.onItemBookmarked(false, item)
                    notifyItemRemoved(adapterPosition)
                }
            }
        }

        fun bindData(item: Bookmark) {
            with(itemView) {
                Glide.with(this).asBitmap().load(item.poster).into(ivFeature)
                tvTitle.text = item.title
                tvDate.text = item.year
                tvSubTitle.text = item.type
                cbBookmark.isSelected = true
            }
        }

    }

    private class FeatureDiffCallback : DiffUtil.ItemCallback<Bookmark>() {

        override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean
                = oldItem.imdbID == newItem.imdbID

        override fun areContentsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean
                = oldItem == newItem

    }


}