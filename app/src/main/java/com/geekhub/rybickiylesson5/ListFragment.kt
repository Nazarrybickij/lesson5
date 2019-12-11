package com.geekhub.rybickiylesson5

import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Suppress("UNREACHABLE_CODE")
class ListFragment : Fragment() {


    var location: String? = null
    var cashTempSet = String()
    var adapter = Adapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView!!.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapter
        if (savedInstanceState == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            val weatherService = retrofit.create<WeatherService>(WeatherService::class.java)
            val messages = weatherService.getCurrentWeather(
                location.toString(),
                "c5c73ba1d229c20d8f3c05715269c00f"
            )
            val dbHelper = FeedReaderDbHelper(view.context)

            val db = dbHelper.writableDatabase

            messages.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    adapter.addWeather(response.body())
                    adapter.cashTempSet = cashTempSet

                    db.delete(WeatherDbScheme.WeatherConditionsEntry.TABLE_NAME, null, null)

                    val valuesCity = ContentValues().apply {
                        put(WeatherDbScheme.CityEntry.COLUMN_HASHCODE, response.body()?.city?.id)
                        put(WeatherDbScheme.CityEntry.COLUMN_NAME_CITY, response.body()?.city?.name)
                    }
                    var count = 0
                    while (count < 40) {
                        val valuesList = ContentValues().apply {
                            put(
                                WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_DT,
                                response.body()?.list!![count].dt
                            )
                            put(
                                WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_TEMP,
                                response.body()?.list!![count].main?.temp
                            )
                            put(
                                WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_TEMP_MIN,
                                response.body()?.list!![count].main?.temp_min
                            )
                            put(
                                WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_TEMP_MAX,
                                response.body()?.list!![count].main?.temp_max
                            )
                            put(
                                WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_PRESSURE,
                                response.body()?.list!![count].main?.pressure
                            )
                            put(
                                WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_HUMIDITY,
                                response.body()?.list!![count].main?.humidity
                            )
                            put(
                                WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_DESC,
                                response.body()?.list!![count].weather[0].description
                            )
                            put(
                                WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_ICON,
                                response.body()?.list!![count].weather[0].icon
                            )
                        }
                        val newRowIdList =
                            db?.insert(
                                WeatherDbScheme.WeatherConditionsEntry.TABLE_NAME,
                                null,
                                valuesList
                            )
                        count++

                    }


                    val newRowId =
                        db?.insert(WeatherDbScheme.CityEntry.TABLE_NAME, null, valuesCity)


                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Toast.makeText(view.context, "Maybe Internet", Toast.LENGTH_LONG).show()


                    val cursorCity = db.query(
                        WeatherDbScheme.CityEntry.TABLE_NAME,   // The table to query
                        null,             // The array of columns to return (pass null to get all)
                        null,              // The columns for the WHERE clause
                        null,          // The values for the WHERE clause
                        null,                   // don't group the rows
                        null,                   // don't filter by row groups
                        "_id DESC LIMIT 1"               // The sort order
                    )


                    var cityRes: City? = null

                    with(cursorCity) {
                        while (moveToNext()) {
                            val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                            val hash =
                                getInt(getColumnIndexOrThrow(WeatherDbScheme.CityEntry.COLUMN_HASHCODE))
                            val nameCity =
                                getString(getColumnIndexOrThrow(WeatherDbScheme.CityEntry.COLUMN_NAME_CITY))
                            val city = City(hash.toLong(), nameCity)
                            cityRes = city
                        }
                    }

                    cursorCity.close()

                    val q = "SELECT * FROM weather_conditions ORDER BY _id ASC LIMIT 40"
                    val cursorList = db.rawQuery(q, null)


                    val listWeather = mutableListOf<ListWeather>()
                    with(cursorList) {
                        while (moveToNext()) {
                            val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                            val temp =
                                getDouble(getColumnIndexOrThrow(WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_TEMP))
                            val tempMin =
                                getDouble(getColumnIndexOrThrow(WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_TEMP_MIN))
                            val tempMax =
                                getDouble(getColumnIndexOrThrow(WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_TEMP_MAX))
                            val pressure =
                                getDouble(getColumnIndexOrThrow(WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_PRESSURE))
                            val humidity =
                                getDouble(getColumnIndexOrThrow(WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_HUMIDITY))
                            val des =
                                getString(getColumnIndexOrThrow(WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_DESC))
                            val icon =
                                getString(getColumnIndexOrThrow(WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_ICON))
                            val dt =
                                getLong(getColumnIndexOrThrow(WeatherDbScheme.WeatherConditionsEntry.COLUMN_NAME_DT))
                            val mainInfo =
                                MainWeatherInfo(temp, humidity, pressure, tempMin, tempMax)
                            val weather = Weather(des, icon)
                            val mutList = mutableListOf<Weather>()
                            mutList.add(weather)
                            val weatherIn: List<Weather> = mutList
                            val ll = ListWeather(mainInfo, weatherIn, dt)
                            listWeather.add(ll)
                        }
                    }

                    cursorList.close()

                    if (cityRes != null) {
                        val weatherResponse = WeatherResponse(cityRes!!, listWeather)
                        adapter.addWeather(weatherResponse)
                        adapter.cashTempSet = cashTempSet
                    }
                }
            })
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
            R.layout.list_fragment,
            container, false
        )


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }


}