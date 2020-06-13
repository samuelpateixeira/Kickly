package com.example.kickly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kickly.Activities.ManageTeams
import com.example.kickly.Activities.ManageTournaments
import com.example.kickly.Activities.ManageLocations
import kotlinx.android.synthetic.main.activity_manage.*

class ManageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)
        title = resources.getString(R.string.manage)

        venues.setOnClickListener { startActivity(Intent(this, ManageLocations::class.java)) }
        teams.setOnClickListener { startActivity(Intent(this, ManageTeams::class.java)) }
        tournaments.setOnClickListener { startActivity(Intent(this, ManageTournaments::class.java)) }

    }

}
