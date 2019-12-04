package com.geekhub.rybickiylesson5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList

@Suppress(
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class DescriptionActivity : AppCompatActivity() {
    val descriptionFragment = DescriptionFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        val intentList: ArrayList<ListWeather> =
            intent.getParcelableArrayListExtra<ListWeather>("weather")
        val position = intent.getIntExtra("position", 0)
        val cashTempSet = intent.getStringExtra("temp")
        title = intent.getStringExtra("day")
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().run {
                add(R.id.descriotion_fragment_view, descriptionFragment, "des2")
                commit()
            }
        } else {
            finish()
        }
        descriptionFragment.addList(weatherWeek(intentList, position))
        descriptionFragment.cashTempSet = cashTempSet
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
