package com.jsoft.tasknotes.ui.adapters

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.jsoft.tasknotes.R
import com.jsoft.tasknotes.data.room.entities.TaskData
import com.jsoft.tasknotes.utils.SingleBlock
import com.jsoft.tasknotes.utils.extensions.bindItem
import com.jsoft.tasknotes.utils.extensions.inflate
import com.jsoft.tasknotes.utils.extensions.timeDifference
import com.jsoft.tasknotes.utils.extensions.toDatetime
import kotlinx.android.synthetic.main.task_item.view.*
import java.util.*

class RecyclerAdapter(private val pos: Int) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var itemClickListener: SingleBlock<TaskData>? = null
    var itemEditClickListener: SingleBlock<TaskData>? = null
    var itemDeleteClickListener: SingleBlock<TaskData>? = null
    var itemCompleteClickListener: SingleBlock<TaskData>? = null
    var itemCancelClickListener: SingleBlock<TaskData>? = null
    var itemRestoreClickListener: SingleBlock<TaskData>? = null
    var itemCloneClickListener: SingleBlock<TaskData>? = null


    private val callback = object : SortedListAdapterCallback<TaskData>(this) {
        override fun areItemsTheSame(item1: TaskData, item2: TaskData): Boolean {
            return item1.id == item2.id
        }

        override fun compare(o1: TaskData, o2: TaskData): Int {
            return if (o1.deadline > o2.deadline) 1 else if (o1.deadline == o2.deadline) 0 else -1
        }

        override fun areContentsTheSame(oldItem: TaskData, newItem: TaskData): Boolean =
            oldItem.title == newItem.title && oldItem.hashTag == newItem.hashTag && oldItem.deadline == newItem.deadline
                    && oldItem.description == newItem.description && oldItem.rejected == newItem.rejected &&
                    oldItem.deleted == newItem.deleted && oldItem.done == newItem.done &&
                    oldItem.outOfTime == newItem.outOfTime
    }

    private val sortedList = SortedList(TaskData::class.java, callback)
    private fun replaceAll(list: List<TaskData>) {
        sortedList.beginBatchedUpdates()
        sortedList.replaceAll(list)
        sortedList.endBatchedUpdates()
    }

    fun submitList(list: List<TaskData>) {
        replaceAll(list)
    }

    fun setOnEditClickListener(f: SingleBlock<TaskData>) {
        itemEditClickListener = f
    }

    fun setOnDeleteClickListener(f: SingleBlock<TaskData>) {
        itemDeleteClickListener = f
    }

    fun setOnItemClickListener(f: SingleBlock<TaskData>) {
        itemClickListener = f
    }

    fun setOnCompleteClickListener(f: SingleBlock<TaskData>) {
        itemCompleteClickListener = f
    }

    fun setOnCancelClickListener(f: SingleBlock<TaskData>) {
        itemCancelClickListener = f
    }

    fun setOnRestoreClickListener(f: SingleBlock<TaskData>) {
        itemRestoreClickListener = f
    }

    fun setOnCloneClickListener(f: SingleBlock<TaskData>) {
        itemCloneClickListener = f
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.apply {
                itemLayout.setOnClickListener {
                    itemClickListener?.invoke(sortedList[adapterPosition])
                }
            }
        }

        fun handleUndoneTaskButtons() = bindItem {
            checked.visibility = View.VISIBLE
            cancelButton.visibility = View.VISIBLE

            cancelButton.setOnClickListener {
                itemCancelClickListener?.invoke(sortedList[adapterPosition])
            }
            checked.setOnClickListener {
                itemCompleteClickListener?.invoke(sortedList[adapterPosition])
            }

        }

        fun handleTrashButtons() = bindItem {
            restoreButton.visibility = View.VISIBLE
            deleteForeverButton.visibility = View.VISIBLE
            restoreButton.setOnClickListener {
                itemRestoreClickListener?.invoke(sortedList[adapterPosition])
            }
            deleteForeverButton.setOnClickListener {
                itemDeleteClickListener?.invoke(sortedList[adapterPosition])
            }
        }

        fun handleMainColors() = bindItem {
            val d = sortedList[adapterPosition]
            val now = Calendar.getInstance().timeInMillis
            val timeDifference = now.timeDifference(d.deadline)
            wrapLayout.setBackgroundResource(
                when (timeDifference.days) {
                    0 -> {
                        R.color.quiteImportant
                    }
                    in 1..3 -> {
                        R.color.mediumImportant
                    }
                    else -> {
                        R.color.defaultImportant
                    }
                }
            )
        }

        fun handleEditButtons() = bindItem {
            editButton.visibility = View.VISIBLE
            deleteButton.visibility = View.VISIBLE
            cancelButton.visibility = View.VISIBLE

            cancelButton.setOnClickListener {
                itemCancelClickListener?.invoke(sortedList[adapterPosition])
            }
            deleteButton.setOnClickListener {
                itemDeleteClickListener?.invoke(sortedList[adapterPosition])
            }
            editButton.setOnClickListener {
                itemEditClickListener?.invoke(sortedList[adapterPosition])
            }

        }

        fun handleOtherCategory() = bindItem {
            cloneButton.visibility = View.VISIBLE
            deleteForeverButton.visibility = View.VISIBLE
            priorityView.visibility = View.INVISIBLE
            cloneButton.setOnClickListener {
                itemCloneClickListener?.invoke(sortedList[adapterPosition])
            }
            deleteForeverButton.setOnClickListener {
                itemDeleteClickListener?.invoke(sortedList[adapterPosition])
            }
        }

        fun bind() = bindItem {
            val (name, date, deadline, tags, priority, description, done, rejected, outOfTime, deleted, id: Long) = sortedList[adapterPosition]
            textDeadline.text = deadline.toDatetime()
            val now = Calendar.getInstance().timeInMillis
            val (days, hours, min, sec) = date.timeDifference(now)
            textDate.text = when {
                days > 0 -> {
                    "$days days ago"
                }
                hours > 0 -> {
                    "$hours hours ago"
                }
                min > 0 -> {
                    "$min minutes ago"
                }
                else -> {
                    "moments ago"
                }
            }
            textTag.text = tags
            textTaskName.text = name
            val timeDifference = now.timeDifference(deadline)
            priorityView.setBackgroundColor(
                when (timeDifference.days) {
                    0 -> {
                        Color.RED
                    }
                    in 1..3 -> {
                        Color.YELLOW
                    }
                    else -> {
                        Color.GREEN
                    }
                }
            )
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.task_item))


    override fun getItemCount(): Int = sortedList.size()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
        when (pos) {
            3 -> {
                holder.handleUndoneTaskButtons()
            }
            in 4..6 -> {
                holder.handleOtherCategory()
            }
            9 -> {
                holder.handleEditButtons()
            }
            10 -> {
                holder.handleTrashButtons()
            }
            12 -> {
                holder.handleMainColors()
            }
        }
    }

    fun delete(taskData: TaskData) {
        sortedList.remove(taskData)
    }

    fun add(taskData: TaskData) {
        sortedList.add(taskData)
    }

    fun update(taskData: TaskData) {
        var index = -1
        for (i in 0 until sortedList.size()) {
            if (sortedList[i].id == taskData.id) {
                index = i
            }
        }
        if (index != -1) {
            sortedList.updateItemAt(index, taskData)
        }
    }
}