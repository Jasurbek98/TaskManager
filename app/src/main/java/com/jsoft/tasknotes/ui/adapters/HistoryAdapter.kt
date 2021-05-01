package com.jsoft.tasknotes.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jsoft.tasknotes.data.models.ListData
import com.jsoft.tasknotes.data.room.entities.TaskData
import com.jsoft.tasknotes.ui.fragments.HistoryFragment
import com.jsoft.tasknotes.utils.SingleBlock
import com.jsoft.tasknotes.utils.extensions.putArguments

class HistoryAdapter(val list: List<TaskData>, activity: FragmentActivity) :
    FragmentStateAdapter(activity) {
    private val data: ArrayList<List<TaskData>> = ArrayList()
    private var itemListener: SingleBlock<TaskData>? = null

    init {
        data.add(list.filter { it.done })
        data.add(list.filter { it.rejected })
        data.add(list.filter { it.outOfTime })
    }


    override fun getItemCount(): Int = data.size

    override fun createFragment(position: Int): Fragment = HistoryFragment()
        .apply { itemListener?.let { this.setItemClickListener(it) } }
        .putArguments {
            val ls = ListData(data[position])
            putSerializable("HISTORY_DATA", ls)
            putInt("POS", position)
        }

    fun setItemClickListener(f: SingleBlock<TaskData>) {
        itemListener = f
    }

}