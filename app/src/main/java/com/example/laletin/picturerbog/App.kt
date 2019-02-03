package com.example.laletin.picturerbog

import android.arch.persistence.room.Room
import android.app.Application

class App : Application() {

    var database: AppDatabase? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        //size_ = 0
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
                .allowMainThreadQueries()
                .build()
    }
/*
    fun add() {
        size_++
    }

    fun remove() {
        size_--
    }
   */
    companion object {
        lateinit var instance: App
        //var size_ = 0

    }
}