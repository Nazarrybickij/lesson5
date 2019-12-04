package com.geekhub.rybickiylesson5
import android.os.Parcel
import android.os.Parcelable
data class ListWeather(
    val main: MainWeatherInfo?,
    val weather: List<Weather>,
    val dt: Long
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(MainWeatherInfo::class.java.classLoader),
        parcel.createTypedArrayList(Weather)!!,
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(main, flags)
        parcel.writeTypedList(weather)
        parcel.writeLong(dt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListWeather> {
        override fun createFromParcel(parcel: Parcel): ListWeather {
            return ListWeather(parcel)
        }

        override fun newArray(size: Int): Array<ListWeather?> {
            return arrayOfNulls(size)
        }
    }
}

