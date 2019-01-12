package com.zufar.submision3.model

import com.google.gson.annotations.SerializedName

class Teams (
    @SerializedName("idTeam")
    var idTeam: String? = null,

    @SerializedName("strTeam")
    var strTeam: String? = null,

    @SerializedName("strTeamBadge")
    var strTeamBadge: String? = null,

    @SerializedName("strDescriptionEN")
    var strDescriptionEN: String? = null
)