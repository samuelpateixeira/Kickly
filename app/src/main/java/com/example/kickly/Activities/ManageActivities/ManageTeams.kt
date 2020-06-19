package com.example.kickly.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kickly.Activities.ManageActivities.ManageLocation
import com.example.kickly.Activities.ManageActivities.ManageTeam
import com.example.kickly.Classes.Kickly
import com.example.kickly.Classes.Kickly.Companion.checkData
import com.example.kickly.Classes.Kickly.Companion.iconList
import com.example.kickly.Classes.Kickly.Companion.locationList
import com.example.kickly.Classes.Kickly.Companion.putTeamToAPI
import com.example.kickly.Classes.Kickly.Companion.teamList
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import com.example.kickly.KicklyTools
import com.example.kickly.R
import com.example.kickly.Team
import kotlinx.android.synthetic.main.activity_manage_locations.*
import kotlinx.android.synthetic.main.activity_matches.*
import kotlinx.android.synthetic.main.activity_matches.recyclerView
import kotlinx.android.synthetic.main.activity_matches.llNoMatchesScheduled as llNoMatchesScheduled1
import kotlinx.android.synthetic.main.activity_matches.message_empty as message_empty1

class ManageTeams : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_locations)

        checkData()

        title = getString(R.string.manage_teams)


        var intentCreate = Intent(this, ManageTeam::class.java)
        intentCreate.putExtra("requestCode", createCode)

        btnCreate.setOnClickListener {
            startActivityForResult(intentCreate, createCode) }

        if (teamList.isNotEmpty()) {

            recyclerView.adapter = KicklyTools.Adapters.TeamsEdit(this, teamList)
            recyclerView.layoutManager = LinearLayoutManager(this)

        } else {

            recyclerView.visibility = View.GONE
            llNoMatchesScheduled.visibility = View.VISIBLE
            message_empty.text = getString(R.string.no_teams_yet)

        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            finish()
            startActivity(intent)

        var teamName : String? = null

        if (requestCode == editCode) {
            if (resultCode == Activity.RESULT_OK) {

                teamName = data!!.extras!!.getString("teamName")
                var teamID = data.extras!!.getInt("teamID")
                var locationID = data.extras!!.getInt("locationID")
                var iconID = data.extras!!.getInt("iconID")

                teamList[teamID].name = teamName!!
                teamList[teamID].icon = iconList[iconID]
                teamList[teamID].location = locationList[locationID]

                putTeamToAPI(teamList[teamID])

            }
        } else if (requestCode == createCode) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }

    }

}