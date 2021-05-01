package com.jsoft.tasknotes.mvp.contracts

import com.jsoft.tasknotes.data.room.entities.TaskData

interface HistoryContract {
    interface Model {
        fun getAll(): List<TaskData>
    }

    interface View {
        fun openInformationScreen(data: TaskData)
        fun submitList(data: List<TaskData>)
    }

    interface Presenter {
        fun clickItem(data: TaskData)
    }
}