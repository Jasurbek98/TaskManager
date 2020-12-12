package com.example.tasknotes.ui.screens.intro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.tasknotes.R
import com.example.tasknotes.data.local.LocalStorage
import com.example.tasknotes.data.models.IntroData
import com.example.tasknotes.mvp.contracts.IntoContract
import com.example.tasknotes.mvp.presenters.IntroPresenter
import com.example.tasknotes.mvp.repositories.IntroRepository
import com.example.tasknotes.ui.adapters.IntroAdapter
import com.example.tasknotes.ui.screens.main.MainActivity
import com.example.tasknotes.utils.extensions.changeNavigationBarColor
import com.example.tasknotes.utils.extensions.changeStatusBarColor
import com.example.tasknotes.utils.extensions.toDarkenColor
import kotlinx.android.synthetic.main.activity_intro.*
import java.io.File
import java.io.FileOutputStream

class IntroActivity : AppCompatActivity(), IntoContract.View {
    lateinit var adapter: IntroAdapter
    lateinit var presenter: IntoContract.Presenter
    lateinit var model: IntoContract.Model
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        model = IntroRepository()
        presenter = IntroPresenter(this, model)
        loadData()
        if (LocalStorage.instance.isFirst) {
            loadData()
        } else {
            startActivityForResult(Intent(this, MainActivity::class.java), 2)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent()
        setResult(1, intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 2 && requestCode == 2) {
            onBackPressed()
        }
    }

    private fun loadData() {
        terms.setOnClickListener { openPdf("terms.pdf") }
        adapter = IntroAdapter(model.getData())
        pager.adapter = adapter
        indicator.setViewPager2(pager)
        nextButton.setOnClickListener { presenter.clickNext() }
        pager.registerOnPageChangeCallback(onPageChangeCallBack)
    }

    private val onPageChangeCallBack = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            layoutContent.setBackgroundColor(model.getData()[position].background)
            changeStatusBarColor(model.getData()[position].background.toDarkenColor())
            changeNavigationBarColor(model.getData()[position].background.toDarkenColor())
        }
    }

    override fun moveToNextScreen(data: List<IntroData>) {
        if (pager.currentItem != data.size - 1) {
            pager.setCurrentItem(pager.currentItem + 1, true)
        } else {
            if (agreement.isChecked) {
                LocalStorage.instance.isFirst = false
                startActivityForResult(Intent(this, MainActivity::class.java), 2)
            }else{
                Toast.makeText(this,"Siz shartlargaga rozilik berishingizga to'g'ri keladi",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openPdf(name: String) {
        // Open the PDF file from raw folder
        val inputStream =
            when (name) {
                "instruction.pdf" -> resources.openRawResource(R.raw.instruction)
                else -> resources.openRawResource(R.raw.terms)
            }
        // Copy the file to the cache folder
        inputStream.use { inputStream ->
            val file = File(cacheDir, name)
            FileOutputStream(file).use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (inputStream.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
        }

        val cacheFile = File(cacheDir, name)

        // Get the URI of the cache file from the FileProvider
        val uri = FileProvider.getUriForFile(this, "$packageName.provider", cacheFile)
        if (uri != null) {
            // Create an intent to open the PDF in a third party app
            val pdfViewIntent = Intent(Intent.ACTION_VIEW)
            pdfViewIntent.data = uri
            pdfViewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(Intent.createChooser(pdfViewIntent, "Choos PDF viewer"))
        }
    }
}
