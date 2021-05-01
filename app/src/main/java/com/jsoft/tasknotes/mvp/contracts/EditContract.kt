package com.jsoft.tasknotes.mvp.contracts

import com.jsoft.tasknotes.data.room.entities.TaskData

interface EditContract {
    interface Model {
        fun getMainTasks(): List<TaskData>
        fun update(data: TaskData): Int
        fun insert(data: TaskData): Long
    }

    interface View {
        fun submitList(data: List<TaskData>)
        fun openEditDialog(data:TaskData)
        fun updateTask(data: TaskData)
        fun deleteTask(data: TaskData)
        fun openTaskInformation(data:TaskData)
    }

    interface Presenter {
        fun clickDeleteButton(data: TaskData)
        fun clickOpenEditDialog(data: TaskData)
        fun clickCancelButton(data: TaskData)
        fun clickItem(data: TaskData)
        fun editTask(data: TaskData)
    }
}