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
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import kotlinx.android.synthetic.main.activity_watching.*

class WatchingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watching)
        title = resources.getString(R.string.watching)


        // set Tournament Summary Adapter
        lvTournaments.adapter =  KicklyTools.Adapters.TournamentSummary(this, tournamentList)

    }

}
