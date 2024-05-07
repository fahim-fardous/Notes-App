package com.example.planner.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.planner.models.Notes

@Database(
    entities = [
        Notes::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object {
        operator fun invoke(context: Context) = buildDatabase(context)

        private fun buildDatabase(context: Context): AppDatabase {
            val databaseBuilder = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "MainDatabase.db"
            )
            return databaseBuilder.build()
        }
    }
}