package com.alokomkar.entertainment.data.remote

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FeatureAPI {

    //http://www.omdbapi.com/?s=friends&apikey=1425e29b&page=1
    @GET
    fun search(
        @Query("s") searchQuery: String,
        @Query("page") page: Int
    ): Observable<Feature>


    //http://www.omdbapi.com/?i=tt3896198&apikey=1425e29b
    @GET
    fun searchById(
        @Query("i") featureId: String
    ): Single<ShowDetails>

}