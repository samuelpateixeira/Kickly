package com.example.kickly

import java.time.LocalDateTime
import java.util.*

class Match(team1: Team, team2 : Team, dateTime: LocalDateTime) {

    var team1 : Team? = team1
    var team2 : Team? = team2
    var dateTime : LocalDateTime? = dateTime
    var isFinished : Boolean = false // if the game has been played and finished
    private var _team1Score : Int? = null
    var team1Score : Int? = null
        get() {
            if (isFinished) {return _team1Score}
            else throw(Exception("game isn't finished. It's expected to happen on " + dateTime.toString()))
        }
    private var _team2Score : Int? = null
    var team2Score : Int? = null
        get() {
            if (isFinished) {return _team2Score}
            else throw(Exception("game isn't finished. It's expected to happen on " + dateTime.toString()))
        }

    fun isTie() : Boolean? {
        var tie : Boolean? = null

        if (!isFinished) { tie = null }
        else tie = _team1Score == _team2Score

        return tie
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

    // to know which team is the winner
    fun looser() : Team? {
        var looser : Team? = null
        if (isFinished) {
            //tie
            if (team1Score == team2Score) { throw(Exception("It's a tie. There's no winner.")) }
            //team 1
            else if (team1Score!! < team2Score!!) { looser = team1!! }
            //team 2
            else { looser = team2!! }
        }
        return looser
    }

    // to finish a match and set scores
    fun finish(team1Score : Int, team2Score : Int){
        isFinished = true
        this._team1Score = team1Score
        this._team2Score = team2Score
    }

}