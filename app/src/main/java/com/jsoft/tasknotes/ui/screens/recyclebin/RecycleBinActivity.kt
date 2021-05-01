package com.jsoft.tasknotes.ui.screens.recyclebin

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jsoft.tasknotes.R
import com.jsoft.tasknotes.mvp.contracts.RecycleContract
import com.jsoft.tasknotes.mvp.repositories.RecycleScreenRepository
import com.jsoft.tasknotes.data.room.entities.TaskData
import com.jsoft.tasknotes.mvp.presenters.RecycleScreenPresenter
import com.jsoft.tasknotes.ui.adapters.RecyclerAdapter
import com.jsoft.tasknotes.ui.screens.taskinformation.TaskInformationActivity
import kotlinx.android.synthetic.main.activity_recycle_bin.*
import kotlinx.android.synthetic.main.task_fragment.*

class RecycleBinActivity : AppCompatActivity(), RecycleContract.View {
    companion object {
        private const val RECYCLE_BIN_RECYCLER_VALUE = 10
    }

    private lateinit var trashAdapter: RecyclerAdapter
    lateinit var presenter: RecycleContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_bin)
        presenter = RecycleScreenPresenter(this, RecycleScreenRepository())
        setToolbar()
        loadAdapter()
        loadEvents()

    }

    private fun setToolbar() {
        setSupportActionBar(toolbarTrash)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Recycle Bin"
    }

    private fun loadAdapter() {
        trashAdapter = RecyclerAdapter(RECYCLE_BIN_RECYCLER_VALUE)
        fragmentRecyclerView.adapter = trashAdapter
        fragmentRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun loadEvents() {
        trashAdapter.setOnRestoreClickListener(presenter::clickRestoreButton)
        trashAdapter.setOnDeleteClickListener(presenter::clickDeleteButton)
        trashAdapter.setOnItemClickListener(presenter::clickItem)
    }

    override fun submitList(data: List<TaskData>) {
        trashAdapter.submitList(data)
    }

    override fun deleteTask(data: TaskData) {
        trashAdapter.delete(data)
    }

    override fun openTaskInformation(data: TaskData) {
        startActivity(
            Intent(this, TaskInformationActivity::class.java).putExtra(
                "TASK_DATA",
                data
            )
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
