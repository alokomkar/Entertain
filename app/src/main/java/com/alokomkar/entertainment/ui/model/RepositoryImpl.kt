package com.alokomkar.entertainment.ui.model

import com.alokomkar.core.extensions.*
import com.alokomkar.core.networking.Response
import com.alokomkar.core.networking.Scheduler
import com.alokomkar.entertainment.data.local.Bookmark
import com.alokomkar.entertainment.data.local.FeatureLocal
import com.alokomkar.entertainment.data.remote.Feature
import com.alokomkar.entertainment.data.remote.ShowDetails
import com.alokomkar.entertainment.di.ActivityScope
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ActivityScope
class RepositoryImpl @Inject constructor(
    private val local: DataContract.Local,
    private val remote: DataContract.Remote,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : DataContract.Repository {

    private var prevPageIndex = -1
    private val allShows : ArrayList<FeatureLocal> = ArrayList()
    private val localList : ArrayList<FeatureLocal> = ArrayList()

    override val fetchShowsOutcome: PublishSubject<Response<List<FeatureLocal>>>
            = PublishSubject.create<Response<List<FeatureLocal>>>()

    override val fetchShowDetails: PublishSubject<Response<ShowDetails>>
            = PublishSubject.create<Response<ShowDetails>>()

    override val bookmarksOutcome: PublishSubject<Response<List<Bookmark>>>
            = PublishSubject.create<Response<List<Bookmark>>>()

    override fun fetchShows(internetConnected: Boolean, searchQuery: String, pageIndex: Int) {
        fetchShowsOutcome.loading(true)
        if( pageIndex == 1 ) {
            allShows.clear()
        }
        local.fetchAllShows()
            .performOnBackOutOnMain(scheduler)
            .doAfterNext {
                if( internetConnected && prevPageIndex != pageIndex ) {
                    fetchFromRemote(searchQuery, pageIndex)
                }
            }
            .subscribe ({
                if( internetConnected )
                    allShows.addAll(it)
                else {
                    if(!allShows.containsAll(it))
                        allShows.addAll(it)
                }
                fetchShowsOutcome.success(allShows)
            },
                { handleError(it) })
            .addTo(compositeDisposable)
    }

    override fun fetchFromRemote(searchQuery: String, pageIndex: Int) {
        prevPageIndex = pageIndex
        fetchShowsOutcome.loading(true)
        remote.fetchShows(searchQuery, pageIndex)
            .map { response ->
                localList.clear()
                response.search.forEach {
                    localList.add(mapToFeatureLocal(it))
                }
                saveShows(localList)
                localList
            }
            .performOnBackOutOnMain(scheduler)
            .subscribe({
                allShows.addAll(it)
                fetchShowsOutcome.success(allShows)
            }, { handleError(it) })
            .addTo(compositeDisposable)
    }

    override fun fetchShowDetails(imdbID: String) {
        fetchShowDetails.loading(true)
        remote.fetchShowById(imdbID)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                { fetchShowDetails.success(it) },
                { handleError(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun mapToFeatureLocal(feature: Feature): FeatureLocal
            = FeatureLocal(
        feature.imdbID,
        feature.poster,
        feature.title,
        feature.type,
        feature.year
    )

    private fun mapToBookmark(item: FeatureLocal): Bookmark
            = Bookmark(
        item.imdbID,
        item.poster,
        item.title,
        item.type,
        item.year
    )

    override fun saveShows(shows: List<FeatureLocal>) {
        local.saveShows(shows)
    }

    override fun bookmark(bookmarked: Boolean, item: FeatureLocal) {
        local.bookmark(bookmarked, mapToBookmark(item))
    }

    override fun bookmark(bookmarked: Boolean, item: Bookmark) {
        local.bookmark(bookmarked, item)
    }

    override fun fetchBookmarks() {
        bookmarksOutcome.loading(true)
        local.fetchAllBookmarks()
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                { bookmarksOutcome.success(it) },
                { bookmarksOutcome.failed(it) }
            ).addTo(compositeDisposable)
    }

    override fun performSearch(
        isConnected: Boolean,
        observableFromView: Observable<String>,
        pageIndex: Int
    ) {
        observableFromView
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { it.isNotEmpty() && it.length >= 3 }
            .distinctUntilChanged()
            .switchMap { query -> Observable.just(query) }
            .observeOn(scheduler.mainThread())
            .subscribe { query -> fetchShows(isConnected, query, pageIndex)}
            .addTo(compositeDisposable)
    }

    override fun handleError(error: Throwable) {
        if( prevPageIndex > 1 )
            prevPageIndex--
        fetchShowsOutcome.onError(error)
    }
}