package com.alokomkar.entertainment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FeatureLocal::class, Bookmark::class], exportSchema = false, version = 1)
abstract class LocalDB : RoomDatabase() {
    abstract fun featureLocalDao(): FeatureLocalDao
    abstract fun bookmarkDao(): BookmarkDao
}