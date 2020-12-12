package com.example.tasknotes.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasknotes.R
import com.example.tasknotes.app.App
import com.example.tasknotes.data.models.ListData
import com.example.tasknotes.data.room.AppDatabase
import com.example.tasknotes.data.room.entities.TaskData
import com.example.tasknotes.ui.adapters.RecyclerAdapter
import com.example.tasknotes.utils.SingleBlock
import kotlinx.android.synthetic.main.task_fragment.*
import java.util.concurrent.Executors

class AllTasksFragment :
    Fragment(R.layout.task_fragment) {
    private val handle = Handler(Looper.getMainLooper())
    private val executor = Executors.newSingleThreadExecutor()
    private val taskDao = AppDatabase.getDatabase(App.instance).taskDao()

    private var itemClickListener: SingleBlock<TaskData>? = null
    private var completeClickListener: SingleBlock<TaskData>? = null
    private var cancelClickListener: SingleBlock<TaskData>? = null
    private var cloneClickListener: SingleBlock<TaskData>? = null
    private var deleteClickListener: SingleBlock<TaskData>? = null

    private var position = 0
    lateinit var adapter: RecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = requireArguments()
//        val filteredSerializableList = bundle.getSerializable("TASK_DATA") as ListData
//        val data = filteredSerializableList.ls
        if (bundle.getInt("POSITION") in 0..2) {
            position = bundle.getInt("POSITION") + 4
        } else {
            position = bundle.getInt("POSITION")
        }

       /* val adapter = RecyclerAdapter(position)
        adapter.submitList(data)
        fragmentRecyclerView.layoutManager =
            LinearLayoutManager(App.instance.baseContext, LinearLayoutManager.VERTICAL, false)
        fragmentRecyclerView.adapter = adapter*/

        
        /* adapter.apply {
             setOnItemClickListener {
                 startActivity(
                     Intent(context, TaskInformationActivity::class.java).putExtra(
                         "TASK_DATA",
                         it
                     )
                 )
             }
             setOnCompleteClickListener {
                 it.done = true
                 taskDao.update(it)
                 adapter.delete(it)
             }
             setOnCancelClickListener {
                 it.rejected = true
                 taskDao.update(it)
                 adapter.delete(it)
             }
         }*/

    }

    override fun onResume() {
        super.onResume()
        adapter = RecyclerAdapter(position)
//        adapter.submitList(data)
        fragmentRecyclerView.layoutManager =
            LinearLayoutManager(App.instance.baseContext, LinearLayoutManager.VERTICAL, false)
        fragmentRecyclerView.adapter = adapter
        when (position) {
            3 -> runOnWorkerThread {
                val data = taskDao.getMainTasks()
                runOnUIThread {
                    adapter.submitList(data)
                }
            }

            4 -> runOnWorkerThread {
                val data = taskDao.getOldTasks()
                runOnUIThread {
                    adapter.submitList(data)
                }
            }

            5 -> runOnWorkerThread {
                val data = taskDao.getCompletedTasks()
                runOnUIThread {
                    adapter.submitList(data)
                }
            }

            6 -> runOnWorkerThread {
                val data = taskDao.getRejectedTasks()
                runOnUIThread {
                    adapter.submitList(data)
                }
            }
        }
        adapter.setOnItemClickListener {
            itemClickListener?.invoke(it)
        }
        adapter.setOnCompleteClickListener {
            completeClickListener?.invoke(it)
            adapter.delete(it)
        }
        adapter.setOnCancelClickListener {
            cancelClickListener?.invoke(it)
            adapter.delete(it)
        }
        adapter.setOnCloneClickListener {
            cloneClickListener?.invoke(it)
//            adapter.delete(it)
        }
        adapter.setOnDeleteClickListener {
            deleteClickListener?.invoke(it)
            adapter.delete(it)
        }

    }

    fun setItemClickListener(f: SingleBlock<TaskData>) {
        itemClickListener = f
    }

    fun setCompleteClickListener(f: SingleBlock<TaskData>) {
        completeClickListener = f
    }

    fun setCancelClickListener(f: SingleBlock<TaskData>) {
        cancelClickListener = f
    }

    fun setCloneClickListener(f: SingleBlock<TaskData>) {
        cloneClickListener = f
    }

    fun setDeleteClickListener(f: SingleBlock<TaskData>) {
        deleteClickListener = f
    }

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