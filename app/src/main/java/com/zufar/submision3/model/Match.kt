package com.zufar.submision3.model

import com.google.gson.annotations.SerializedName

class Match (
    @SerializedName("idEvent")
    var idEvent: String? = null,

    @SerializedName("dateEvent")
    var dateEvent: String? = null,

    @SerializedName("strEvent")
    var strEvent: String? = null,


    // Home Team
    @SerializedName("idHomeTeam")
    var idHomeTeam: String? = null,

    @SerializedName("strHomeTeam")
    var strHomeTeam: String? = null,

    @SerializedName("intHomeScore")
    var intHomeScore: String? = null,

    @SerializedName("strHomeGoalDetails")
    var strHomeGoalDetails: String? = null,
    // Home Lineup
    @SerializedName("strHomeLineupGoalkeeper")
    var strHomeLineupGoalkeeper: String? = null,

    @SerializedName("strHomeLineupDefense")
    var strHomeLineupDefense: String? = null,

    @SerializedName("strHomeLineupMidfield")
    var strHomeLineupMidfield: String? = null,

    @SerializedName("strHomeLineupForward")
    var strHomeLineupForward: String? = null,

    @SerializedName("strHomeLineupSubstitutes")
    var strHomeLineupSubstitutes: String? = null,


    // Away Team
    @SerializedName("idAwayTeam")
    var idAwayTeam: String? = null,

    @SerializedName("strAwayTeam")
    var strAwayTeam: String? = null,

    @SerializedName("intAwayScore")
    var intAwayScore: String? = null,

    @SerializedName("strAwayGoalDetails")
    var strAwayGoalDetails: String? = null,
    // Away Lineup
    @SerializedName("strAwayLineupGoalkeeper")
    var strAwayLineupGoalkeeper: String? = null,

    @SerializedName("strAwayLineupDefense")
    var strAwayLineupDefense: String? = null,

    @SerializedName("strAwayLineupMidfield")
    var strAwayLineupMidfield: String? = null,

    @SerializedName("strAwayLineupForward")
    var strAwayLineupForward: String? = null,

    @SerializedName("strAwayLineupSubstitutes")
    var strAwayLineupSubstitutes: String? = null
    )
