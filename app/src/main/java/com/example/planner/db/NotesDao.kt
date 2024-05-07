package com.example.planner.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.planner.models.Notes

@Dao
interface NotesDao {

    @Query("SELECT * FROM Notes")
    suspend fun getNotes(): List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(notes: Notes)

    @Query("DELETE FROM Notes WHERE id=:id")
    suspend fun deleteNote(id: Int)

    @Update
    suspend fun updateNote(notes: Notes)

    @Query("SELECT * FROM Notes WHERE id=:id")
    suspend fun getNoteById(id: Int):Notes

    @Query("SELECT * FROM Notes WHERE priority=:priority")
    suspend fun getNotesByPriority(priority:String):List<Notes>
}