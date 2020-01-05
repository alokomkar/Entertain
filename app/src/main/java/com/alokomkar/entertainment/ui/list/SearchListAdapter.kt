package com.alokomkar.entertainment.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alokomkar.entertainment.R
import com.alokomkar.entertainment.data.local.FeatureLocal
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_feature_show.view.*
import javax.inject.Inject

class SearchListAdapter @Inject constructor() : ListAdapter<FeatureLocal, SearchListAdapter.FeatureViewHolder>(FeatureDiffCallback()) {

    var onItemClickListener : OnItemClickListener ?= null
    var onReadyToLoadMore: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder
            = FeatureViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_feature_show, parent, false),
        onItemClickListener)

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        holder.bindData(getItem(position))
        onReadyToLoadMore?.let {
            if (position == itemCount - 2)
                it.invoke()
        }
    }

    inner class FeatureViewHolder(itemView: View, private val onItemClickListener: OnItemClickListener? )
        : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                if( adapterPosition != RecyclerView.NO_POSITION )
                    onItemClickListener?.onItemClick(getItem(adapterPosition))
            }
            itemView.cbBookmark.setOnClickListener {
                if( adapterPosition != RecyclerView.NO_POSITION ) {
                    itemView.cbBookmark.isChecked = !itemView.cbBookmark.isChecked
                    val item = getItem(adapterPosition)
                    item.isBookmarked = itemView.cbBookmark.isChecked
                    onItemClickListener?.onItemBookmarked(itemView.cbBookmark.isChecked, item)
                }
            }
        }

        fun bindData(item: FeatureLocal) {
            with(itemView) {
                Glide.with(this).asBitmap().load(item.poster).into(ivFeature)
                tvTitle.text = item.title
                tvDate.text = item.year
                tvSubTitle.text = item.type
                cbBookmark.isChecked = item.isBookmarked
            }
        }

    }

    private class FeatureDiffCallback : DiffUtil.ItemCallback<FeatureLocal>() {

        override fun areItemsTheSame(oldItem: FeatureLocal, newItem: FeatureLocal): Boolean
                = oldItem.imdbID == newItem.imdbID

        override fun areContentsTheSame(oldItem: FeatureLocal, newItem: FeatureLocal): Boolean
                = oldItem == newItem

    }


}