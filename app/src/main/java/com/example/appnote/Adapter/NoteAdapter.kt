package com.example.appnote.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appnote.Entities.Note
import com.example.appnote.databinding.ItemNoteBinding

class NoteAdapter(private val notes: List<Note>,private var noteListener: NoteListener? = null) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.tvTitle.text = note.title
        holder.tvTime.text = note.time
        holder.layoutNote.setOnClickListener{
            noteListener!!.onNoteClicked(note)
        }
    }

    inner class ViewHolder(binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvTitle: TextView = binding.tvTitle
        val tvTime: TextView = binding.tvTime
        val layoutNote : LinearLayout = binding.layoutNote
    }

    interface NoteListener {
        fun onNoteClicked(note: Note)
    }

    fun onClickListener(listener: NoteListener) {
        noteListener = listener
    }
}