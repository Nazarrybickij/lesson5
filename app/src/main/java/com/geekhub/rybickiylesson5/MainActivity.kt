package com.geekhub.rybickiylesson5

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), Adapter.AdapterCallback {

    lateinit var pref: SharedPreferences
    var cashTempSet = String()
    val myFragmentManager = supportFragmentManager


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listFragment = ListFragment()
        var desFragment = DescriptionFragment()
        pref = getSharedPreferences(Cash.APP_PREFERENCES, MODE_PRIVATE)
        val cash = Cash(pref)
        title = cash.getCash()
        cashTempSet = cash.getCashTemp()


        if (savedInstanceState == null) {
            listFragment.adapter.delegate = this
            listFragment.location = cash.getCash()
            listFragment.cashTempSet = cashTempSet
            myFragmentManager.beginTransaction().run {
                add(R.id.list_fragment_view, listFragment, "list")
                commit()
            }
            myFragmentManager.beginTransaction().run {
                add(R.id.descriotion_fragmentland_view, desFragment, "des")
                commit()
            }
        }

    }

    override fun onItemClick(output: ArrayList<ListWeather>, position: Int, day: String) {
        val fragment = myFragmentManager.findFragmentByTag("des") as DescriptionFragment
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val intent = Intent(this, DescriptionActivity()::class.java)
            intent.putParcelableArrayListExtra("weather", output)
            intent.putExtra("position", position)
            intent.putExtra("temp", cashTempSet)
            intent.putExtra("day", day)
            intent.putExtra("day", fragment.id)
            startActivity(intent)
            fragment.addList(weatherWeek(output, position))
            fragment.cashTempSet = cashTempSet
            fragment.desAdapter.allDay = weatherWeek(output, position)
            fragment.desAdapter.cashTempSet = cashTempSet
            fragment.desAdapter.notifyDataSetChanged()

        } else {

            fragment.addList(weatherWeek(output, position))
            fragment.cashTempSet = cashTempSet
            fragment.desAdapter.allDay = weatherWeek(output, position)
            fragment.desAdapter.cashTempSet = cashTempSet
            fragment.desAdapter.notifyDataSetChanged()

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.seting_item -> {
                val intent = Intent(this, SettingActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun weatherWeek(intentList: ArrayList<ListWeather>, position: Int): ArrayList<ListWeather> {
        var curentList = ArrayList<ListWeather>()
        var count = 0
        val sdd = java.text.SimpleDateFormat("d")
        var nodate: Date

        while (count < 39) {
            nodate = Date(intentList[count].dt * 1000L)
            if (position == sdd.format(nodate).toInt()) {
                curentList.add(intentList[count])
            }
            count++
        }


        return curentList
    }


}


