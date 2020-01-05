package com.alokomkar.entertainment.ui.model

import com.alokomkar.entertainment.data.remote.FeatureAPI
import com.alokomkar.entertainment.data.remote.FeatureResponse
import com.alokomkar.entertainment.data.remote.ShowDetails
import com.alokomkar.entertainment.di.ActivityScope
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

@ActivityScope
class RemoteImpl @Inject constructor(
    private val api : FeatureAPI
) : DataContract.Remote {

    override fun fetchShows(searchQuery: String, pageIndex: Int): Observable<FeatureResponse> = api.search(searchQuery, pageIndex)
    override fun fetchShowById(imdbID: String): Single<ShowDetails> = api.searchById(imdbID)

}