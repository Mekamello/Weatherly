package com.mekamello.weatherly.utils

import java.util.*
import java.util.concurrent.TimeUnit

class DateUtils {
    private val tenMinutesInMills = TimeUnit.MINUTES.toMillis(10)
    private val calendar = Calendar.getInstance()

    fun getCurrentTimeInMills() = System.currentTimeMillis()

    fun isCurrentlyNight(): Boolean {
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 3)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startNight = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 5)
        calendar.set(Calendar.MINUTE, 30)
        val endNight = calendar.timeInMillis
        val current = getCurrentTimeInMills()
        return current in startNight until endNight
    }

    fun isPastTenMinutes(date: Long): Boolean {
        val after = getCurrentTimeInMills() - tenMinutesInMills
        return date <= after
    }

    fun isNow(date: Long): Boolean {
        calendar.timeInMillis = getCurrentTimeInMills()
        val todayYear = calendar.get(Calendar.YEAR)
        val todayMonth = calendar.get(Calendar.MONTH)
        val todayDay = calendar.get(Calendar.DAY_OF_MONTH)
        val todayHour = calendar.get(Calendar.HOUR_OF_DAY)
        calendar.timeInMillis = date
        val dateYear = calendar.get(Calendar.YEAR)
        val dateMonth = calendar.get(Calendar.MONTH)
        val dateDay = calendar.get(Calendar.DAY_OF_MONTH)
        val dateHour = calendar.get(Calendar.HOUR_OF_DAY)
        return todayYear == dateYear
                && todayMonth == dateMonth
                && todayDay == dateDay
                && todayHour in (dateHour - 3)..dateHour
    }

    fun getMidnightTimeInMills(date: Long): Long {
        calendar.timeInMillis = date
        setToMidnight(calendar)
        return calendar.timeInMillis
    }

    fun getDayOfWeek(date: Long): String {
        calendar.timeInMillis = date
        setToMidnight(calendar)
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
    }

    private fun setToMidnight(calendar: Calendar) {
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
    }
}