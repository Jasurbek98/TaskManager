package com.example.tasknotes.utils.extensions

import java.text.SimpleDateFormat
import java.util.*


fun String.toLongDate(pattern: String = "dd MM yyyy hh:mm"): Long =
    SimpleDateFormat(pattern, Locale.getDefault()).parse(this).time

fun String.toDate(): Date =
    SimpleDateFormat("dd MM yyyy hh:mm", Locale.getDefault()).parse(this)
