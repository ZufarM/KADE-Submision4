package com.zufar.submision3.view.favourites


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.zufar.submision3.R
import com.zufar.submision3.adapter.FavouritesAdapter
import com.zufar.submision3.db.Favourite
import com.zufar.submision3.db.database
import com.zufar.submision3.view.detail.DetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.dip
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
class FavouriteMatchFragment : Fragment(), AnkoComponent<Context>  {

    private var favourites: MutableList<Favourite> = mutableListOf()
    private lateinit var adapter: FavouritesAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavouritesAdapter(favourites){
            context?.startActivity<DetailActivity>(
                // Event
                "id" to "${it.eventId}",
                "name" to "${it.eventName}",
                // home Team
                "homeId" to "${it.homeId}",
                // away Team
                "awayId" to "${it.awayId}")
        }

        listEvent.adapter = adapter
        showFavorite()
        swipeRefresh.onRefresh {
            favourites.clear()
            showFavorite()
        }
    }

    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favourite.TABLE_FAVOURITE)
            val favourite = result.parseList(classParser<Favourite>())
            favourites.addAll(favourite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        id = R.id.rv_fav_match
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }
            }
        }
    }
}
