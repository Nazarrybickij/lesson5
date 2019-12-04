package com.geekhub.rybickiylesson5


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekhub.rybickiylesson5.Cash.Companion.KELVINCASH
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_view.view.*


class Adapter() :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    var delegate: AdapterCallback? = null
    var weatherDay = mutableListOf<ListWeather>()
    var allDay = ArrayList<ListWeather>()
    var cashTempSet = String()
    interface AdapterCallback {
        fun onItemClick(
            output: ArrayList<ListWeather>,
            position: Int,
            day: String
        )

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBind(values = weatherDay[position])
    }


    override fun getItemCount() = weatherDay.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_view, parent, false)


        return ViewHolder(itemView)
    }

    fun addWeather(wthr: WeatherResponse?) {
        if (wthr != null) {
            allDay = ArrayList(wthr.list)
            weatherDay.add(wthr.list[0])
            val curentDate = java.util.Date(wthr.list[0].dt * 1000L)
            val curentSdf = java.text.SimpleDateFormat("dd")
            for (i in 1..39) {
                val date = java.util.Date(wthr.list[i].dt * 1000L)
                val sdh = java.text.SimpleDateFormat("HH")
                val sdd = java.text.SimpleDateFormat("dd")

                if (sdh.format(date).toString() == "11" && sdd.format(date).toString() != curentSdf.format(
                        curentDate
                    ).toString()
                ) {
                    weatherDay.add(wthr.list[i])
                    notifyDataSetChanged()
                } else if (sdh.format(date).toString() == "12" && sdd.format(date).toString() != curentSdf.format(
                        curentDate
                    ).toString()
                ) {
                    weatherDay.add(wthr.list[i])
                    notifyDataSetChanged()
                } else if (sdh.format(date).toString() == "13" && sdd.format(date).toString() != curentSdf.format(
                        curentDate
                    ).toString()
                ) {
                    weatherDay.add(wthr.list[i])
                    notifyDataSetChanged()
                }
            }
        }

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val date = java.util.Date(weatherDay[adapterPosition].dt * 1000L)
            val sdf = java.text.SimpleDateFormat("d")
            val sdq = java.text.SimpleDateFormat("EEE")
            delegate?.onItemClick(allDay, sdf.format(date).toInt(), sdq.format(date))


        }

        fun onBind(values: ListWeather) {

            itemView.description_view.text = values.weather[0].description
            Picasso.get()
                .load("http://openweathermap.org/img/wn/" + values.weather[0].icon + "@2x.png")
                .into(itemView.icon_view)

            val date = java.util.Date(values.dt * 1000L)
            val sdf = java.text.SimpleDateFormat("dd/MM")
            itemView.date_view.text = sdf.format(date)
            if (cashTempSet == KELVINCASH) {
                itemView.temp_view.text = values.main?.temp.toString() + "°"
            } else {
                itemView.temp_view.text = values.main?.temp?.minus(273.15)?.toInt().toString()+ "°"
            }
        }

    }
}