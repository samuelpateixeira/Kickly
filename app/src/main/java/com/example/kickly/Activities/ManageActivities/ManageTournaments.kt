package com.example.kickly.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kickly.Activities.ManageActivities.ManageTeam
import com.example.kickly.Activities.ManageActivities.ManageTournament
import com.example.kickly.Classes.Kickly
import com.example.kickly.KicklyTools
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_manage_locations.*
import kotlinx.android.synthetic.main.activity_manage_locations.btnCreate
import kotlinx.android.synthetic.main.activity_manage_locations.recyclerView
import kotlinx.android.synthetic.main.activity_manage_tournaments.*
import kotlinx.android.synthetic.main.activity_matches.*

class ManageTournaments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_tournaments)
        title = getString(R.string.manage_tournaments)

        listView.adapter = KicklyTools.Adapters.ManageTournamentsRV(this, Kickly.tournamentList)

    /*
        var intentCreate = Intent(this, ManageTournament::class.java)
        intentCreate.putExtra("tournamentID", Kickly.tournamentList.size)
        intentCreate.putExtra("requestCode", createCode)

        btnCreate.setOnClickListener {
            startActivity(intentCreate) }

    }
    */
    }
}