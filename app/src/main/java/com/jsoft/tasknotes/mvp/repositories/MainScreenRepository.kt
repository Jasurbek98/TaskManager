package com.jsoft.tasknotes.mvp.repositories

import com.jsoft.tasknotes.app.App
import com.jsoft.tasknotes.mvp.contracts.MainContract
import com.jsoft.tasknotes.data.room.AppDatabase
import com.jsoft.tasknotes.data.room.entities.TaskData

class MainScreenRepository : MainContract.Model {
    private val db = AppDatabase.getDatabase(App.instance)
    private val taskDao = db.taskDao()
    override fun getMainTasks() = taskDao.getMainTasks()

    override fun insertTask(task: TaskData) = taskDao.insert(task)
    override fun updateTask(task: TaskData) = taskDao.update(task)


}