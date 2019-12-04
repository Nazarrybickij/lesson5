package com.geekhub.rybickiylesson5

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.geekhub.rybickiylesson5.Cash.Companion.APP_PREFERENCES
import com.geekhub.rybickiylesson5.Cash.Companion.CELSIUSCASH
import com.geekhub.rybickiylesson5.Cash.Companion.KELVINCASH
import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity : AppCompatActivity() {

    lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        val cash = Cash(pref)
        location_editText.setText(cash.getCash())
        val arrayTemp = arrayOf(CELSIUSCASH, KELVINCASH)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayTemp)
        spinner.adapter = adapter



        button_save.setOnClickListener {
            cash.saveCash(location_editText.text.toString())
            cash.saveCashTemp(spinner.selectedItem.toString())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
