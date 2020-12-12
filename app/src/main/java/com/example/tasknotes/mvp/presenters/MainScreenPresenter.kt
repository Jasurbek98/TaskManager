package com.example.tasknotes.mvp.presenters

import android.os.Handler
import android.os.Looper
import com.example.tasknotes.mvp.contracts.MainContract
import com.example.tasknotes.data.local.LocalStorage
import com.example.tasknotes.data.room.entities.TaskData
import java.util.*
import java.util.concurrent.Executors

class MainScreenPresenter(
    private val view: MainContract.View,
    private val model: MainContract.Model
) : MainContract.Presenter {
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    init {
        runWorkerThread {
            val data = checkListDeadline(model.getMainTasks())
            val image = LocalStorage.instance.personProfilePicture!!
            val name = LocalStorage.instance.personProfileName!!
            val email = LocalStorage.instance.personProfileEmail!!
            runOnUIThread {
                view.setBackground()
                view.setProfile(image, name, email)
                view.setNavigation()
                view.submitList(data)
            }
        }
    }

    private fun checkListDeadline(data: List<TaskData>): List<TaskData> {
        data.forEach {
            if (it.deadline - Calendar.getInstance().timeInMillis <= 0) {
                it.outOfTime = true
                model.updateTask(it)
            }
        }
        return model.getMainTasks()
    }


    override fun clickMenuButton() {
        runWorkerThread {
            runOnUIThread {
                view.openNavigation()
            }
        }
    }

    override fun createTask(task: TaskData) {
        runWorkerThread {
            val id = model.insertTask(task)
            task.id = id
            runOnUIThread {
                view.addAndChangeTask(task)
            }
        }
    }

    override fun openAddScreen() {
        runWorkerThread {
            runOnUIThread {
                view.openInsertDialog()
            }
        }
    }

    override fun clickItem(itemData: TaskData) {
        runWorkerThread {
            runOnUIThread {
                view.openTaskInformationScreen(itemData)
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