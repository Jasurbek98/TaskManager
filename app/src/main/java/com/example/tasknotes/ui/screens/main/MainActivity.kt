package com.example.tasknotes.ui.screens.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasknotes.R
import com.example.tasknotes.data.local.LocalStorage
import com.example.tasknotes.data.models.ProfileData
import com.example.tasknotes.data.room.entities.TaskData
import com.example.tasknotes.mvp.contracts.MainContract
import com.example.tasknotes.mvp.presenters.MainScreenPresenter
import com.example.tasknotes.mvp.repositories.MainScreenRepository
import com.example.tasknotes.ui.adapters.RecyclerAdapter
import com.example.tasknotes.ui.dialog.AddTaskDialog
import com.example.tasknotes.ui.dialog.EditProfileDialog
import com.example.tasknotes.ui.screens.alltasks.AllTasksActivity
import com.example.tasknotes.ui.screens.edit.EditActivity
import com.example.tasknotes.ui.screens.history.HistoryActivity
import com.example.tasknotes.ui.screens.recyclebin.RecycleBinActivity
import com.example.tasknotes.ui.screens.taskinformation.TaskInformationActivity
import com.example.tasknotes.utils.extensions.loadFromUrl
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_layout.view.*
import kotlinx.android.synthetic.main.layout_main_content.*
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    MainContract.View {

    companion object {
        private const val MAIN_RECYCLER_VALUE = 12
    }

    private lateinit var adapter: RecyclerAdapter
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        presenter = MainScreenPresenter(this, MainScreenRepository())
        loadRecycler()
        loadEvents()

    }

    private fun loadRecycler() {
        adapter = RecyclerAdapter(MAIN_RECYCLER_VALUE)
        recyclerViewMain.layoutManager = LinearLayoutManager(this)
        recyclerViewMain.adapter = adapter
    }

    private fun loadEvents() {
        buttonMenu.setOnClickListener { presenter.clickMenuButton() }
        addTask.setOnClickListener { presenter.openAddScreen() }
        adapter.setOnItemClickListener(presenter::clickItem)
        val headerView = navigationView.getHeaderView(0)
        headerView.editProfileButton.setOnClickListener {
            val dialog = EditProfileDialog(
                this,
                "Edit",
                supportFragmentManager
            )
            dialog.setProfileData(
                ProfileData(
                    LocalStorage.instance.personProfilePicture,
                    LocalStorage.instance.personProfileName,
                    LocalStorage.instance.personProfileEmail
                )
            )
            dialog.setEditClickListener {
                headerView.imageAvatar.loadFromUrl(LocalStorage.instance.personProfilePicture!!)
                headerView.userName.text = LocalStorage.instance.personProfileName
                headerView.userEmail.text = LocalStorage.instance.personProfileEmail
            }
            dialog.show()
        }
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = null
        title
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.allTasksLayout -> {
                startActivity(Intent(this, AllTasksActivity::class.java))
                drawer.closeDrawer(GravityCompat.START, true)
            }
            R.id.recycleBinLayout -> {
                startActivity(Intent(this, RecycleBinActivity::class.java))
                drawer.closeDrawer(GravityCompat.START, true)
            }
            R.id.editLayout -> {
                startActivity(Intent(this, EditActivity::class.java))
                drawer.closeDrawer(GravityCompat.START, true)
            }
            R.id.historyLayout -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                drawer.closeDrawer(GravityCompat.START, true)
            }
            R.id.shareLayout -> {
                drawer.closeDrawer(GravityCompat.START, true)
                sendApkFile(this)
            }
            R.id.termsOfUse -> {
//                startActivity(Intent(this, ConditionsActivity::class.java))
                drawer.closeDrawer(GravityCompat.START, true)
                openPdf("terms.pdf")
            }

            R.id.conditionOfUse -> {
//                startActivity(Intent(this, ConditionsActivity::class.java))
                drawer.closeDrawer(GravityCompat.START, true)
                openPdf("instruction.pdf")
            }

        }
        return true
    }

/*    private fun openPdf(name: String) {
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
            startActivity(Intent.createChooser(pdfViewIntent, "Choose PDF viewer"))
        }
    }*/

//    private fun sendApkFile(context: Context) {
//        try {
//            val pm = context.packageManager
//            val ai = pm.getApplicationInfo(context.packageName, 0)
//            val srcFile = File(ai.publicSourceDir)
//            val intent = Intent(Intent.ACTION_SEND)
//            intent.type = "*/*"
//            val uri: Uri = FileProvider.getUriForFile(this, context.packageName, srcFile)
//            intent.putExtra(Intent.EXTRA_STREAM, uri)
//            context.grantUriPermission(
//                context.packageManager.toString(),
//                uri,
//                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
//            )
//            context.startActivity(intent)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

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
            startActivity(Intent.createChooser(pdfViewIntent, "Choose PDF viewer"))
        }
    }

    private fun sendApkFile(context: Context) {
        try {
            val pm = context.packageManager
            val ai = pm.getApplicationInfo(context.packageName, 0)
            val srcFile = File(ai.publicSourceDir)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "*/*"
            val uri: Uri = FileProvider.getUriForFile(this, "$packageName.provider", srcFile)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            context.grantUriPermission(
                context.packageManager.toString(),
                uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()

    }

    override fun onStart() {
        super.onStart()
        presenter = MainScreenPresenter(this, MainScreenRepository())
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun openInsertDialog() {
        val dialog = AddTaskDialog(this, "Add", supportFragmentManager)
        dialog.setAddClickListener {
            presenter.createTask(it)
        }
        dialog.show()
    }


    override fun submitList(data: List<TaskData>) {
        adapter.submitList(data)
    }


    override fun addAndChangeTask(data: TaskData) {
        adapter.add(data)
    }

    override fun openNavigation() {
        drawer.openDrawer(GravityCompat.START)
    }

    override fun openTaskInformationScreen(itemData: TaskData) {
        startActivity(
            Intent(this, TaskInformationActivity::class.java).putExtra(
                "TASK_DATA",
                itemData
            )
        )
    }

    override fun setNavigation() {
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.checkedItem
    }

    override fun setProfile(imageUrl: String, userName: String, userEmail: String) {
        val headerView = navigationView.getHeaderView(0)
        headerView.imageAvatar.loadFromUrl(imageUrl)
        headerView.userName.text = userName
        headerView.userEmail.text = userEmail
    }

    override fun setBackground() {
        constraintLayout.setBackgroundResource(R.drawable.main)
        toolbar.setBackgroundColor(Color.parseColor("#279D83"))

    }


}
