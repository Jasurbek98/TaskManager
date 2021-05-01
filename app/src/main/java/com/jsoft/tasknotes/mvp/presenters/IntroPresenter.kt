package com.jsoft.tasknotes.mvp.presenters

import android.os.Looper
import com.jsoft.tasknotes.mvp.contracts.IntoContract
import java.util.concurrent.Executors


class IntroPresenter(
    private val view: IntoContract.View,
    private val model: IntoContract.Model
) : IntoContract.Presenter {
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = android.os.Handler(Looper.getMainLooper())
    override fun clickNext() {
        runWorkerThread {
            runOnUIThread {
                view.moveToNextScreen(model.getData())
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