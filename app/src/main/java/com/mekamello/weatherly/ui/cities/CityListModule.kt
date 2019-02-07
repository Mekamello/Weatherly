package com.mekamello.weatherly.ui.cities

import android.content.Context
import com.mekamello.weatherly.di.PerActivity
import dagger.Module
import dagger.Provides

@Module
class CityListModule(
    private val context: Context
) {
    @Provides
    @PerActivity
    fun provideActivityContext(): Context = context
}