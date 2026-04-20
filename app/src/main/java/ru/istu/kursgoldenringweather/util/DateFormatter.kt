package ru.istu.kursgoldenringweather.util

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Locale

object DateFormatter {

    private const val API_DATE = "yyyy-MM-dd"
    private const val API_DATE_TIME = "yyyy-MM-dd'T'HH:mm"

    fun formatDate(dateIso: String, context: Context): String {
        if (dateIso.isBlank()) return "—"
        val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
        return runCatching {
            val parser = SimpleDateFormat(API_DATE, Locale.US)
            val formatter = SimpleDateFormat("EEE, d MMM", locale)
            formatter.format(parser.parse(dateIso)!!)
        }.getOrElse { dateIso }
    }

    fun formatDateTime(dateTimeIso: String, context: Context): String {
        if (dateTimeIso.isBlank()) return "—"
        val locale = context.resources.configuration.locales[0] ?: Locale.getDefault()
        return runCatching {
            val parser = SimpleDateFormat(API_DATE_TIME, Locale.US)
            val formatter = SimpleDateFormat("d MMM, HH:mm", locale)
            formatter.format(parser.parse(dateTimeIso)!!)
        }.getOrElse { dateTimeIso }
    }
}
