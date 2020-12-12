package com.example.tasknotes.ui.screens.history

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.tasknotes.R
import com.example.tasknotes.app.App
import com.example.tasknotes.mvp.contracts.HistoryContract
import com.example.tasknotes.mvp.repositories.HistoryRepository
import com.example.tasknotes.data.room.AppDatabase
import com.example.tasknotes.data.room.entities.TaskData
import com.example.tasknotes.mvp.presenters.HistoryPresenter
import com.example.tasknotes.ui.adapters.HistoryAdapter
import com.example.tasknotes.ui.screens.taskinformation.TaskInformationActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity(), HistoryContract.View {

    private lateinit var adapter: HistoryAdapter
    val taskData = AppDatabase.getDatabase(App.instance).taskDao()
    lateinit var presenter: HistoryContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setToolbar()
        presenter = HistoryPresenter(this, HistoryRepository())
    }

    private fun setToolbar() {
        setSupportActionBar(toolbarHistory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "History"
    }

    override fun openInformationScreen(data: TaskData) {
        startActivity(
            Intent(this, TaskInformationActivity::class.java).putExtra(
                "TASK_DATA",
                data
            )
        )
    }

    override fun submitList(data: List<TaskData>) {
        val eachTabTitle = listOf("Done", "Cancelled", "Postponed")
        val eachTabIcon = listOf(
            R.drawable.ic_baseline_done,
            R.drawable.ic_baseline_cancelled_,
            R.drawable.ic_baseline_postponed_circle_
        )
        adapter = HistoryAdapter(data, this)
        historyViewPager.adapter = adapter
        TabLayoutMediator(historyTabLayout, historyViewPager) { tab, position ->
            tab.setIcon(eachTabIcon[position])
            tab.text = eachTabTitle[position]
        }.attach()
        adapter.setItemClickListener(presenter::clickItem)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

}
