package com.example.tasknotes.mvp.contracts

import com.example.tasknotes.data.room.entities.TaskData

interface AllTasksContract {
    interface Model {
        fun insert(data: TaskData): Long
        fun getAll(): List<TaskData>
        fun update(data: TaskData): Int
        fun delete(data: TaskData): Int
    }

    interface View {
        fun openInformationScreen(data: TaskData)
        fun submitList(data: List<TaskData>)
        fun openCloneDialog(data: TaskData)
        fun moveToMainScreen()
    }

    interface Presenter {
        fun clickItem(data: TaskData)
        fun clickCompletedButton(data: TaskData)
        fun clickCancelButton(data: TaskData)
        fun clickDeleteButton(data: TaskData)
        fun clickOpenCloneDialog(data: TaskData)
        fun cloneTask(data: TaskData)
    }
}