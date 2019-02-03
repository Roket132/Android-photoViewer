package com.example.laletin.picturerbog

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database


@Database(entities = [ImageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}