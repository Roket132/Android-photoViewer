package com.example.laletin.picturerbog

import android.arch.persistence.room.*

@Dao
interface ImageDao {

    @Query("SELECT * FROM imageentity")
    fun getAll(): List<ImageEntity>

    @Query("SELECT * FROM imageentity WHERE id = :id")
    fun getById(id: Long): ImageEntity

    @Insert
    fun insert(image: ImageEntity)

    @Update
    fun update(image: ImageEntity)

    @Delete
    fun delete(image: ImageEntity)

}