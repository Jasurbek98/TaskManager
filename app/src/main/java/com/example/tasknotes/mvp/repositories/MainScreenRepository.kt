package com.example.tasknotes.mvp.repositories

import com.example.tasknotes.app.App
import com.example.tasknotes.mvp.contracts.MainContract
import com.example.tasknotes.data.room.AppDatabase
import com.example.tasknotes.data.room.entities.TaskData

class MainScreenRepository : MainContract.Model {
    private val db = AppDatabase.getDatabase(App.instance)
    private val taskDao = db.taskDao()
    override fun getMainTasks() = taskDao.getMainTasks()

    override fun insertTask(task: TaskData) = taskDao.insert(task)
    override fun updateTask(task: TaskData) = taskDao.update(task)


}