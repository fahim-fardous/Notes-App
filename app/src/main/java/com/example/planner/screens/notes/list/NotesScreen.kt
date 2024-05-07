package com.example.planner.screens.notes.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.planner.R
import com.example.planner.adapter.NoteAdapter
import com.example.planner.databinding.FragmentNoteScreenBinding
import com.example.planner.models.Notes
import com.example.planner.screens.notes.add.AppViewModel


class NotesScreen : Fragment(R.layout.fragment_note_screen) {
    private lateinit var binding: FragmentNoteScreenBinding
    private val viewModel: AppViewModel by viewModels()
    private lateinit var adapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = NoteAdapter {
            onClick(it)
        }
        initObserver()

    }

    private fun initObserver() {
        viewModel.noteItem.observe(this) { notes ->
            adapter.submitList(notes)

        }

        viewModel.showMessage.observe(this) { message ->
            message?.run {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNoteScreenBinding.bind(view)

        initViews()

        initListeners()

        loadData()
    }

    private fun loadData() {
        viewModel.getNotes()
    }

    private fun initViews() {
        binding.notesRv.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.notesRv.adapter = adapter


    }

    private fun initListeners() {
        binding.noteAddFab.setOnClickListener {
            val action = NotesScreenDirections.actionNotesScreenToAddScreen()
            findNavController().navigate(action)
        }
        binding.showAll.setOnClickListener {
            viewModel.getNotes()
        }
        binding.showHigh.setOnClickListener {
            viewModel.getNoteByPriority("3")
        }
        binding.showMedium.setOnClickListener {
            viewModel.getNoteByPriority("2")
        }
        binding.showLow.setOnClickListener {
            viewModel.getNoteByPriority("1")
        }
    }

    private fun onClick(note: Notes) {
        val action = NotesScreenDirections.actionNotesScreenToDetailsScreen(note.id)
        findNavController().navigate(action)
    }
}