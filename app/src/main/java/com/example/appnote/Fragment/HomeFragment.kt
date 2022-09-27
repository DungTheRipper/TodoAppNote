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
import com.example.appnote.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: NoteAdapter
    private lateinit var notes: List<Note>
    private lateinit var notesHome: MutableList<Note>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
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
        notesHome = mutableListOf()
        notes.forEach{
            Log.e("notes", it.toString() )
            if(it.status==1){
                notesHome.add(it)
            }
        }
        adapter = NoteAdapter(notesHome)
        adapter.onClickListener(onClick)
        binding.apply {
            recycleViewNotes.layoutManager = GridLayoutManager(context, 1)
            recycleViewNotes.setHasFixedSize(true)
            recycleViewNotes.adapter = adapter
        }
    }

    private val onClick = object : NoteAdapter.NoteListener {
        override fun onNoteClicked(note: Note) {
            val bundle = Bundle()
            Log.e("position", note.id.toString() )
            bundle.putInt("id", note.id!!)
            bundle.putString("type","editNote")
            findNavController().navigate(R.id.action_homeFragment_to_createNoteFragment, bundle)
        }
    }

    private fun setClickListeners() {
        binding.ivAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createNoteFragment)
        }
    }


}