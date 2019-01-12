package com.zufar.submision3.view.detail

import com.google.gson.Gson
import com.zufar.submision3.api.ApiRepository
import com.zufar.submision3.api.TheSportDBApi
import com.zufar.submision3.model.EventsResponse
import com.zufar.submision3.model.TeamsAwayResponse
import com.zufar.submision3.model.TeamsHomeResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(private val view: DetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson
) {

    fun getTeamList(idEvent: String?,idHome: String?, idAway: String?){
        view.showLoading()
        doAsync {
            val matchList = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getEventDetail(idEvent)),
                EventsResponse::class.java
            )

            val homeList = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetail(idHome)),
                TeamsHomeResponse::class.java
            )

            val awayList = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetail(idAway)),
                TeamsAwayResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showEventList(matchList.events,homeList.teams,awayList.teams)
            }
        }
    }

}