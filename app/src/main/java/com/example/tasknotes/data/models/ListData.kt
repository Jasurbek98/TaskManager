package com.example.tasknotes.data.models

import com.example.tasknotes.data.room.entities.TaskData
import java.io.Serializable

class ListData(
    val ls: List<TaskData>
):Serializable