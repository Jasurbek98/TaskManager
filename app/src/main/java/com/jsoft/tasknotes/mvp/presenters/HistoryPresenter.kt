package com.jsoft.tasknotes.mvp.presenters

import android.os.Handler
import android.os.Looper
import com.jsoft.tasknotes.mvp.contracts.HistoryContract
import com.jsoft.tasknotes.data.room.entities.TaskData
import java.util.concurrent.Executors

class HistoryPresenter(
    private val view: HistoryContract.View,
    private val model: HistoryContract.Model
) : HistoryContract.Presenter {
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