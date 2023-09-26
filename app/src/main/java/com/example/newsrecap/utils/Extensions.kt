package com.example.newsrecap.utils

import com.example.newsrecap.utils.constants.Constants
import java.text.SimpleDateFormat
import java.util.Locale

fun String.parseDate(): String {
    val outputFormat = SimpleDateFormat("HH:mm | dd.MM", Locale.getDefault())
    val date = try {
        SimpleDateFormat(Constants.DEFAULT_TIME_FORMAT, Locale.getDefault()).parse(this)
    } catch (e: Exception) {
        SimpleDateFormat(Constants.RESERVE_TIME_FORMAT, Locale.getDefault()).parse(this)
    }
    return date?.let { outputFormat.format(it) } ?: ""
}