package com.jsoft.tasknotes.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jsoft.tasknotes.data.models.ListData
import com.jsoft.tasknotes.data.room.entities.TaskData
import com.jsoft.tasknotes.ui.fragments.AllTasksFragment
import com.jsoft.tasknotes.utils.SingleBlock
import com.jsoft.tasknotes.utils.extensions.putArguments

class AllTasksAdapter(val list: List<TaskData>, activity: FragmentActivity) :
    FragmentStateAdapter(activity) {
    private var data: ArrayList<List<TaskData>> = ArrayList()
    private var itemClickListener: SingleBlock<TaskData>? = null
    private var completeClickListener: SingleBlock<TaskData>? = null
    private var cancelClickListener: SingleBlock<TaskData>? = null
    private var cloneClickListener: SingleBlock<TaskData>? = null
    private var deleteClickListener: SingleBlock<TaskData>? = null

    init {
        data.add(list.filter { it.outOfTime })
        data.add(list.filter { it.done })
        data.add(list.filter { it.rejected })
        data.add(list.filter { !it.outOfTime && !it.done && !it.rejected && !it.deleted })
    }

    override fun getItemCount() = data.size
    override fun createFragment(position: Int): Fragment = AllTasksFragment()
        .apply {
            itemClickListener?.let { this.setItemClickListener(it) }
            completeClickListener?.let { this.setCompleteClickListener(it) }
            cancelClickListener?.let { this.setCancelClickListener(it) }
            cloneClickListener?.let { this.setCloneClickListener(it) }
            deleteClickListener?.let { this.setDeleteClickListener(it) }
        }
        .putArguments {
            val filtered = ListData(data[position])
            putSerializable("TASK_DATA", filtered)
            putInt("POSITION", position)
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
}
