package com.jsoft.tasknotes.mvp.repositories

import com.jsoft.tasknotes.app.App
import com.jsoft.tasknotes.mvp.contracts.RecycleContract
import com.jsoft.tasknotes.data.room.AppDatabase
import com.jsoft.tasknotes.data.room.entities.TaskData

class RecycleScreenRepository : RecycleContract.Model {
    private val db = AppDatabase.getDatabase(App.instance)
    private val taskDao = db.taskDao()
    override fun getDeletedTasks() = taskDao.getDeletedTasks()

    override fun delete(data: TaskData) = taskDao.delete(data)

    override fun update(data: TaskData) = taskDao.update(data)

}