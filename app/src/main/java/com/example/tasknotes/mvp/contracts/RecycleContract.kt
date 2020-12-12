package com.example.tasknotes.mvp.contracts

import com.example.tasknotes.data.room.entities.TaskData

interface RecycleContract {
    interface Model {
        fun getDeletedTasks(): List<TaskData>
        fun delete(data: TaskData): Int
        fun update(data: TaskData): Int
    }

    interface View {
        fun submitList(data: List<TaskData>)
        fun deleteTask(data:TaskData)
        fun openTaskInformation(data:TaskData)
    }

    interface Presenter {
        fun clickDeleteButton(data: TaskData)
        fun clickRestoreButton(data: TaskData)
        fun clickItem(data: TaskData)
    }
}