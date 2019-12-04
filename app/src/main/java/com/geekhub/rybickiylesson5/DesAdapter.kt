package com.geekhub.rybickiylesson5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.listdes_item_view.view.*
import java.text.SimpleDateFormat
import java.util.*

class DesAdapter :
    RecyclerView.Adapter<DesAdapter.ViewHolder>() {
    var allDay: MutableList<ListWeather>? = null
    var cashTempSet = String()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.listdes_item_view, parent, false)


        return ViewHolder(itemView)
    }

    override fun getItemCount() = allDay!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(values = allDay!![position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(values: ListWeather) {
            itemView.desdescription_view.text = values.weather[0].description
            Picasso.get()
                .load("http://openweathermap.org/img/wn/" + values.weather[0].icon + "@2x.png")
                .into(itemView.desicon_view)

            val date = Date(values.dt * 1000L)
            val sdf = SimpleDateFormat("hh:mm a")
            val sdd = SimpleDateFormat("EEE")
            itemView.clock_view.text = sdf.format(date)
            itemView.day_view.text = sdd.format(date)
            if (cashTempSet == Cash.KELVINCASH) {
                itemView.destemp_view.text = values.main?.temp_min.toString()+"/"+ values.main?.temp_max.toString() +"°"
            } else {
                itemView.destemp_view.text =
                    values.main?.temp_min?.minus(273.15)?.toInt().toString()+"/"+ values.main?.temp_max?.minus(273.15)?.toInt().toString() + "°"
            }

        }
    }
}