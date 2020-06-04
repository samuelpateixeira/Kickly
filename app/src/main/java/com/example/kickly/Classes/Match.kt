package com.example.kickly

import com.example.kickly.Classes.Location
import java.time.LocalDateTime
import java.util.*

class Match(team1: Tournament.RegisteredTeam, team2 : Tournament.RegisteredTeam, dateTime: LocalDateTime, location : Location) {

    //region properties
    var team1 : Tournament.RegisteredTeam = team1
    var team2 : Tournament.RegisteredTeam = team2
    var dateTime : LocalDateTime = dateTime
    var location = location
    var isFinished : Boolean = false // if the game has been played and finished
    private var _team1Score : Int? = null
    var team1Score : Int? = null
        get() {
            // throw exception if the game isn't finished
            if (isFinished) {return _team1Score}
            else throw(Exception("game isn't finished. It's expected to happen on " + dateTime.toString()))
        }
    private var _team2Score : Int? = null
    var team2Score : Int? = null
        get() {
            // throw exception if the game isn't finished
            if (isFinished) {return _team2Score}
            else throw(Exception("game isn't finished. It's expected to happen on " + dateTime.toString()))
        }
    //endregion

    // check if the game is a tie
    fun isTie() : Boolean? {
        var tie : Boolean? = null

        // if game isn't finished, return null
        if (!isFinished) { tie = null }
        // else, return tie or not tie
        else tie = _team1Score == _team2Score

        return tie
    }

    // to know which team is the winner
    fun winner() : Tournament.RegisteredTeam? {
        var winner : Tournament.RegisteredTeam? = null

        //if the game isn't finished
        if (!isFinished) { throw(Exception("game isn't finished. It's expected to happen on " + dateTime.toString())) }
        //tie
        else if (team1Score == team2Score) { throw(Exception("It's a tie. There's no winner. You should have used isTie() to know if it's a tie")) }
        //team 1
        else if (team1Score!! > team2Score!!) { winner = team1!! }
        //team 2
        else { winner = team2!! }

        return winner
    }

    // to know which team is the looser
    fun looser() : Tournament.RegisteredTeam? {
        var looser : Tournament.RegisteredTeam? = null

        //if the game isn't finished
        if (isFinished) { throw(Exception("game isn't finished. It's expected to happen on " + dateTime.toString())) }
        //tie
        else if (team1Score == team2Score) { throw(Exception("It's a tie. There's no winner.")) }
        //team 1
        else if (team1Score!! < team2Score!!) { looser = team1!! }
        //team 2
        else { looser = team2!! }

        return looser
    }

    // to finish a match and set scores
    fun finish(team1Score : Int, team2Score : Int){
        isFinished = true
        this._team1Score = team1Score
        this._team2Score = team2Score
    }

}