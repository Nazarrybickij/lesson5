package com.geekhub.rybickiylesson5

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class FeedReaderDbHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_WEATHER_CONDITIONS_TABLE)
        db.execSQL(CREATE_CITY_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(REMOVE_ALL_TABLES)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"


        internal const val CREATE_CITY_TABLE =
            "CREATE TABLE ${WeatherDbScheme.CityEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                    "${WeatherDbScheme.CityEntry.COLUMN_HASHCODE} INTEGER, " +
                    "${WeatherDbScheme.CityEntry.COLUMN_NAME_CITY} TEXT " +
                    ")"

        internal const val CREATE_WEATHER_CONDITIONS_TABLE =
            "CREATE TABLE ${WeatherDbScheme.WeatherConditionsEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                    "${WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_DT} NUMERIC, " +
                    "${WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_TEMP} REAL, " +
                    "${WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_TEMP_MIN} REAL, " +
                    "${WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_TEMP_MAX} REAL, " +
                    "${WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_PRESSURE} REAL, " +
                    "${WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_HUMIDITY} REAL, " +
                    "${WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_DESC} TEXT, " +
                    "${WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_ICON} TEXT " +

                    ")"

        internal const val REMOVE_ALL_TABLES =
            "DROP TABLE IF EXISTS ${WeatherDbScheme.CityEntry.TABLE_NAME};" +
                    "DROP TABLE IF EXISTS ${WeatherDbScheme.WeatherConditionsEntry.TABLE_NAME}"


    }


}
