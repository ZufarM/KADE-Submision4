package com.zufar.submision3.db

data class Favourite(val id: Long?,
                val eventId: String?,
                val eventName: String?,
                val eventDate: String?,
                val homeId: String?,
                val homeTeam: String?,
                val homeScore: String?,
                val awayId: String?,
                val awayTeam: String?,
                val awayScore: String?) {

    companion object {
        const val TABLE_FAVOURITE: String = "TABLE_FAVOURITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_NAME: String = "EVENT_NAME"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val HOME_ID: String = "HOME_ID"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_ID: String = "AWAY_ID"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val AWAY_SCORE: String = "AWAY_SCORE"
    }
}