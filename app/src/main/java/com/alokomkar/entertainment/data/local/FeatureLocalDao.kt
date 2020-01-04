package com.alokomkar.entertainment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface FeatureLocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(list: List<FeatureLocal>)

    @Query("SELECT * FROM FeatureLocal")
    fun getAllShows(): Flowable<List<FeatureLocal>>
}