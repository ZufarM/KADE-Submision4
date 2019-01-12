package com.zufar.submision3.view.match


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.gson.Gson
import com.zufar.submision3.R

import com.zufar.submision3.adapter.MatchAdapter
import com.zufar.submision3.api.ApiRepository
import com.zufar.submision3.model.Match
import com.zufar.submision3.util.invisible
import com.zufar.submision3.util.visible
import com.zufar.submision3.view.detail.DetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class NextMatchFragment : Fragment(), MatchView {

    private var events: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MatchPresenter
    private lateinit var adapter: MatchAdapter
    private lateinit var listMatch: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return UI {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)

                swipeRefresh = swipeRefreshLayout {

                    relativeLayout{
                        lparams (width = matchParent, height = wrapContent)

                        listMatch = recyclerView {
                            id = R.id.rv_next_match
                            lparams (width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                        }

                        progressBar = progressBar {
                        }.lparams{
                            centerHorizontally()
                        }
                    }
                }
            }

            adapter = MatchAdapter(events){
                startActivity<DetailActivity>(
                    // Event
                    "id" to "${it.idEvent}",
                    "name" to "${it.strEvent}",
                    // home Team
                    "homeId" to "${it.idHomeTeam}",
                    // away Team
                    "awayId" to "${it.idAwayTeam}"
                )
            }
            listMatch.adapter = adapter

            val request = ApiRepository()
            val gson = Gson()
            presenter = MatchPresenter(this@NextMatchFragment, request, gson)
            presenter.getNextMatchEvent()

            swipeRefresh.onRefresh {
                presenter.getNextMatchEvent()
            }

        }.view
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Match>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
