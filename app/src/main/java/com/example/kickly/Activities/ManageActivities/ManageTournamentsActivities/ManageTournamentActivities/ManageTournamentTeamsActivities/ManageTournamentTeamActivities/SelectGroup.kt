package com.example.kickly.Activities.ManageActivities.ManageTournamentsActivities.ManageTournamentActivities.ManageTournamentTeamsActivities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kickly.Classes.Kickly
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import com.example.kickly.KicklyTools
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_manage_locations.*
import kotlinx.android.synthetic.main.activity_matches.*
import kotlinx.android.synthetic.main.activity_matches.recyclerView

class SelectGroup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_group)

        title = getString(R.string.select_group)


        var tournamentID = intent.extras!!.getInt("tournamentID")

        var groups = tournamentList[tournamentID].getGroupsString()

        setResult(Activity.RESULT_CANCELED)

        recyclerView.adapter = KicklyTools.Adapters.GroupsSelect(this, groups)
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnCreate.setOnClickListener {

            var tournamentID = intent.extras!!.getInt("tournamentID")
            var otherTeamsID = intent.extras!!.getInt("otherTeamsID")
            intent.extras!!.getInt("requestCode")

            var nextGroup = tournamentList[tournamentID].nextGroup()

            var resultIntent = Intent()

            resultIntent.putExtra("tournamentID", tournamentID)
            resultIntent.putExtra("otherTeamsID", otherTeamsID)
            resultIntent.putExtra("group", nextGroup.toString())

            setResult(Activity.RESULT_OK, resultIntent)
            finish()



        }

    }
}