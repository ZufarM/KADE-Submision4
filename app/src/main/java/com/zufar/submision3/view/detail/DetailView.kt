package com.zufar.submision3.view.detail

import com.zufar.submision3.model.Match
import com.zufar.submision3.model.Teams

interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(
        eventMatch: List<Match>,
        homeTeam: List<Teams>,
        awayTeam: List<Teams>
    )
}