package com.zufar.submision3.model

import com.zufar.submision3.model.Teams

class EventsResponse (
    val events: List<Match>
)

class TeamsHomeResponse (
    val teams: List<Teams>
)

class TeamsAwayResponse (
    val teams: List<Teams>
)