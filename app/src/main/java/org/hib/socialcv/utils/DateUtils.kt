package org.hib.socialcv.utils

import java.text.SimpleDateFormat

class DateUtils {

    fun getFormattedDate(dateFromServer: String): String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormatter = SimpleDateFormat("dd, MMM yyyy")
        val date = inputFormatter.parse(dateFromServer)
        return outputFormatter.format(date)
    }

    fun getDayInMonth(dateFromServer: String): String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormatter = SimpleDateFormat("d")
        val date = inputFormatter.parse(dateFromServer)
        return outputFormatter.format(date)
    }

    fun getMonthInYear(dateFromServer: String): String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormatter = SimpleDateFormat("MMM")
        val date = inputFormatter.parse(dateFromServer)
        return outputFormatter.format(date)
    }
}