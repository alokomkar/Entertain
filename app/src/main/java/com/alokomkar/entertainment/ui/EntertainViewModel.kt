package com.alokomkar.entertainment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alokomkar.core.extensions.toLiveData
import com.alokomkar.core.networking.Response
import com.alokomkar.entertainment.data.local.FeatureLocal
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
        repository.postFetchShowsOutcome.toLiveData(compositeDisposable)
    }

    private val selectedItemMutableLiveData: MutableLiveData<FeatureLocal> = MutableLiveData()

    fun fetchShows() {
        repository.fetchShows(pageIndex++)
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

}