package com.alokomkar.entertainment.ui.model

import com.alokomkar.core.networking.Response
import com.alokomkar.entertainment.data.local.FeatureLocal
import com.alokomkar.entertainment.data.remote.Feature
import com.alokomkar.entertainment.data.remote.FeatureResponse
import com.alokomkar.entertainment.data.remote.ShowDetails
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface DataContract {

    interface Repository {
        val fetchShowsOutcome: PublishSubject<Response<List<FeatureLocal>>>
        val fetchShowDetails: PublishSubject<Response<ShowDetails>>
        fun fetchShowDetails( imdbID: String )
        fun fetchShows(internetConnected: Boolean, pageIndex : Int )
        fun fetchFromRemote(pageIndex: Int)
        fun saveShows( shows : List<FeatureLocal> )
        fun handleError( error: Throwable )
    }

    interface Local {
        fun saveShows( shows : List<FeatureLocal>)
        fun fetchAllShows(): Flowable<List<FeatureLocal>>
    }

    interface Remote {
        fun fetchShows( pageIndex : Int ): Observable<FeatureResponse>
        fun fetchShowById( imdbID : String ): Single<ShowDetails>
    }

}