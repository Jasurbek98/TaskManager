package com.example.tasknotes.mvp.repositories

import com.example.tasknotes.app.App
import com.example.tasknotes.mvp.contracts.RecycleContract
import com.example.tasknotes.data.room.AppDatabase
import com.example.tasknotes.data.room.entities.TaskData

class RecycleScreenRepository : RecycleContract.Model {
    private val db = AppDatabase.getDatabase(App.instance)
    private val taskDao = db.taskDao()
    override fun getDeletedTasks() = taskDao.getDeletedTasks()

    override fun delete(data: TaskData) = taskDao.delete(data)

    override fun update(data: TaskData) = taskDao.update(data)

}