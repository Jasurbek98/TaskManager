package com.example.tasknotes.ui.screens.edit

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasknotes.R
import com.example.tasknotes.mvp.contracts.EditContract
import com.example.tasknotes.mvp.repositories.EditScreenRepository
import com.example.tasknotes.data.room.entities.TaskData
import com.example.tasknotes.mvp.presenters.EditScreenPresenter
import com.example.tasknotes.ui.adapters.RecyclerAdapter
import com.example.tasknotes.ui.dialog.AddTaskDialog
import com.example.tasknotes.ui.screens.taskinformation.TaskInformationActivity
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.task_fragment.*

class EditActivity : AppCompatActivity(), EditContract.View {
    companion object {
        private const val EDIT_RECYCLER_VALUE = 9
    }

    private lateinit var editAdapter: RecyclerAdapter

    lateinit var presenter: EditContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setToolbar()
        presenter = EditScreenPresenter(this, EditScreenRepository())
        loadRecycler()
        loadEvents()

    }

    private fun loadRecycler() {
        editAdapter = RecyclerAdapter(EDIT_RECYCLER_VALUE)
        fragmentRecyclerView.adapter = editAdapter
        fragmentRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setToolbar() {
        setSupportActionBar(toolbarEdit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Edit"
    }

    private fun loadEvents() {
        editAdapter.setOnDeleteClickListener(presenter::clickDeleteButton)
        editAdapter.setOnEditClickListener(presenter::clickOpenEditDialog)
        editAdapter.setOnCancelClickListener(presenter::clickCancelButton)
        editAdapter.setOnItemClickListener(presenter::clickItem)
    }

    override fun submitList(data: List<TaskData>) {
        editAdapter.submitList(data)
    }

    override fun openEditDialog(data: TaskData) {
        val editDialog =
            AddTaskDialog(this, "Edit", supportFragmentManager).apply { setTaskData(data) }
        editDialog.setOnClickListener(presenter::editTask)
        editDialog.show()
    }

    override fun updateTask(data: TaskData) {
        editAdapter.update(data)
    }

    override fun deleteTask(data: TaskData) {
        editAdapter.delete(data)
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
