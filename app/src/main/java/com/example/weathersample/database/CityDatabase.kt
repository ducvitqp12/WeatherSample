package com.example.weathersample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [City::class], version = 1)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDAO(): CityDAO

    companion object {
        @Volatile
        private lateinit var INSTANCE: CityDatabase
        fun getDatabase(context: Context): CityDatabase {
            synchronized(CityDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        CityDatabase::class.java,
                        "city").build()
                }
            }
            return INSTANCE
        }

        fun getInstance() = INSTANCE
    }


}