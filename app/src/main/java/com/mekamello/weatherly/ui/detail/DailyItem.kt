package com.mekamello.weatherly.ui.detail

data class DailyItem(
    val date: Long,
    val dayOfWeek: String,
    val weatherId: Int,
    var weatherDescription: String,
    val max: Float,
    val min: Float
) {
    fun getMinTempString(): String {
        val minValue = min.toInt()
         return if (minValue > 0) {
             "+$minValue"
         } else {
             minValue.toString()
         }
    }

    fun getMaxTempString(): String {
        val maxValue = max.toInt()
        return if (maxValue > 0) {
            "+$maxValue"
        } else {
            maxValue.toString()
        }
    }
}