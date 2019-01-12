package com.zufar.submision3.view.match

import com.zufar.submision3.model.Match


interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Match>)
}