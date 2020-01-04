package com.alokomkar.entertainment.ui.list

import android.content.Context
import androidx.room.Room
import com.alokomkar.core.constants.Constants
import com.alokomkar.entertainment.data.local.FeatureLocalDao
import com.alokomkar.entertainment.data.local.LocalDB
import com.alokomkar.entertainment.data.remote.FeatureAPI
import com.alokomkar.entertainment.di.ActivityScope
import com.alokomkar.entertainment.ui.model.DataContract
import com.alokomkar.entertainment.ui.model.LocalImpl
import com.alokomkar.entertainment.ui.model.RemoteImpl
import com.alokomkar.entertainment.ui.model.RepositoryImpl
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit

@Module(includes = [ListModule.ListAutoModule::class])
class ListModule {

    @Provides
    @ActivityScope
    fun listAdapter(): SearchListAdapter = SearchListAdapter()
    
    @Provides
    @ActivityScope
    fun localDB( context: Context ): LocalDB
            = Room.databaseBuilder(context, LocalDB::class.java, Constants.LOCAL_DB_NAME).build()

    @Provides
    @ActivityScope
    fun localDao( localDB: LocalDB ): FeatureLocalDao = localDB.featureLocalDao()

    @Provides
    @ActivityScope
    fun featureAPI( retrofit: Retrofit ): FeatureAPI = retrofit.create(FeatureAPI::class.java)
    
    @Provides
    @ActivityScope
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Module
    interface ListAutoModule {

        @Binds
        @ActivityScope
        fun repository( repository: RepositoryImpl ): DataContract.Repository

        @Binds
        @ActivityScope
        fun local( local : LocalImpl ): DataContract.Local

        @Binds
        @ActivityScope
        fun remote( remote: RemoteImpl ): DataContract.Remote

    }

}