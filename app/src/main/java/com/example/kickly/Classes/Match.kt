package com.example.kickly

import com.example.kickly.Classes.Location
import java.time.LocalDateTime

class Match(team1: Tournament.RegisteredTeam, team2 : Tournament.RegisteredTeam, dateTime: LocalDateTime, location : Location) {

    //region properties
    var team1 : Tournament.RegisteredTeam = team1
    var team2 : Tournament.RegisteredTeam = team2
    var dateTime : LocalDateTime = dateTime
    var location = location
    var isFinished : Boolean = false // if the game has been played and finished
    private var _team1Score = 0
    var team1Score = 0
        get() {
            // throw exception if the game isn't finished
            if (isFinished) {return _team1Score}
            else throw(Exception("game isn't finished. It's expected to happen on " + dateTime.toString()))
        }
    private var _team2Score  = 0
    var team2Score = 0
        get() {
            // throw exception if the game isn't finished
            if (isFinished) {return _team2Score}
            else throw(Exception("game isn't finished. It's expected to happen on " + dateTime.toString()))
        }


    var stats : Stats? = null

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
    fun finish(team1Score : Int, team2Score : Int, stats: Stats){

        isFinished = true

        // scores
        this._team1Score = team1Score
        this._team2Score = team2Score

        // increment team matches
        this.team1.matches++
        this.team2.matches++

        // increase team points
        if (isTie() == true) {
            this.team1.points += 1
            this.team2.points += 1

        } else if (winner() == this.team1) {
            this.team1.points += 3
        }

        else if (winner() == this.team2) {
            this.team2.points += 3
        }

        // increase goals scored and conceded
        this.team1.goalsScored = this.team1Score
        this.team2.goalsScored = this.team2Score
        this.team1.goalsConceded = this.team2Score
        this.team2.goalsConceded = this.team1Score

        this.stats = stats

    }

}

class Stats (
                 team1TotalAttempts : Int,
                 team2TotalAttempts : Int,

                 team1AttemptsOnTarget : Int,
                 team2AttemptsOnTarget : Int,

                 team1FoulsCommited : Int,
                 team2FoulsCommited : Int,

                 team1YellowCards : Int,
                 team2YellowCards : Int,

                 team1RedCards : Int,
                 team2RedCards : Int,

                 team1Offsides : Int,
                 team2Offsides : Int,

                 team1Corners : Int,
                 team2Corners : Int,

                 team1Possession : Int
) {


    var team1TotalAttempts = team1TotalAttempts
    var team2TotalAttempts = team2TotalAttempts
    var team1AttemptsOnTarget = team1AttemptsOnTarget
    var team2AttemptsOnTarget = team2AttemptsOnTarget
    var team1FoulsCommited = team1FoulsCommited
    var team2FoulsCommited = team2FoulsCommited
    var team1YellowCards = team1YellowCards
    var team2YellowCards = team2YellowCards
    var team1RedCards = team1RedCards
    var team2RedCards = team2RedCards
    var team1Offsides = team1Offsides
    var team2Offsides = team2Offsides
    var team1Corners = team1Corners
    var team2Corners = team2Corners
    var team1Possession = if (team1Possession > 100) {100} else if (team1Possession < 0) {0} else {team1Possession}
        set(value) {if (value > 100) {field = 100} else if (value < 0) {field = 0}}
    var team2Possession = 100 - team1Possession
        private set
        get() {field = 100 - team1Possession; return field
        }


}