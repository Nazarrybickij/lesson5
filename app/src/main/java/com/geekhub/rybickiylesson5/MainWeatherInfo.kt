package com.geekhub.rybickiylesson5

import android.os.Parcel
import android.os.Parcelable

data class MainWeatherInfo(
    val temp: Double,
    val humidity: Double,
    val pressure: Double,
    val temp_min: Double,
    val temp_max: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(temp)
        parcel.writeDouble(humidity)
        parcel.writeDouble(pressure)
        parcel.writeDouble(temp_min)
        parcel.writeDouble(temp_max)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainWeatherInfo> {
        override fun createFromParcel(parcel: Parcel): MainWeatherInfo {
            return MainWeatherInfo(parcel)
        }

        override fun newArray(size: Int): Array<MainWeatherInfo?> {
            return arrayOfNulls(size)
        }
    }
}
