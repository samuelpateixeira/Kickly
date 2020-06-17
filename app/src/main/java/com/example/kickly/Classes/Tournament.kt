package com.example.kickly

import android.graphics.drawable.Icon
import com.example.kickly.Classes.Kickly
import com.example.kickly.Classes.Stage
import java.lang.Exception
import java.time.LocalDateTime

class Tournament( icon : Icon, name: String/*, currentStage : Stage*/ ) {

    //region properties
    var icon : Icon? = icon
    var name : String? = name
    var currentStage : Stage? = Stage.GROUPSTAGE
    //var nextMatch : Match? = nextMatch
    var registeredTeams = ArrayList<RegisteredTeam>()
    var matches = ArrayList<Match>()
    //endregion

    // registered team class
    // Team + information about this team participation
    class RegisteredTeam( var team : Team, var group : Char? ) {

        var active : Boolean = true
        var matches = 0
        var points = 0
        var goalsScored = 0
        var goalsConceded = 0
        fun goalsDifference() : Int { return goalsScored - goalsConceded }

    }

    class Group( var teams : ArrayList<RegisteredTeam>, var group : Char ) {

    }

    fun byGroup(): Map<Char, List<RegisteredTeam>> {return registeredTeams.groupBy { it.group!!  } }

    fun previousMatches() : ArrayList<Match>? {

        var previousMatches : ArrayList<Match>? = null

        matches?.let {

            orderMatches()

            previousMatches = ArrayList<Match>()

            for (match in matches) {
                if (match.isFinished) {
                    previousMatches!!.add(match)
                }
            }
        }
        return previousMatches
    }

    fun nextMatches() : ArrayList<Match>? {

        var nextMatches : ArrayList<Match>? = null

        matches?.let {

            orderMatches()

            nextMatches = ArrayList<Match>()

            for (match in matches) {
                if (!match.isFinished) {
                    nextMatches!!.add(match)
                }
            }
        }

        return nextMatches
    }

    fun previousMatch() : Match? {

        var previousMatch : Match? = null

        previousMatches()?.let {

            if (!it.isEmpty()) {

                previousMatch = it.last()

                for (match in it) {

                    if (KicklyTools.timeDifference(
                            previousMatch!!.dateTime!!,
                            match.dateTime!!
                        ) > 0
                    )
                        previousMatch = match
                }

            }
        }

        return previousMatch

    }

    fun nextMatch() : Match? {

        var nextMatch : Match? = null
        nextMatches()?.let {
            if (!it.isEmpty()) {

                nextMatch = it.last()



                for (match in it) {

                    if (KicklyTools.timeDifference(nextMatch!!.dateTime!!, match.dateTime!!) < 0)
                        nextMatch = match
                }

            }

        }

        return nextMatch

    }

    // get an array of groups
    fun groupsArray() : ArrayList<Group> {

        var byGroup = byGroup()

        // get the keys (groups)
        var keys = byGroup.keys
        var groups = ArrayList<Group>()

        var teams = ArrayList<RegisteredTeam>()

        // for each key
        for (key in keys) {

            // for each team of the key (group)
            for ( team in byGroup.get(key)!! ) {
                teams.add(team) // add it to the array
            }

            // add the array and key to a group
            groups.add( Group(teams, key) )
        }

        // i'm not sure what this is
        groups.sortBy { it.group }

        return groups

    }

    fun orderMatches() {

        matches?.let {
            if (!it.isEmpty()) {

                var orderedMatches: List<Match> =
                    matches.sortedBy {
                        KicklyTools.timeDifference(
                            LocalDateTime.now(),
                            it.dateTime
                        )
                    }

                var orderedMatchesArrayList: ArrayList<Match> = ArrayList<Match>()

                for (match in orderedMatches) {
                    orderedMatchesArrayList.add(match)
                }

                matches = orderedMatchesArrayList

            }
        }

    }

    fun otherTeams() : ArrayList<Team> {

        // get non registered teams
        var otherTeams = ArrayList<Team>()
        var isRegistered = false
        for (team in Kickly.teamList) {

            isRegistered = false

            for (tournamentTeam in this.registeredTeams) {
                if (team == tournamentTeam.team) { isRegistered = true }
            }

            if (!isRegistered) {
                otherTeams.add(team)
            }
        }

        return otherTeams

    }

    fun getGroups() : ArrayList<Char> {
        var groupsArray = groupsArray()

        var groupsCharArray = ArrayList<Char>()

        for (group in groupsArray) {
            groupsCharArray.add(group.group)
        }

        return groupsCharArray

    }

    fun getGroupsString() : ArrayList<String> {

        var groupsArray = groupsArray()

        var groupsStringArray = ArrayList<String>()

        for (group in groupsArray) {
            groupsStringArray.add(group.group.toString())
        }

        return groupsStringArray

    }



    fun nextGroup() : Char {

        var groups = getGroups()

        var nextGroup : Char? = null

        when (groups.last()) {
            'A' -> nextGroup = 'B'
            'B' -> nextGroup = 'C'
            'C' -> nextGroup = 'D'
            'D' -> nextGroup = 'E'
            'E' -> nextGroup = 'F'
            'F' -> nextGroup = 'G'
            'G' -> nextGroup = 'H'
            'H' -> nextGroup = 'I'
            'I' -> nextGroup = 'J'
            'J' -> nextGroup = 'K'
            'K' -> nextGroup = 'L'
            'L' -> nextGroup = 'M'

            else -> { // Note the block
                throw (Exception("too many groups"))
            }
        }

        return nextGroup

    }

}