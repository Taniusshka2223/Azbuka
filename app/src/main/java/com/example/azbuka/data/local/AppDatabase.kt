package com.example.azbuka.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.azbuka.data.local.dao.CardDao
import com.example.azbuka.data.local.entity.CardEntity

@Database(entities = [CardEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "azbuka_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
