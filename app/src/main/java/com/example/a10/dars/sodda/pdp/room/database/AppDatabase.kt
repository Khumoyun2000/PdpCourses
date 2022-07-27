package com.example.a10.dars.sodda.pdp.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a10.dars.sodda.pdp.room.dao.PdpDao
import com.example.a10.dars.sodda.pdp.room.entity.Courses
import com.example.a10.dars.sodda.pdp.room.entity.Groups
import com.example.a10.dars.sodda.pdp.room.entity.Mentors
import com.example.a10.dars.sodda.pdp.room.entity.Student

@Database(
    entities = [Courses::class, Groups::class, Mentors::class, Student::class],
    version = 1,exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
        abstract fun pdpDao(): PdpDao

        companion object {
            private var appDatabase: AppDatabase? = null

            @Synchronized
            fun getInstance(context: Context): AppDatabase {
                if (appDatabase == null) {
                    appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "Pdp.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
                return appDatabase!!
            }
        }

}