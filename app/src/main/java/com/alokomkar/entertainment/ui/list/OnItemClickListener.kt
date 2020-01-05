package com.alokomkar.entertainment.ui.list

interface OnItemClickListener {
    fun onItemClick(item : Any)
    fun onItemBookmarked(isBookmarked: Boolean, item : Any)
}