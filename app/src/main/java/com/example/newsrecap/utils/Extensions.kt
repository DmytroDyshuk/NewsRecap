package com.example.newsrecap.utils

import com.example.newsrecap.utils.constants.Constants
import java.text.SimpleDateFormat
import java.util.Locale

fun String.parseTime(): String {
    val inputFormat: SimpleDateFormat = try {
        SimpleDateFormat(Constants.DEFAULT_TIME_FORMAT, Locale.getDefault())
    } catch (e: Exception) {
        SimpleDateFormat(Constants.RESERVE_TIME_FORMAT, Locale.getDefault())
    }
    val outputFormat = SimpleDateFormat("HH:mm | dd.MM", Locale.getDefault())
    val date = inputFormat.parse(this)
    return date?.let { outputFormat.format(it) } ?: ""
}