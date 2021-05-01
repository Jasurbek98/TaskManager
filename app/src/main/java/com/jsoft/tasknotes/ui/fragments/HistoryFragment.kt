package com.jsoft.tasknotes.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jsoft.tasknotes.R
import com.jsoft.tasknotes.data.models.ListData
import com.jsoft.tasknotes.data.room.entities.TaskData
import com.jsoft.tasknotes.ui.adapters.RecyclerAdapter
import com.jsoft.tasknotes.utils.SingleBlock
import kotlinx.android.synthetic.main.task_fragment.*

class HistoryFragment : Fragment(R.layout.task_fragment) {
    private var itemClickListener: SingleBlock<TaskData>? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = requireArguments()
        val filteredList = bundle.getSerializable("HISTORY_DATA") as ListData
        val data = filteredList.ls
        val position = bundle.getInt("POS")
        val adapter = RecyclerAdapter(position)
        adapter.submitList(data)
        fragmentRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fragmentRecyclerView.adapter = adapter

        adapter.setOnItemClickListener {
            itemClickListener?.invoke(it)
        }

/*   2-usul     adapter.apply {
            setOnItemClickListener {
                startActivity(
                    Intent(context, TaskInformationActivity::class.java).putExtra(
                        "TASK_DATA",
                        it
                    )
                )
            }
        }*/
    }

    fun setItemClickListener(block: SingleBlock<TaskData>) {
        itemClickListener = block
    }
}