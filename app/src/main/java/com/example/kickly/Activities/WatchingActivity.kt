package com.example.kickly

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import kotlinx.android.synthetic.main.activity_matches.*
import kotlinx.android.synthetic.main.activity_watching.*
import kotlinx.android.synthetic.main.activity_watching.llNoMatchesScheduled

class WatchingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watching)
        title = resources.getString(R.string.watching)

        if (tournamentList.isNotEmpty()) {

            // set Tournament Summary Adapter
            lvTournaments.adapter = KicklyTools.Adapters.TournamentSummary(this, tournamentList)

        } else {
            lvTournaments.visibility = View.GONE
            llNoMatchesScheduled.visibility = View.VISIBLE
        }

    }

}
