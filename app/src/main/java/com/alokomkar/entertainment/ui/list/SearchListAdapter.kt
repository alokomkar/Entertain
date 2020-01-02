package com.alokomkar.entertainment.ui.list

import android.view.View
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alokomkar.entertainment.data.local.FeatureLocal

class SearchListAdapter : ListAdapter<FeatureLocal, SearchListAdapter.FeatureViewHolder>() {


    var onItemClickListener : OnItemClickListener ?= null

    inner class FeatureViewHolder(itemView: View, private val onItemClickListener: OnItemClickListener? )
        : RecyclerView.ViewHolder(itemView) {

    }

    interface OnItemClickListener {
        fun onItemClick(item : FeatureLocal)
    }

    private class FeatureDiffCallback :
}