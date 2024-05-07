package com.example.planner.repository

import androidx.lifecycle.LiveData
import com.example.planner.db.NotesDao
import com.example.planner.models.Notes

class NotesRepository(val dao: NotesDao) {
    suspend fun getAllNotes(): List<Notes> = dao.getNotes()

    suspend fun insertNote(notes: Notes) = dao.insertNote(notes)

    suspend fun deleteNote(id: Int) = dao.deleteNote(id)

    suspend fun updateNote(notes: Notes) = dao.updateNote(notes)

    suspend fun getNoteById(id:Int):Notes = dao.getNoteById(id)

    suspend fun getNotesByPriority(priority:String):List<Notes> = dao.getNotesByPriority(priority)
}