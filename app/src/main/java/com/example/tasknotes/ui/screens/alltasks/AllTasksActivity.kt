package com.example.tasknotes.ui.screens.alltasks

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.tasknotes.R
import com.example.tasknotes.mvp.contracts.AllTasksContract
import com.example.tasknotes.mvp.repositories.AllTasksRepository
import com.example.tasknotes.data.room.entities.TaskData
import com.example.tasknotes.mvp.presenters.AllTasksPresenter
import com.example.tasknotes.ui.adapters.AllTasksAdapter
import com.example.tasknotes.ui.dialog.AddTaskDialog
import com.example.tasknotes.ui.screens.main.MainActivity
import com.example.tasknotes.ui.screens.taskinformation.TaskInformationActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_all_tasks.*

class AllTasksActivity : AppCompatActivity(), AllTasksContract.View {
    private lateinit var adapter: AllTasksAdapter
    lateinit var presenter: AllTasksContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tasks)
        setSupportActionBar(toolbar2)
        title = "All tasks"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = AllTasksPresenter(this, AllTasksRepository())
    }


    override fun openInformationScreen(data: TaskData) {
        startActivity(Intent(this, TaskInformationActivity::class.java).apply {
            putExtra(
                "TASK_DATA",
                data
            )
        })
    }

    override fun submitList(data: List<TaskData>) {
        val eachTabTitle = listOf("Postponed", "Done", "Cancelled", "Undone")
        val eachTabIcon = listOf(
            R.drawable.ic_baseline_postponed_circle_,
            R.drawable.ic_baseline_done,
            R.drawable.ic_baseline_cancelled_,
            R.drawable.ic_baseline_new
        )
        adapter = AllTasksAdapter(data, this)
        ViewPagerList.adapter = adapter
        TabLayoutMediator(tabLayout, ViewPagerList) { tab, position ->
            tab.setIcon(eachTabIcon[position])
            tab.text = eachTabTitle[position]
        }.attach()
        handleClicks()
    }

    override fun openCloneDialog(data: TaskData) {
        val openDialog =
            AddTaskDialog(this, "Clone", supportFragmentManager).apply { setTaskData(data, true) }
        openDialog.setOnClickListener(presenter::cloneTask)
        openDialog.show()
    }

    override fun moveToMainScreen() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun handleClicks() {
        adapter.setItemClickListener(presenter::clickItem)
        adapter.setCompleteClickListener(presenter::clickCompletedButton)
        adapter.setCancelClickListener(presenter::clickCancelButton)
        adapter.setCloneClickListener(presenter::clickOpenCloneDialog)
        adapter.setDeleteClickListener(presenter::clickDeleteButton)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

}
