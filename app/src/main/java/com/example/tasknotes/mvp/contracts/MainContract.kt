package com.example.tasknotes.mvp.contracts

import com.example.tasknotes.data.room.entities.TaskData

interface MainContract {
    interface Model {
        fun getMainTasks(): List<TaskData>
        fun insertTask(task: TaskData): Long
        fun updateTask(task: TaskData): Int
    }

    interface View {
        fun showMessage(message: String)
        fun openInsertDialog()
        fun submitList(data: List<TaskData>)
        fun addAndChangeTask(data: TaskData)
        fun openNavigation()
        fun openTaskInformationScreen(itemData: TaskData)
        fun setNavigation()
        fun setProfile(imageUrl:String,userName:String,userEmail:String)
        fun setBackground()
    }

    interface Presenter {
        fun clickMenuButton()
        fun createTask(task: TaskData)
        fun openAddScreen()
        fun clickItem(itemData: TaskData)

    }
}