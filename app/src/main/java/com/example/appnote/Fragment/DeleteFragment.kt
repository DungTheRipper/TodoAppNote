package com.example.appnote.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appnote.Adapter.NoteAdapter
import com.example.appnote.Database.NoteDatabase
import com.example.appnote.Entities.Note
import com.example.appnote.R
import com.example.appnote.databinding.FragmentDeleteBinding


class DeleteFragment : Fragment() {
    private lateinit var binding: FragmentDeleteBinding
    private lateinit var adapter: NoteAdapter
    private lateinit var notes: List<Note>
    private lateinit var notesDelete: MutableList<Note>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setClickListeners()

    }

    private fun initAdapter() {
        context?.let {
            notes = NoteDatabase.getDatabase(it).getNoteDao().getAllNotes()
        }
        notesDelete = mutableListOf()
        notes.forEach{
            if(it.status==0){
                notesDelete.add(it)
            }
        }
        adapter = NoteAdapter(notesDelete)
        adapter.onClickListener(onClick)
        binding.apply {
            recycleViewDeleteNotes.layoutManager = GridLayoutManager(context, 1)
            recycleViewDeleteNotes.setHasFixedSize(true)
            recycleViewDeleteNotes.adapter = adapter
        }
    }

    private val onClick = object : NoteAdapter.NoteListener {
        override fun onNoteClicked(note: Note) {
            val bundle = Bundle()
            Log.e("position", note.id.toString() )
            bundle.putInt("id", note.id!!)
            bundle.putString("type","restoreNote")
            findNavController().navigate(R.id.action_deleteFragment_to_createNoteFragment, bundle)
        }
    }

    private fun setClickListeners() {

    }

}