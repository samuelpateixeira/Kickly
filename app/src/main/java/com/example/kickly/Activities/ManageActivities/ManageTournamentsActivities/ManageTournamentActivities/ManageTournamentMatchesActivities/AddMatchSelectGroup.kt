package com.example.kickly.Activities.ManageActivities.ManageTournamentsActivities.ManageTournamentActivities.ManageTournamentMatchesActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kickly.Classes.Kickly
import com.example.kickly.Classes.Kickly.Companion.checkData
import com.example.kickly.KicklyTools
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_manage_locations.*

class AddMatchSelectGroup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_group)

        checkData()

        title = getString(R.string.select_group)

        btnCreate.visibility = View.GONE

        var tournamentID = intent.extras!!.getInt("tournamentID")

        var groups = Kickly.tournamentList[tournamentID].getGroupsString()

        recyclerView.adapter = KicklyTools.Adapters.AddMatchGroupsSelect(this, groups)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
}