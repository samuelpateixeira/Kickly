package com.example.kickly.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kickly.Activities.ManageActivities.ManageTeam
import com.example.kickly.Activities.ManageActivities.ManageTournament
import com.example.kickly.Activities.ManageActivities.ManageTournamentsActivities.CreateTournament
import com.example.kickly.Classes.Kickly
import com.example.kickly.Classes.Kickly.Companion.checkData
import com.example.kickly.KicklyTools
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_manage_locations.*
import kotlinx.android.synthetic.main.activity_manage_locations.btnCreate
import kotlinx.android.synthetic.main.activity_manage_locations.recyclerView
import kotlinx.android.synthetic.main.activity_manage_tournaments.*
import kotlinx.android.synthetic.main.activity_matches.*
import kotlinx.android.synthetic.main.activity_manage_locations.llNoMatchesScheduled as llNoMatchesScheduled1
import kotlinx.android.synthetic.main.activity_manage_locations.message_empty as message_empty1
import kotlinx.android.synthetic.main.activity_manage_tournaments.llNoMatchesScheduled as llNoMatchesScheduled1
import kotlinx.android.synthetic.main.activity_manage_tournaments.message_empty as message_empty1

class ManageTournaments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_tournaments)
        title = getString(R.string.manage_tournaments)

        checkData()

        if (Kickly.tournamentList.isNotEmpty()) {

            listView.adapter = KicklyTools.Adapters.ManageTournamentsRV(this, Kickly.tournamentList)

        } else {

            listView.visibility = View.GONE
            llNoMatchesScheduled.visibility = View.VISIBLE
            message_empty.text = getString(R.string.no_tournaments_yet)

        }



        var intentCreate = Intent(this, CreateTournament::class.java)
        intentCreate.putExtra("tournamentID", Kickly.tournamentList.size)
        intentCreate.putExtra("requestCode", createCode)

        btnCreate.setOnClickListener {
            startActivityForResult(intentCreate, createCode) }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        finish()
        startActivity(intent)

    }

}
