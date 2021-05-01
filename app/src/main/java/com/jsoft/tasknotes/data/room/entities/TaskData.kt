package com.jsoft.tasknotes.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "tasks"
)
data class TaskData(
    var title: String,
    var date: Long,
    var deadline: Long,
    var hashTag: String,
    var priority: Int,
    var description: String,
    var done: Boolean = false,
    var rejected: Boolean = false,
    var outOfTime: Boolean = false,
    var deleted:Boolean = false,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
) : Serializable