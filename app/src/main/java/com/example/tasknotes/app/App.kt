package com.example.tasknotes.app

import android.app.Application
import com.example.tasknotes.data.local.LocalStorage

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        LocalStorage.init(instance)
    }

    companion object {
        lateinit var instance: App
    }
}