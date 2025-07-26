package com.example.common.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleHelper {

    fun updateLocale(activity: Activity, localeCode: String) {
        val locale = Locale(localeCode)
        Locale.setDefault(locale)

        val config = Configuration(activity.resources.configuration)
        config.setLocale(locale)

        activity.resources.updateConfiguration(config, activity.resources.displayMetrics)

        activity.applicationContext.resources.updateConfiguration(
            config,
            activity.applicationContext.resources.displayMetrics
        )
    }

    fun getLocale(context: Context): String {
        return context.resources.configuration.locales[0].language
    }
}
