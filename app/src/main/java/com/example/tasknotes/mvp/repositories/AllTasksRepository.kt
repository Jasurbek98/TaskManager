package com.example.tasknotes.mvp.repositories

import com.example.tasknotes.app.App
import com.example.tasknotes.mvp.contracts.AllTasksContract
import com.example.tasknotes.data.room.AppDatabase
import com.example.tasknotes.data.room.entities.TaskData

class AllTasksRepository : AllTasksContract.Model {
    private val taskDao = AppDatabase.getDatabase(App.instance).taskDao()
    override fun insert(data: TaskData) = taskDao.insert(data)
    override fun getAll() = taskDao.getAll()
    override fun update(data: TaskData) = taskDao.update(data)
    override fun delete(data: TaskData) = taskDao.delete(data)

}