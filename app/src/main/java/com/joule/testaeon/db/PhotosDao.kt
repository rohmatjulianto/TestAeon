package com.joule.testaeon.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.joule.testaeon.dataClass.Photos

@Dao
interface PhotosDao {
    @Query("SELECT * FROM Photos")
    fun getAll(): List<Photos>

    @Insert
    fun insertAll(vararg data: Photos)

    @Query("SELECT * FROM Photos WHERE id IN (:id)")
    fun getById(id: Int): Photos

    @Query("SELECT * FROM Photos WHERE title LIKE '%' || :title || '%'")
    fun searchByTitle(title: String): List<Photos>

}