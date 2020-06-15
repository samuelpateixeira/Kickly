package com.example.kickly.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kickly.Activities.ManageActivities.ManageLocation
import com.example.kickly.Classes.Kickly.Companion.locationList
import com.example.kickly.KicklyTools
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_manage_locations.*
import kotlinx.android.synthetic.main.activity_matches.recyclerView

var createCode = 1
var editCode = 2
var locations = ArrayList<com.example.kickly.Classes.Location>()

class ManageLocations : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_locations)

        title = getString(R.string.manage_locations)

        var intentCreate = Intent(this, ManageLocation::class.java)
        intentCreate.putExtra("locationID", locationList.size)
        intentCreate.putExtra("requestCode", createCode)

        btnCreate.setOnClickListener { startActivityForResult(intentCreate, createCode) }

        recyclerView.adapter = KicklyTools.Adapters.Locations(this, locationList)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var locationName : String? = null

        if (requestCode == editCode) {
            if (resultCode == Activity.RESULT_OK) {

                locationName = data!!.extras!!.getString("locationName")
                var locationID = data.extras!!.getInt("locationID")

                locationList[locationID].name = locationName!!
            }
        } else if (requestCode == createCode) {
            if (resultCode == Activity.RESULT_OK) {

                locationName = data!!.extras!!.getString("locationName")
                locationList.add(com.example.kickly.Classes.Location(locationName!!))
            }
        }

        recyclerView.adapter!!.notifyDataSetChanged()
    }
}