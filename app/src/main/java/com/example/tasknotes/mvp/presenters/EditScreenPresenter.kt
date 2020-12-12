package com.example.tasknotes.mvp.presenters

import android.os.Handler
import android.os.Looper
import com.example.tasknotes.mvp.contracts.EditContract
import com.example.tasknotes.data.room.entities.TaskData
import java.util.concurrent.Executors

class EditScreenPresenter(
    private val view: EditContract.View,
    private val model: EditContract.Model
) : EditContract.Presenter {
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    init {
        runWorkerThread {
            val data = model.getMainTasks()
            runOnUIThread {
                view.submitList(data)
            }
        }
    }

    override fun clickDeleteButton(data: TaskData) {
        runWorkerThread {
            data.deleted = true
            val update = model.update(data)
            runOnUIThread {
                if (update > 0) {
                    view.deleteTask(data)
                }
            }
        }
    }

    override fun clickOpenEditDialog(data: TaskData) {
        runWorkerThread {
            runOnUIThread {
                view.openEditDialog(data)
            }
        }
    }

    override fun clickCancelButton(data: TaskData) {
        runWorkerThread {
            data.rejected = true
            val update = model.update(data)
            runOnUIThread {
                if (update > 0) {
                    view.deleteTask(data)
                }
            }
        }
    }

    override fun clickItem(data: TaskData) {
        runWorkerThread {
            runOnUIThread {
                view.openTaskInformation(data)
            }
        }
    }

    override fun editTask(data: TaskData) {
        runWorkerThread {
            val update = model.update(data)
            runOnUIThread {
                if (update > 0) {
                    view.updateTask(data)
                }
            }
        }
    }

    private fun runOnUIThread(f: () -> Unit) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            f()
        } else {
            handler.post { f() }
        }
    }

    private fun runWorkerThread(f: () -> Unit) {
        executor.execute { f() }
    }

}