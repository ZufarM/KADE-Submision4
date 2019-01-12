package com.zufar.submision3.view.match

import com.google.gson.Gson
import com.zufar.submision3.TestContextProvider
import com.zufar.submision3.api.ApiRepository
import com.zufar.submision3.api.TheSportDBApi
import com.zufar.submision3.model.Match
import com.zufar.submision3.model.MatchResponse
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MatchPresenterTest {
    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var presenter: MatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getLastMatchEvent() {
        val match: MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)

        `when`(gson.fromJson(apiRepository
            .doRequest(TheSportDBApi.getLastMatch()),
            MatchResponse::class.java
        )).thenReturn(response)

        presenter.getLastMatchEvent()

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeamList(match)
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun getNextMatchEvent() {
        val match: MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)

        `when`(gson.fromJson(apiRepository
            .doRequest(TheSportDBApi.getNextMatch()),
            MatchResponse::class.java
        )).thenReturn(response)

        presenter.getNextMatchEvent()

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeamList(match)
        Mockito.verify(view).hideLoading()
    }
}