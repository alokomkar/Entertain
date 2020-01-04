package com.alokomkar.entertainment.ui.model

import com.alokomkar.core.extensions.performOnBackOutOnMain
import com.alokomkar.core.networking.Scheduler
import com.alokomkar.entertainment.data.local.FeatureLocal
import com.alokomkar.entertainment.data.local.FeatureLocalDao
import com.alokomkar.entertainment.di.ActivityScope
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

@ActivityScope
class LocalImpl @Inject constructor(
    private val dao : FeatureLocalDao,
    private val scheduler: Scheduler
) : DataContract.Local {

    override fun saveShows(shows: List<FeatureLocal>) {
        Completable.fromAction { dao.upsertAll(shows) }
            .performOnBackOutOnMain(scheduler)
            .subscribe()
    }

    override fun fetchAllShows(): Flowable<List<FeatureLocal>>
            = dao.getAllShows()
}