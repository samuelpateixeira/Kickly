package com.example.kickly

import java.time.LocalDateTime
import java.util.*

class Match(team1: Team, team2 : Team, dateTime: LocalDateTime) {

    var team1 : Team? = null
    var team2 : Team? = null
    var dateTime : LocalDateTime? = null
    var isFinished : Boolean = false // if the game has been played and finished
    var team1Score : Int? = null
        get() {
            if (isFinished) {return team1Score}
            else throw(Exception("game isn't finished. It's expected to happen on " + dateTime.toString()))
        }
    var team2Score : Int? = null
        get() {
            if (isFinished) {return team2Score}
            else throw(Exception("game isn't finished. It's expected to happen on " + dateTime.toString()))
        }

    // to know which team is the winner
    fun winner() : Team? {
        var winner : Team? = null
        if (isFinished) {
            //tie
            if (team1Score == team2Score) { throw(Exception("It's a tie. There's no winner.")) }
            //team 1
            else if (team1Score!! > team2Score!!) { winner = team1!! }
            //team 2
            else { winner = team2!! }
        }
        return winner
    }

}