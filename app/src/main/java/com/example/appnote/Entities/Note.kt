package com.example.appnote.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes")
class Note : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int?= null

    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "description")
    var description: String? = null

    @ColumnInfo(name = "time")
    var time: String? = null

    @ColumnInfo(name = "timePicker")
    var timePicker: String? = null

    @ColumnInfo(name = "datePicker")
    var datePicker: String? = null

    @ColumnInfo(name = "color")
    var color: String? = null

    @ColumnInfo(name = "status")
    var status: Int = 1 // 1 in home, 0 in delete

    override fun toString(): String {
        return "$id : $title : $description : $time"
    }

}