package com.example.planner.adapter

import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.planner.R
import com.example.planner.databinding.ItemNotesBinding
import com.example.planner.models.Notes

class NoteAdapter(
    private val onClick: (Notes) -> Unit
) : ListAdapter<Notes, NoteAdapter.NoteViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent, onClick)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val notes = getItem(position)
        holder.bind(notes)
    }

    class NoteViewHolder private constructor(
        private val binding: ItemNotesBinding,
        private val onClick: (Notes) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Notes) {
            binding.titleTv.text = item.title
            binding.subtitleTv.text = item.subTitle
            binding.notesTv.text = item.notes
            when (item.priority) {
                "1" -> {
                    binding.priority.setBackgroundResource(R.drawable.round_shape_green)
                }

                "2" -> {
                    binding.priority.setBackgroundResource(R.drawable.round_shape_yellow)
                }

                "3" -> {
                    binding.priority.setBackgroundResource(R.drawable.round_shape_red)
                }
            }

            binding.root.setOnClickListener {
                onClick(item)
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onClick: (Notes) -> Unit
            ): NoteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNotesBinding.inflate(layoutInflater, parent, false)
                return NoteViewHolder(binding, onClick)
            }
        }

    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Notes>() {
            override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}