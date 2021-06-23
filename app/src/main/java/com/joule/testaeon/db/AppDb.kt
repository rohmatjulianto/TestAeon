package com.joule.testaeon.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joule.testaeon.dataClass.Photos

@Database(entities = arrayOf(Photos::class), version = 1)
 abstract class AppDb  : RoomDatabase(){
  abstract fun photosDa() : PhotosDao

}