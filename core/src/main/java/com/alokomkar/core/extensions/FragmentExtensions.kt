package com.alokomkar.core.extensions

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.io.IOException


fun Fragment.handleFailures(failure: Throwable, retry: (() -> Unit)? = null) {
    val message = when (failure) {
        is IOException -> {
            "Check your internet connection"
        }
        else -> failure.localizedMessage
    }

    this.view?.let {
        val isToRetry = retry != null
        val snackBar = Snackbar.make(
            it,
            message ?: "",
            if (isToRetry) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
        )
        if (isToRetry) snackBar.setAction("Retry") { retry?.invoke() }
        val messageTextView =
            snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        messageTextView.setTextColor(Color.WHITE)
        snackBar.show()
    }
}

fun Fragment.showToast( message : Int ) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.isInternetConnected() : Boolean {
    val connectivityManager =
        this.context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}