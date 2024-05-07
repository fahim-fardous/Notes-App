package com.example.planner.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var subTitle: String? = null,
    var notes: String,
    var date: String,
    var priority: String
)
