package com.alokomkar.entertainment.ui.model

import com.alokomkar.core.networking.Response
import com.alokomkar.entertainment.data.local.Bookmark
import com.alokomkar.entertainment.data.local.FeatureLocal
import com.alokomkar.entertainment.data.remote.FeatureResponse
import com.alokomkar.entertainment.data.remote.ShowDetails
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface DataContract {

    interface Repository {
        val fetchShowsOutcome: PublishSubject<Response<List<FeatureLocal>>>
        val bookmarksOutcome: PublishSubject<Response<List<Bookmark>>>
        val fetchShowDetails: PublishSubject<Response<ShowDetails>>
        fun fetchShowDetails( imdbID: String )
        fun fetchShows(internetConnected: Boolean, searchQuery: String, pageIndex : Int )
        fun fetchFromRemote(searchQuery: String, pageIndex: Int)
        fun saveShows( shows : List<FeatureLocal> )
        fun bookmark(bookmarked: Boolean, item: FeatureLocal)
        fun bookmark(bookmarked: Boolean, item: Bookmark)
        fun handleError( error: Throwable )
        fun fetchBookmarks()
        fun performSearch(
            isConnected: Boolean,
            observableFromView: Observable<String>,
            pageIndex: Int
        )
    }

    interface Local {
        fun saveShows( shows : List<FeatureLocal>)
        fun fetchAllShows(): Flowable<List<FeatureLocal>>
        fun bookmark(bookmarked: Boolean, item: Bookmark)
        fun fetchAllBookmarks(): Flowable<List<Bookmark>>
    }

    interface Remote {
        fun fetchShows(searchQuery: String, pageIndex : Int ): Observable<FeatureResponse>
        fun fetchShowById( imdbID : String ): Single<ShowDetails>
    }

}