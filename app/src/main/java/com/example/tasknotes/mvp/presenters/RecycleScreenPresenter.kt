package com.example.tasknotes.mvp.presenters

import android.os.Handler
import android.os.Looper
import com.example.tasknotes.mvp.contracts.RecycleContract
import com.example.tasknotes.data.room.entities.TaskData
import java.util.concurrent.Executors

class RecycleScreenPresenter(
    private val view: RecycleContract.View,
    private val model: RecycleContract.Model
) : RecycleContract.Presenter {
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    init {
        runWorkerThread {
            val data = model.getDeletedTasks()
            runOnUIThread {
                view.submitList(data)
            }
        }
    }

    override fun clickDeleteButton(data: TaskData) {
        runWorkerThread {
            val deleted = model.delete(data)
            runOnUIThread {
                if (deleted > 0) {
                    view.deleteTask(data)
                }
            }
        }
    }

    override fun clickRestoreButton(data: TaskData) {
        runWorkerThread {
            data.deleted = false
            val restore = model.update(data)
            runOnUIThread {
                if (restore > 0) {
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