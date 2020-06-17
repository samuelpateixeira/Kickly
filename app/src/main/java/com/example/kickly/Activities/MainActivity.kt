package com.example.kickly.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kickly.*
import com.example.kickly.Classes.Kickly.Companion.iconList
import com.example.kickly.Classes.Kickly.Companion.locationList
import com.example.kickly.Classes.Kickly.Companion.teamList
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import java.time.LocalDateTime
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    var buttonList = ArrayList<IconTextActivity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        context = applicationContext

        generate()

        // create intent for Main Menu
        var mainMenu = Intent(this, MainMenuActivity::class.java)

        // start first page
        startActivity(mainMenu)

    }

    override fun onResume() {
        super.onResume()

        // create intent for Main Menu
        var mainMenu = Intent(this, MainMenuActivity::class.java)

        // start first page
        startActivity(mainMenu)

    }

    companion object {
        var context : Context? = null
            private set
    }

    fun generate () {

        //tournamentList = KicklyTools.Generate.tournamentList(this)
        locationList = KicklyTools.Generate.locationList(this)
        iconList = KicklyTools.Generate.iconList(this)
        //teamList = KicklyTools.Generate.teamList(this)

        var randomGen = Random(LocalDateTime.now().nano.toInt())

        fun randomPosInt() : Int {
            return Math.abs(randomGen.nextInt())
        }
        fun randomYear() : Int {
            return (randomPosInt() % 4) + 2019
        }


        fun randomMonth() : Int {
            return (randomPosInt() % 12) + 1
        }

        fun randomDay() : Int {
            return (randomPosInt() % 28) + 1
        }

        fun randomHour() : Int {
            return (randomPosInt() % 24)
        }

        fun randomMinute() : Int {
            return (randomPosInt() % 60)
        }


        //region teams
        var teamNames = resources.getStringArray(R.array.teamNames)

        var i = 0
        var j = 0

        while( i < teamNames.size ) {

            if (j >= iconList.size) { j = 0 }

            teamList.add( Team( iconList[j], teamNames[i] ))

            i++
            j++
        }

        for (i in 0 until (teamList.size)){
            teamList[i].location = locationList[i % (locationList.size)]
        }
        //endregion

        //region tournaments

        // create tournaments
        fun createTournament(name : String) {
            tournamentList.add(Tournament(
                iconList[randomPosInt() % iconList.size],
                name))

            // generate teams
            i = 0
            var group: Char = 'A'
            while (i < 16 && i < teamList.size) {
                if (i == 4) {
                    group = 'B'
                }
                if (i == 8) {
                    group = 'C'
                }
                if (i == 12) {
                    group = 'D'
                }
                tournamentList.last().registeredTeams.add(
                    Tournament.RegisteredTeam(
                        teamList[i],
                        group
                    )
                )
                i++
            }

            // generate matches
            i = 0
            var opponentID = tournamentList.last().registeredTeams.size - 1
            var locationID = 0
            while (i < tournamentList.last().registeredTeams.size) {

                var isSame = true
                while (isSame) {
                    opponentID = randomPosInt() % tournamentList.last().registeredTeams.size
                    isSame = i == opponentID
                }

                locationID = randomPosInt() % locationList.size

                tournamentList.last().matches.add(
                    Match(
                        tournamentList.last().registeredTeams[i],
                        tournamentList.last().registeredTeams[opponentID],
                        LocalDateTime.of(
                            randomYear(),
                            randomMonth(),
                            randomDay(),
                            randomHour(),
                            randomMinute()
                        )
                        , locationList[locationID]
                    )
                )
                i++
            }

            tournamentList.last().orderMatches()

            var finishedMatches = randomPosInt() % (tournamentList.last().matches.size - 2) + 1
            for (i in 0 until finishedMatches) {
                tournamentList.last().matches[i].finish(
                    randomPosInt() % 6,
                    randomPosInt() % 6,
                    Stats(
                        randomPosInt() % 50,
                        randomPosInt() % 50,
                        randomPosInt() % 50,
                        randomPosInt() % 50,
                        randomPosInt() % 50,
                        randomPosInt() % 50,
                        randomPosInt() % 50,
                        randomPosInt() % 50,
                        randomPosInt() % 50,
                        randomPosInt() % 50,
                        randomPosInt() % 50,
                        randomPosInt() % 50,
                        randomPosInt() % 50,
                        randomPosInt() % 50,
                        randomPosInt() % 20 + 40
                    )
                )
            }
        }

        createTournament("National Kotlin Tournament")
        createTournament("Mega Tournament")
        createTournament("IDK")
        createTournament("Crazy Tournament")
        createTournament("Null Tournament")
        createTournament("World Championship")
        //endregion

    }
}

