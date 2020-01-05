package com.alokomkar.core.extensions

import android.view.View

fun View.changeVisibility(isVisible: Boolean) {
    if( isVisible ) this.show() else this.hide()
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}
