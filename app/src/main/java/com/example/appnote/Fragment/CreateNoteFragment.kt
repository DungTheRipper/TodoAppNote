package com.example.appnote.Fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
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
    private var type = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        loadingDataEdit()
        hideButton()
        setClickListeners()
        return binding.root
    }

    private fun hideButton() {
        if (type == "restoreNote") {
            binding.ivRestore.visibility = View.VISIBLE
            binding.ivDone.visibility = View.GONE
        } else {
            binding.ivRestore.visibility = View.GONE
        }
        if (editNoteId == -1) {
            binding.ivDelete.visibility = View.GONE
        } else {
            binding.ivDelete.visibility = View.VISIBLE
        }
    }

    private fun loadingDataEdit() {
        arguments?.let {
            editNoteId = it.getInt("id") as Int
            type = it.getString("type") as String
        }
        if (editNoteId != -1) context?.let {
            noteEdit = NoteDatabase.getDatabase(it).getNoteDao().getNoteFromId(editNoteId)
            binding.apply {
                etTitle.setText(noteEdit.title)
                etDescription.setText(noteEdit.description)
                colorChoice = noteEdit.color.toString()
                tvTime.text = noteEdit.time
                tvTimePicker.text = noteEdit.timePicker
                tvDatePicker.text = noteEdit.datePicker
                noteEdit.status = 1
            }
        }
    }

    private fun setClickListeners() {
        binding.ivDatePicker.setOnClickListener {
            setDatePicker()
        }
        binding.ivTimePicker.setOnClickListener {
            setTimePicker()
        }
        binding.ivRestore.setOnClickListener {
            restoreNote()
        }
        binding.ivBack.setOnClickListener {
            if (type == "restoreNote") findNavController().navigate(R.id.action_createNoteFragment_to_deleteFragment)
            else findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
        }
        binding.ivDelete.setOnClickListener {
            deleteNote()
        }
        binding.ivDone.setOnClickListener {
            if (editNoteId == -1) {
                saveNote()

            } else {
                updateNote()

            }
        }
    }

    private fun setTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timeListener =
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                binding.tvTimePicker.text =
                    SimpleDateFormat("HH:mm a", Locale.US).format(calendar.time)
            }
        TimePickerDialog(
            requireContext(),
            timeListener,
            hour,
            minute,
            false
        ).show()
    }

    private fun setDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dateListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding.tvDatePicker.text =
                    SimpleDateFormat("dd MMMM yyyy", Locale.US).format(calendar.time)
            }
        DatePickerDialog(
            requireContext(),
            dateListener,
            year,
            month,
            day
        ).show()
    }

    private fun restoreNote() {
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
                    noteEdit.timePicker = tvTimePicker.text.toString()
                    noteEdit.datePicker = tvDatePicker.text.toString()
                    noteEdit.status = 1
                }
                NoteDatabase.getDatabase(it).getNoteDao().updateNote(noteEdit)
            }
            findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
        }
    }

    private fun deleteNote() {
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
                    noteEdit.timePicker = tvTimePicker.text.toString()
                    noteEdit.datePicker = tvDatePicker.text.toString()
                    noteEdit.status = 0
                }
                if (type == "restoreNote") NoteDatabase.getDatabase(it).getNoteDao()
                    .deleteNote(noteEdit)
                else NoteDatabase.getDatabase(it).getNoteDao().updateNote(noteEdit)
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
                    noteEdit.timePicker = tvTimePicker.text.toString()
                    noteEdit.datePicker = tvDatePicker.text.toString()
                    if (type == "restoreNote") noteEdit.status = 0
                    else noteEdit.status = 1
                }
                NoteDatabase.getDatabase(it).getNoteDao().updateNote(noteEdit)
                findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
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
        } else if (binding.tvTimePicker.text.isNullOrEmpty()) {
            "Please choose a time".notification()
        } else if (binding.tvDatePicker.text.isNullOrEmpty()) {
            "Please choose a date!".notification()
        } else {
            context?.let {
                val note = Note()
                binding.apply {
                    note.title = etTitle.text.toString()
                    note.description = etDescription.text.toString()
                    note.color = colorChoice
                    note.time = tvTime.text.toString()
                    note.timePicker = tvTimePicker.text.toString()
                    note.datePicker = tvDatePicker.text.toString()
                    note.status = 1
                }
                NoteDatabase.getDatabase(it).getNoteDao().insertNote(note)
                findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
            }
        }
    }

    private fun String.notification() {
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }
}