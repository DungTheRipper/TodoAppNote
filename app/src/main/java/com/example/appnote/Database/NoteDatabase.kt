package com.example.appnote.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appnote.Dao.NoteDao
import com.example.appnote.Entities.Note


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    companion object {
        private var notesDatabase: NoteDatabase? = null
        @Synchronized
        fun getDatabase(context: Context): NoteDatabase {
            if (notesDatabase == null) {
                notesDatabase = Room.databaseBuilder(
                    context.applicationContext
                    , NoteDatabase::class.java
                    , "database-notes"
                ).allowMainThreadQueries().build()
            }
            return notesDatabase!!
        }
    }

    abstract fun getNoteDao(): NoteDao
}
