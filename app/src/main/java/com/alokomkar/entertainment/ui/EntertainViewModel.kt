package com.alokomkar.entertainment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alokomkar.core.extensions.toLiveData
import com.alokomkar.core.networking.Response
import com.alokomkar.entertainment.data.local.Bookmark
import com.alokomkar.entertainment.data.local.FeatureLocal
import com.alokomkar.entertainment.data.remote.ShowDetails
import com.alokomkar.entertainment.di.ActivityScope
import com.alokomkar.entertainment.ui.model.DataContract
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScope
class EntertainViewModel @Inject constructor(
    private val repository: DataContract.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private var pageIndex = 1

    val showsListLiveData: LiveData<Response<List<FeatureLocal>>> by lazy {
        repository.fetchShowsOutcome.toLiveData(compositeDisposable)
    }

    val bookmarksLiveData: LiveData<Response<List<Bookmark>>> by lazy {
        repository.bookmarksOutcome.toLiveData(compositeDisposable)
    }

    val showDetailsLiveData: LiveData<Response<ShowDetails>> by lazy {
        repository.fetchShowDetails.toLiveData(compositeDisposable)
    }

    private val selectedItemMutableLiveData: MutableLiveData<FeatureLocal> = MutableLiveData()

    fun fetchShows(internetConnected: Boolean) {
        repository.fetchShows(internetConnected, pageIndex++)
    }

    fun decrementPageIndex() {
        if( pageIndex > 1 )
        pageIndex--
    }

    fun resetPageIndex() {
        pageIndex = 1
    }

    fun setSelectedItem( item: FeatureLocal ) {
        selectedItemMutableLiveData.value = item
    }

    fun getSelectedItem() : LiveData<FeatureLocal> = selectedItemMutableLiveData

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun refresh() {
        repository.fetchFromRemote(pageIndex++)
    }

    fun fetchShowById(imdbID: String) {
        repository.fetchShowDetails(imdbID)
    }

    fun onItemBookmarked(bookmarked: Boolean, item: FeatureLocal) {
        repository.bookmark(bookmarked, item)
    }

    fun onItemBookmarked(bookmarked: Boolean, item: Bookmark) {
        repository.bookmark(bookmarked, item)
    }

    fun fetchBookmarks() {
        repository.fetchBookmarks()
    }

}