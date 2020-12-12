package com.example.tasknotes.data.room.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.tasknotes.data.room.entities.TaskData

@Dao
interface TaskDao : BaseDao<TaskData> {
    @Query("SELECT * FROM tasks")
    fun getAll(): List<TaskData>

    @Query("SELECT * FROM tasks WHERE rejected =1 ")
    fun getRejectedTasks(): List<TaskData>

    @Query("SELECT * FROM tasks WHERE outOfTime =1 ")
    fun getOldTasks(): List<TaskData>

    @Query("SELECT * FROM tasks WHERE done=1 ")
    fun getCompletedTasks(): List<TaskData>

    @Query("SELECT * FROM tasks WHERE deleted = 1 ")
    fun getDeletedTasks(): List<TaskData>

    @Query("SELECT * FROM tasks WHERE  done = rejected = outOfTime= 0 AND deleted=0 ")
    fun getMainTasks(): List<TaskData>
}
