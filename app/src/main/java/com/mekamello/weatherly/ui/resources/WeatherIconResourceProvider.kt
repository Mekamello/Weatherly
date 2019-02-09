package com.mekamello.weatherly.ui.resources

import android.support.annotation.DrawableRes
import com.mekamello.weatherly.R
import com.mekamello.weatherly.utils.DateUtils
import javax.inject.Inject

interface WeatherIconResourceProvider {
    @DrawableRes
    fun getIcon(weatherId: Int): Int
}

class WeatherIconResourceProviderImpl
@Inject constructor(
    private val dateUtils: DateUtils
) : WeatherIconResourceProvider {
    override fun getIcon(weatherId: Int): Int = when (weatherId) {
        in (200 until 300) -> getThunderstormIcon()
        in (300 until 400) -> getDrizzleIcon()
        in (500 until 600) -> getRainIcon()
        in (600 until 700) -> getSnowIcon()
        in (700 until 800) -> getAtmosphereIcon()
        800 -> getClearIcon()
        in (801 until 900) -> getCloudsIcon(weatherId)
        else -> -1 // Unknown type
    }

    private fun getThunderstormIcon(): Int = R.drawable.ic_thunderstorm

    private fun getDrizzleIcon(): Int = R.drawable.ic_light_rain

    private fun getRainIcon(): Int = R.drawable.ic_heavy_rain

    private fun getSnowIcon(): Int = R.drawable.ic_snow

    private fun getAtmosphereIcon(): Int = R.drawable.ic_mist

    private fun getClearIcon(): Int = if (dateUtils.isCurrentlyNight()) {
        R.drawable.ic_clearly_night
    } else {
        R.drawable.ic_clearly_day
    }

    private fun getCloudsIcon(weatherId: Int): Int = when(weatherId) {
        801 -> if(dateUtils.isCurrentlyNight()){
            R.drawable.ic_cloudy_night
        } else {
            R.drawable.ic_cloudy_day
        }
        else -> R.drawable.ic_overcast
    }
}