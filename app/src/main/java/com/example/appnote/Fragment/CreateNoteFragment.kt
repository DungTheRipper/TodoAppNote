package com.example.appnote.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.appnote.Database.NoteDatabase
import com.example.appnote.Entities.Note
import com.example.appnote.R
import com.example.appnote.databinding.FragmentCreateNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteFragment : Fragment() {
    private lateinit var binding: FragmentCreateNoteBinding
    private var colorChoice = "#DBDBDB"
    private var editNoteId: Int = -1
    private var noteEdit = Note()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        loadingDataEdit()
        setClickListeners()
        return binding.root
    }

    private fun loadingDataEdit() {
        arguments?.let {
            editNoteId = it.getInt("id") as Int
        }
        if (editNoteId != -1) context?.let {
            noteEdit = NoteDatabase.getDatabase(it).getNoteDao().getNoteFromId(editNoteId)
            binding.apply {
                etTitle.setText(noteEdit.title)
                etDescription.setText(noteEdit.description)
                colorChoice = noteEdit.color.toString()
                tvTime.text = noteEdit.time
                noteEdit.status = 1
            }
        }
    }

    private fun setClickListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
        }
        binding.ivDone.setOnClickListener {
            var type = ""
            arguments?.let {
                editNoteId = it.getInt("id") as Int
                type = it.getString("type") as String
            }
            if (type != "editNote") {
                saveNote()
                findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
            } else {
                updateNote()
                findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
            }
        }
    }

    private fun updateNote() {
        binding.tvTime.text =
            SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(
                Date()
            )
        if (binding.etTitle.text.isNullOrEmpty()) {
            "Note title can't be empty!".notification()
        } else if (binding.etDescription.text.isNullOrEmpty()) {
            "Note description can't be empty!".notification()
        } else {
            context?.let {
                binding.apply {
                    noteEdit.title = etTitle.text.toString()
                    noteEdit.description = etDescription.text.toString()
                    noteEdit.color = colorChoice
                    noteEdit.time = tvTime.text.toString()
                    noteEdit.status = 1
                }
                NoteDatabase.getDatabase(it).getNoteDao().updateNote(noteEdit)
            }
        }
    }

    private fun saveNote() {
        binding.tvTime.text =
            SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(
                Date()
            )
        if (binding.etTitle.text.isNullOrEmpty()) {
            "Note title can't be empty!".notification()
        } else if (binding.etDescription.text.isNullOrEmpty()) {
            "Note description can't be empty!".notification()
        } else {
            context?.let {
                val note = Note()
                binding.apply {
                    note.title = etTitle.text.toString()
                    note.description = etDescription.text.toString()
                    note.color = colorChoice
                    note.time = tvTime.text.toString()
                    note.status = 1
                }
                NoteDatabase.getDatabase(it).getNoteDao().insertNote(note)
            }
        }
    }

    private fun String.notification() {
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }
}