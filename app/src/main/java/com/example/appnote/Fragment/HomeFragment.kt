package com.example.appnote.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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
        selectionSort()
        searchTextListener()
    }

    private fun searchTextListener() {
        binding.svSearch.clearFocus()
        val svObject = object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterListNote(newText)
                return false
            }
        }
        binding.svSearch.setOnQueryTextListener(svObject)
    }

    private fun filterListNote(newText : String){
        val  filteredListNote =  notesHome.filter { it.title!!.contains(newText) }
        adapter = NoteAdapter(filteredListNote)
        adapter.onClickListener(onClick)
        binding.recycleViewNotes.adapter = adapter
    }

    private fun selectionSort() {
        binding.toolBar.setOnMenuItemClickListener{menu->
            when(menu.itemId){
                R.id.sortByTitle -> {
                    notesHome.sortBy { it.title }
                    adapter = NoteAdapter(notesHome)
                    binding.recycleViewNotes.adapter = adapter
                    adapter.onClickListener(onClick)
                    true
                }
                R.id.sortByDate ->{
                    notesHome.sortBy { it.time }
                    adapter = NoteAdapter(notesHome)
                    binding.recycleViewNotes.adapter = adapter
                    adapter.onClickListener(onClick)
                    true
                }
                else -> false
            }

        }
    }

    private fun initAdapter() {
        context?.let {
            notes = NoteDatabase.getDatabase(it).getNoteDao().getAllNotes()
        }
        notesHome = mutableListOf()
        notes.forEach{
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