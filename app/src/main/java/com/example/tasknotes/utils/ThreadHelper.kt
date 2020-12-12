package com.example.tasknotes.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

abstract class ThreadHelper {
    private val handle = Handler(Looper.getMainLooper())
    private val executor = Executors.newSingleThreadExecutor()

    fun runOnUIThread(f: () -> Unit) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            f()
        } else {
            handle.post { f() }
        }
    }

    fun runOnWorkerThread(f: () -> Unit) {
        executor.execute(f)
    }
}