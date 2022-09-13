package com.example.appnote.Dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.appnote.Entities.Note

@Dao
interface NoteDao {
    @Query("select * from notes")
    fun getAllNotes(): List<Note>

    @Query("SELECT * FROM notes WHERE id =:id")
    fun getNoteFromId(id: Int): Note

    @Insert(onConflict = REPLACE)
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}