package com.jsoft.tasknotes.ui.dialog


import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import com.jsoft.tasknotes.R
import com.jsoft.tasknotes.data.models.Time
import com.jsoft.tasknotes.data.room.entities.TaskData
import com.jsoft.tasknotes.utils.SingleBlock
import com.jsoft.tasknotes.utils.extensions.checkDate
import com.jsoft.tasknotes.utils.extensions.checkTime
import com.jsoft.tasknotes.utils.extensions.toDatetime
import com.jsoft.tasknotes.utils.extensions.toLongDate
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.add_task_dialog.*
import kotlinx.android.synthetic.main.add_task_dialog.view.*
import java.util.*

class AddTaskDialog(context: Context, actionName: String, fragmentManager: FragmentManager) :
    AlertDialog(context) {
    private val contentView =
        LayoutInflater.from(context).inflate(R.layout.add_task_dialog, null, false)
    private var listener: SingleBlock<TaskData>? = null
    private var addListener: SingleBlock<TaskData>? = null
    private var contact: TaskData? = null

    init {
        setTitle("$actionName task")
        setView(contentView)
        contentView.apply {
            cancelButton.text = "Cancel"
            cancelButton.setOnClickListener { dismiss() }
        }
        contentView.taskDate.setOnClickListener {
            val now = Calendar.getInstance()
            val dialog =
                DatePickerDialog.newInstance { _, year, month, dayOfMonth ->
                    TimePickerDialog.newInstance(
                        { _, hourOfDay, minute, second ->
                            val time = Time(
                                "$year",
                                "$month",
                                "$dayOfMonth",
                                "$hourOfDay",
                                "$minute",
                                "$second"
                            )
                            val date = Calendar.getInstance()
                            if (now.checkDate(time)) {
                                if (date.checkTime(time)) {
                                    taskDateLayout.error = "Select time later than now"
                                } else {
                                    taskDateLayout.error = null
                                    taskDate.setText(time.toPattern().toString())
                                }
                            } else {
                                taskDateLayout.error = null
                                taskDate.setText(time.toPattern().toString())
                            }
                        },
                        true
                    ).apply {
                        setAccentColor("#005A81")
                        setCancelColor("#013454")
                        setOkColor("#013454")
                        show(fragmentManager, "")
                    }
                }
            dialog.accentColor = getContext().resources.getColor(R.color.colorPrimary)
            dialog.minDate = now
            dialog.show(fragmentManager, "Select Date")

        }

        when (actionName) {
            "Add" -> {
                contentView.addButton.text = actionName
            }
            "Clone" -> {
                contentView.addButton.text = actionName
            }
            else -> {
                contentView.addButton.text = actionName
            }
        }
        contentView.addButton.setOnClickListener {
            val data = contact ?: TaskData("", 0, 0, "", 0, "")
            when {
                contentView.taskName.text.toString().isEmpty() -> {
                    taskNameLayout.error = "Title field is empty"
                    return@setOnClickListener
                }
                contentView.taskDate.text.toString().isEmpty() -> {
                    taskDateLayout.error = "Date field is empty"
                    return@setOnClickListener
                }
                contentView.taskTag.text.toString().isEmpty() -> {
                    taskTagLayout.error = "Tag field is empty"
                    return@setOnClickListener
                }
                !contentView.taskTag.text.toString().startsWith("#") -> {
                    taskTagLayout.error = "Tag must start with '#' sign"
                    return@setOnClickListener
                }
                contentView.taskDescription.text.toString().isEmpty() -> {
                    taskDescriptionLayout.error = "Description field is empty"
                    return@setOnClickListener
                }
            }
            data.title = contentView.taskName.text.toString()
            data.hashTag = contentView.taskTag.text.toString()
            val datetext = contentView.taskDate.text.toString()
            if (datetext.isNotEmpty())
                data.deadline = datetext.toLongDate("dd.MM.yyyy HH:mm")
            data.description = contentView.taskDescription.text.toString()
            data.date = Calendar.getInstance().timeInMillis
            addListener?.invoke(data)
            listener?.invoke(data)
            cancel()
        }

    }

    fun setTaskData(data: TaskData, clearDate: Boolean = false) = with(contentView) {
        this@AddTaskDialog.contact = data.copy()
        taskName.setText(data.title)
        taskDate.setText(if (clearDate) "" else data.deadline.toDatetime("dd.MM.yyyy HH:mm"))
        taskTag.setText(data.hashTag)
        taskDescription.setText(data.description)
    }

    fun setOnClickListener(block: SingleBlock<TaskData>?) {
        listener = block
    }

    fun setAddClickListener(block: SingleBlock<TaskData>?) {
        addListener = block
    }

}