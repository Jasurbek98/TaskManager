package com.jsoft.tasknotes.ui.screens.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jsoft.tasknotes.R
import com.jsoft.tasknotes.ui.screens.intro.IntroActivity
import java.util.concurrent.Executors

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Executors.newSingleThreadExecutor().execute {
            Thread.sleep(2000)
            startActivityForResult(Intent(this, IntroActivity::class.java), 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1 && requestCode == 1) {
            finish()
        }
    }
}