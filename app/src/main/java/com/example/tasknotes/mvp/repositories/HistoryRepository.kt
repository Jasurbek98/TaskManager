package com.example.tasknotes.mvp.repositories

import com.example.tasknotes.app.App
import com.example.tasknotes.mvp.contracts.HistoryContract
import com.example.tasknotes.data.room.AppDatabase
import com.example.tasknotes.data.room.entities.TaskData

class HistoryRepository : HistoryContract.Model {
    private val taskDao = AppDatabase.getDatabase(App.instance).taskDao()
    override fun getAll(): List<TaskData> = taskDao.getAll()

}