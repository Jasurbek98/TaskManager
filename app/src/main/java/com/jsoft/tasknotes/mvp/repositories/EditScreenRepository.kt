package com.jsoft.tasknotes.mvp.repositories

import com.jsoft.tasknotes.app.App
import com.jsoft.tasknotes.mvp.contracts.EditContract
import com.jsoft.tasknotes.data.room.AppDatabase
import com.jsoft.tasknotes.data.room.entities.TaskData

class EditScreenRepository : EditContract.Model {
    private val db = AppDatabase.getDatabase(App.instance)
    private val taskDao = db.taskDao()
    override fun getMainTasks() = taskDao.getMainTasks()

    override fun update(data: TaskData) = taskDao.update(data)

    override fun insert(data: TaskData) = taskDao.insert(data)
}