package com.alexcfa.yourweather.ui.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter



@RequiresApi(Build.VERSION_CODES.O)
fun getActualDateString(): String {
    val actualDate = LocalDate.now()
    val dateFormat = DateTimeFormatter.ofPattern("d MMM uuu")
    return actualDate.format(dateFormat)
}