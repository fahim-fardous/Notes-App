package com.example.planner.screens.notes.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.planner.db.AppDatabase
import com.example.planner.models.Notes
import com.example.planner.repository.NotesRepository
import kotlinx.coroutines.launch

class AppViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository: NotesRepository

    private val _noteItem: MutableLiveData<List<Notes>> by lazy {
        MutableLiveData<List<Notes>>()
    }

    val noteItem: LiveData<List<Notes>>
        get() = _noteItem

    private val _note: MutableLiveData<Notes> by lazy {
        MutableLiveData<Notes>()
    }

    val note: LiveData<Notes>
        get() = _note

    private val _showMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val showMessage: LiveData<String>
        get() = _showMessage

    private val _eventSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val eventSuccess: LiveData<Boolean>
        get() = _eventSuccess


    init {
        val dao = AppDatabase.invoke(application).notesDao()
        repository = NotesRepository(dao)

    }


    fun getNotes() = viewModelScope.launch {
        try {
            val notes = repository.getAllNotes()
            _noteItem.value = notes
        } catch (e: Exception) {
            _showMessage.value = "Unable to load data!"
        }
    }

    fun getNote(id: Int) = viewModelScope.launch {
        try {
            val response = repository.getNoteById(id)
            _note.value = response
        } catch (e: Exception) {
            _showMessage.value = "Unknown error"
        }
    }

    fun getNoteByPriority(priority: String) = viewModelScope.launch {
        try {
            val response = repository.getNotesByPriority(priority)
            _noteItem.value = response
        } catch (e: Exception) {

        }
    }

    fun deleteNote(id:Int) = viewModelScope.launch {
        try {
            repository.deleteNote(id)
            _eventSuccess.postValue(true)
        }
        catch (e:Exception){

        }
    }

    fun isValid(
        title: String,
        priority: String,
        notes: String
    ): Boolean {
        if (title.isBlank()) {
            _showMessage.value = "Please enter title!"
            return false
        }

        if (priority.isBlank()) {
            _showMessage.value = "Please select priority!"
            return false
        }

        if (notes.isBlank()) {
            _showMessage.value = "Please enter notes!"
            return false
        }
        return true
    }

    fun addNote(notes: Notes) = viewModelScope.launch {
        if (!isValid(notes.title, notes.priority, notes.notes)) {
            return@launch
        }

        try {
            repository.insertNote(notes)
            _eventSuccess.postValue(true)
        } catch (e: Exception) {
            _showMessage.value = "Unknown error!"
        }
    }

    fun updateNote(notes: Notes) = viewModelScope.launch {
        if (!isValid(notes.title, notes.priority, notes.notes)) {
            return@launch
        }

        try {
            repository.updateNote(notes)
            _eventSuccess.postValue(true)
        } catch (e: Exception) {
            _showMessage.value = "Unknown error!"
        }
    }


}