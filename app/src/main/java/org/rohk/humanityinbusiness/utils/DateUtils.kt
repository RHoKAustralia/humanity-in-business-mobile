package org.rohk.humanityinbusiness.utils

import java.text.SimpleDateFormat

class DateUtils {

    fun getFormattedDate(dateFromServer: String): String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormatter = SimpleDateFormat("F, MMM yyyy")
        val date = inputFormatter.parse(dateFromServer)
        return outputFormatter.format(date)
    }
}