package com.alokomkar.entertainment.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alokomkar.entertainment.R
import com.alokomkar.entertainment.data.local.FeatureLocal
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_feature_show.view.*
import javax.inject.Inject

class SearchListAdapter @Inject constructor(private val picasso: Picasso) : ListAdapter<FeatureLocal, SearchListAdapter.FeatureViewHolder>(FeatureDiffCallback()) {

    var onItemClickListener : OnItemClickListener ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder
            = FeatureViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_feature_show, parent, false),
        onItemClickListener)

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class FeatureViewHolder(itemView: View, private val onItemClickListener: OnItemClickListener? )
        : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                if( adapterPosition != RecyclerView.NO_POSITION )
                    onItemClickListener?.onItemClick(getItem(adapterPosition))
            }
        }

        fun bindData(item: FeatureLocal) {
            with(itemView) {
                picasso.load(item.poster).into(ivFeature)
                tvTitle.text = item.title
                tvDate.text = item.year
                tvSubTitle.text = item.type
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(item : FeatureLocal)
    }

    private class FeatureDiffCallback : DiffUtil.ItemCallback<FeatureLocal>() {

        override fun areItemsTheSame(oldItem: FeatureLocal, newItem: FeatureLocal): Boolean
                = oldItem.imdbID == newItem.imdbID

        override fun areContentsTheSame(oldItem: FeatureLocal, newItem: FeatureLocal): Boolean
                = oldItem == newItem

    }


}