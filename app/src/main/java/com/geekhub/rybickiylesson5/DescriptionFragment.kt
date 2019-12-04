package com.geekhub.rybickiylesson5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DescriptionFragment: Fragment() {
    var list = ArrayList<ListWeather>()
    var cashTempSet = String()
    val desAdapter = DesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_descrip)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        desAdapter.allDay = list
        desAdapter.cashTempSet = cashTempSet
        recyclerView.adapter = desAdapter


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.description_fragment,
            container, false
        )

    }


    fun addList(
        list: ArrayList<ListWeather>
    ){
        this.list = list

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
}