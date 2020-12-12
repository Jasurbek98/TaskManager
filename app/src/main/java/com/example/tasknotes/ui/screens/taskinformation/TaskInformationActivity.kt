package com.example.tasknotes.ui.screens.taskinformation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tasknotes.R
import com.example.tasknotes.data.room.entities.TaskData
import com.example.tasknotes.utils.MyCountDownTimer
import com.example.tasknotes.utils.extensions.toDatetime
import kotlinx.android.synthetic.main.activity_task_information.*
import java.util.*

class TaskInformationActivity : AppCompatActivity() {
    private lateinit var timer: MyCountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_information)
        setTextFields()

    }

    private fun setTextFields() {
        val data = intent.getSerializableExtra("TASK_DATA") as TaskData
        taskTitle.text = data.title
        taskTag.text = data.hashTag
        taskDate.text = data.deadline.toDatetime()
        if (data.description.isNotEmpty())
            descriptionText.text = data.description
        timer = object : MyCountDownTimer(
            data.deadline - Calendar.getInstance().timeInMillis,
            1000,
            deadlineInterval
        ) {}
        data.apply {
            when {
                rejected -> {
                    deadlineInterval.text = "Cancelled"
                }
                outOfTime -> {
                    deadlineInterval.text = "Postponed"
                }
                deleted -> {
                    deadlineInterval.text = "Deleted"
                }
                done -> {
                    deadlineInterval.text = "Done"
                }
                else -> timer.start()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

}



