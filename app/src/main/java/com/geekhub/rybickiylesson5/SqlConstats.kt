package com.geekhub.rybickiylesson5

import android.provider.BaseColumns

object WeatherDbScheme {

    object CityEntry: BaseColumns {

        const val TABLE_NAME = "city"
        const val COLUMN_HASHCODE = "hashcode"
        const val COLUMN_NAME_CITY = "name"

    }

    object WeatherConditionsEntry : BaseColumns {

        const val TABLE_NAME = "weather_conditions"
        const val COLUMN_NAME_DT = "dt"
        const val COLUMN_NAME_TEMP = "temp"
        const val COLUMN_NAME_TEMP_MIN = "temp_min"
        const val COLUMN_NAME_TEMP_MAX = "temp_max"
        const val COLUMN_NAME_PRESSURE = "pressure"
        const val COLUMN_NAME_HUMIDITY = "humidity"
        const val COLUMN_NAME_DESC = "description"
        const val COLUMN_NAME_ICON = "icon"


    }
}