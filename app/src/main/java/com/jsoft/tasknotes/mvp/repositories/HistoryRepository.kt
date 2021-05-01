package com.jsoft.tasknotes.mvp.repositories

import com.jsoft.tasknotes.app.App
import com.jsoft.tasknotes.mvp.contracts.HistoryContract
import com.jsoft.tasknotes.data.room.AppDatabase
import com.jsoft.tasknotes.data.room.entities.TaskData

class HistoryRepository : HistoryContract.Model {
    private val taskDao = AppDatabase.getDatabase(App.instance).taskDao()
    override fun getAll(): List<TaskData> = taskDao.getAll()

}