package com.example.kickly.Activities.ManageActivities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kickly.Classes.Kickly.Companion.locationList
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_manage_location.*
import java.lang.Exception
import java.util.*

class ManageLocation : AppCompatActivity() {

    var locationName : String? = null

    var createCode = 1
    var editCode = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_location)

        setResult(Activity.RESULT_CANCELED)

        var locationID = intent.extras!!.getInt("locationID")

        if (intent.extras!!.getInt("requestCode") == editCode) {


            edit_text_location_name.setText(locationList[locationID].name)
            button.text = getString(R.string.finish).toUpperCase(Locale.ROOT)

        } else if (intent.extras!!.getInt("requestCode") == createCode) {

            button.text = getString(R.string.create).toUpperCase(Locale.ROOT)

        } else {
            throw(Exception("requestCode is invalid"))
        }

        button.setOnClickListener {

            locationName = edit_text_location_name.text.toString()

        var resultIntent = Intent()
            //resultIntent.putExtra("locationName", locationName)
            //resultIntent.putExtra("locationID", locationID)

            if (locationName == "") { Toast.makeText(this, getString(R.string.please_choose_a_location_name), Toast.LENGTH_SHORT).show() } else {

                if (intent.extras!!.getInt("requestCode") == editCode) {

                    locationList[locationID].name = locationName!!

                } else if (intent.extras!!.getInt("requestCode") == createCode) {

                    locationList.add(com.example.kickly.Classes.Location(locationName!!))

                }

                setResult(Activity.RESULT_OK, resultIntent)
                finish()

            }
        }
    }
}