package com.example.tasknotes.mvp.presenters

import android.os.Handler
import android.os.Looper
import com.example.tasknotes.mvp.contracts.AllTasksContract
import com.example.tasknotes.data.room.entities.TaskData
import java.util.concurrent.Executors

class AllTasksPresenter(
    private val view: AllTasksContract.View,
    private val model: AllTasksContract.Model
) : AllTasksContract.Presenter {
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    init {
        runWorkerThread {
            val data = model.getAll()
            runOnUIThread {
                view.submitList(data)
            }
        }
    }


    override fun clickItem(data: TaskData) {
        runWorkerThread {
            runOnUIThread {
                view.openInformationScreen(data)
            }
        }
    }

    override fun clickCompletedButton(data: TaskData) {
        runWorkerThread {
            data.done = true
            model.update(data)
        }
    }

    override fun clickCancelButton(data: TaskData) {
        runWorkerThread {
            data.rejected = true
            model.update(data)
        }
    }

    override fun clickDeleteButton(data: TaskData) {
        runWorkerThread {
            model.delete(data)
        }
    }

    override fun clickOpenCloneDialog(data: TaskData) {
        runWorkerThread {
            runOnUIThread {
                view.openCloneDialog(data)
            }
        }
    }

    override fun cloneTask(data: TaskData) {
        runWorkerThread {
            when {
                data.done -> {
                    data.done = false
                }
                data.rejected -> {
                    data.rejected = false
                }
                data.outOfTime -> {
                    data.outOfTime = false
                }
            }
            model.update(data)
            view.moveToMainScreen()
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