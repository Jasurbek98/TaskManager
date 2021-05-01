package com.jsoft.tasknotes.data.models

import com.jsoft.tasknotes.data.room.entities.TaskData
import java.io.Serializable

class ListData(
    val ls: List<TaskData>
):Serializable