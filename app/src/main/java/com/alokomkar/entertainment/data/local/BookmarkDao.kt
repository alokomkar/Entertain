package com.alokomkar.entertainment.data.local

import androidx.room.*
import io.reactivex.Flowable

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmark: Bookmark)

    @Delete
    fun delete(bookmark: Bookmark)

    @Query("SELECT * FROM Bookmark")
    fun getAllBookmarks(): Flowable<List<Bookmark>>

}