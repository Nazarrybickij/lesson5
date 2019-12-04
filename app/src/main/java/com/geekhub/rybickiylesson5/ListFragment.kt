package com.geekhub.rybickiylesson5

import android.os.Bundle
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
            val messages =
                weatherService.getCurrentWeather(
                    location.toString(),
                    "c5c73ba1d229c20d8f3c05715269c00f"
                )

            messages.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    adapter.addWeather(response.body())
                    adapter.cashTempSet = cashTempSet


                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Toast.makeText(view.context, "Maybe Internet", Toast.LENGTH_LONG).show()
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