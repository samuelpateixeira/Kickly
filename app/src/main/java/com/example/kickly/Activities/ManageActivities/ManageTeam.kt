package com.example.kickly.Activities.ManageActivities

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.kickly.Classes.Kickly.Companion.iconList
import com.example.kickly.Classes.Kickly.Companion.locationList
import com.example.kickly.Classes.Kickly.Companion.teamList
import com.example.kickly.R
import com.example.kickly.Team
import kotlinx.android.synthetic.main.activity_manage_location.*
import kotlinx.android.synthetic.main.activity_manage_team.*
import kotlinx.android.synthetic.main.activity_manage_team.button
import kotlinx.android.synthetic.main.activity_matches.*
import kotlinx.android.synthetic.main.icon.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ManageTeam : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var teamName: String? = null

    var createCode = 1
    var editCode = 2
    var locationID: Int? = null
    var selectCode = 3
    var resultIntent = Intent()
    var iconID: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_team)

        var locationNames = ArrayList<String>()

        for (location in locationList) {
            locationNames.add(location.name)
        }

        spinner_location.adapter = ArrayAdapter<String>(
            this,
            R.layout.spinner_item_text,
            R.id.text_view_location_name,
            locationNames
        )

        setResult(Activity.RESULT_CANCELED)

        var teamID = intent.extras!!.getInt("teamID")

        if (intent.extras!!.getInt("requestCode") == editCode) {

            img_teamImage.setImageIcon(teamList[teamID].icon)
            edit_text_team_name.setText(teamList[teamID].name)
            button.text = getString(R.string.finish).toUpperCase(Locale.ROOT)

        } else if (intent.extras!!.getInt("requestCode") == createCode) {

            img_teamImage.setImageIcon(Icon.createWithResource(this, R.drawable.image))
            button.text = getString(R.string.create).toUpperCase(Locale.ROOT)

        } else {
            throw(Exception("requestCode is invalid"))
        }

        spinner_location.onItemSelectedListener = this

        var selectIconIntent = Intent(this, TeamImagePicker::class.java)
        selectIconIntent.putExtra("teamID", teamID)
        selectIconIntent.putExtra("requestCode", selectCode)

        img_teamImage.setOnClickListener { startActivityForResult(selectIconIntent, selectCode) }

        button.setOnClickListener {

            teamName = edit_text_team_name.text.toString()

            teamList.add(Team(iconList[iconID!!], teamName!!))
            teamList.last().location = locationList[locationID!!]

            resultIntent.putExtra("teamName", teamName)
            resultIntent.putExtra("teamID", teamID)
            resultIntent.putExtra("locationID", locationID!!)
            resultIntent.putExtra("iconID", iconID!!)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()

        }


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        locationID = position
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == selectCode) {
            if (resultCode == Activity.RESULT_OK) {

                iconID = data!!.extras!!.getInt("iconID")

                img_teamImage.setImageIcon(iconList[iconID!!])

                resultIntent.putExtra("iconID", iconID!!)

            }
        }
    }
}

