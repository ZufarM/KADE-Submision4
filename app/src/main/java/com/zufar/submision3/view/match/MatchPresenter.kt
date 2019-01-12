package com.zufar.submision3.view.match

import android.util.Log
import com.google.gson.Gson
import com.zufar.submision3.api.ApiRepository
import com.zufar.submision3.api.TheSportDBApi
import com.zufar.submision3.model.MatchResponse
import com.zufar.submision3.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchPresenter(private val view: MatchView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLastMatchEvent(){
        view.showLoading()
        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getLastMatch()),
                    MatchResponse::class.java
                )
            }
            view.showTeamList(data.await().events)
            view.hideLoading()
        }
//        doAsync {context
//            val data = gson.fromJson(apiRepository
//                .doRequest(TheSportDBApi.getLastMatch()),
//                MatchResponse::class.java
//            )
//
//            uiThread {
//                view.hideLoading()
//                view.showTeamList(data.events)
//                Log.e("Match Last", data.events.toString())
//            }
//        }
    }

    fun getNextMatchEvent(){
        view.showLoading()
        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getNextMatch()),
                    MatchResponse::class.java
                )
            }
            view.showTeamList(data.await().events)
            view.hideLoading()
        }
//        doAsync {
//            val data = gson.fromJson(apiRepository
//                .doRequest(TheSportDBApi.getNextMatch()),
//                MatchResponse::class.java
//            )
//
//            uiThread {
//                view.hideLoading()
//                view.showTeamList(data.events)
//                Log.e("Match Next", data.events.toString())
//            }
//        }
    }
}