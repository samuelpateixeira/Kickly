package com.example.kickly.Activities.ManageActivities

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
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
    var requestCode: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_team)

        requestCode = createCode
        intent.extras!!.getInt("requestCode")?.let {
            if (it == editCode || it == createCode) {
                requestCode = it
            }
        }

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

        if (requestCode == editCode) {

            title = getString(R.string.edit_team)

            teamList[teamID].icon

            // find teams iconID from iconList index
            iconID = null
            var i = 0
            var team = teamList[teamID]
            while (iconID == null && i < iconList.size) {
                if (team.icon == iconList[i]) { iconID = i }
                i++
            }


            img_teamImage.setImageIcon(teamList[teamID].icon)
            edit_text_team_name.setText(teamList[teamID].name)
            button.text = getString(R.string.finish).toUpperCase(Locale.ROOT)


            // find team location locationID from locationList index
            locationID == null
            i = 0

            while (locationID == null && i < locationList.size) {
                if (teamList[teamID].location == locationList[i]) { locationID = i }
                i++
            }

            spinner_location.setSelection(locationID!!)

        } else if (requestCode == createCode) {

            title = getString(R.string.create_team)

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

            if ( edit_text_team_name.text.toString() == "" ) {
                Toast.makeText(this, getString(R.string.please_choose_a_team_name), Toast.LENGTH_SHORT).show()
            } else if ( iconID == null ) {
                Toast.makeText(this, getString(R.string.please_choose_an_icon), Toast.LENGTH_SHORT).show()
            } else {

                teamName = edit_text_team_name.text.toString()

                if (requestCode == createCode) {

                    teamList.add(Team(iconList[iconID!!], teamName!!))
                    teamList.last().location = locationList[locationID!!]

                }

                resultIntent.putExtra("teamName", teamName)
                resultIntent.putExtra("teamID", teamID)
                resultIntent.putExtra("locationID", locationID!!)

                resultIntent.putExtra("iconID", iconID!!)

                setResult(Activity.RESULT_OK, resultIntent)
                finish()

            }
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

