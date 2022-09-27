package com.example.appnote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.example.appnote.Dao.NoteDao
import com.example.appnote.Database.NoteDatabase
import com.example.appnote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var noteDao: NoteDao
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpDatabase()
        setUpBottomNav()
    }

    private fun setUpDatabase() {
        val database =
            Room.databaseBuilder(applicationContext, NoteDatabase::class.java, "database-notes")
                .build()
        noteDao = database.getNoteDao()
    }

    private fun setUpBottomNav() {
        navController = binding.container.getFragment<NavHostFragment>().navController
        hideBottomNavi()
        binding.bottomNav.setupWithNavController(navController)
    }

    private fun hideBottomNavi() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.createNoteFragment) {
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }
}