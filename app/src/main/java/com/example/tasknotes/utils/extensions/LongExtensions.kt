package com.example.tasknotes.utils.extensions

import com.example.tasknotes.data.models.DateDifference
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

fun Long.toDatetime(pattern: String = "dd MMM yyyy HH:mm"): String? =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)
//      android.icu.text.SimpleDateFormat(pattern,Locale.getDefault()).format(this)

fun Long.timeDifference(afterDate: Long): DateDifference {
    var different = abs(afterDate - this)

    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24

    val elapsedDays = (different / daysInMilli).toInt()
    different %= daysInMilli

    val elapsedHours = (different / hoursInMilli).toInt()
    different %= hoursInMilli

    val elapsedMinutes = (different / minutesInMilli).toInt()
    different %= minutesInMilli

    val elapsedSeconds = (different / secondsInMilli).toInt()
    return DateDifference(elapsedDays,elapsedHours,elapsedMinutes,elapsedSeconds)

}