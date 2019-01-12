package com.zufar.submision3.view.detail

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.zufar.submision3.R
import com.zufar.submision3.R.drawable.ic_add_to_favorites
import com.zufar.submision3.R.drawable.ic_added_to_favorites
import com.zufar.submision3.R.id.add_to_favorite
import com.zufar.submision3.R.menu.detail_menu
import com.zufar.submision3.api.ApiRepository
import com.zufar.submision3.db.Favourite
import com.zufar.submision3.db.database
import com.zufar.submision3.model.Match
import com.zufar.submision3.model.Teams
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class DetailActivity : AppCompatActivity(), DetailView {

    private lateinit var presenter: DetailPresenter
    private var events: MutableList<Match> = mutableListOf()
    private var home: MutableList<Teams> = mutableListOf()
    private var away: MutableList<Teams> = mutableListOf()

    // Event
    private var nameEvent: String = ""
    lateinit var dateEvent: TextView

    // home team
    lateinit var imageHome: ImageView
    lateinit var teamHome: TextView
    lateinit var scoreHome: TextView
    lateinit var goalsHome: TextView
    // linesup
    lateinit var goalskeeperHome: TextView
    lateinit var defenseHome: TextView
    lateinit var midfieldHome: TextView
    lateinit var forwardHome: TextView
    lateinit var substitutesHome: TextView

    // away team
    lateinit var imageAway: ImageView
    lateinit var teamAway: TextView
    lateinit var scoreAway: TextView
    lateinit var goalsAway: TextView
    // linesup
    lateinit var goalskeeperAway: TextView
    lateinit var defenseAway: TextView
    lateinit var midfieldAway: TextView
    lateinit var forwardAway: TextView
    lateinit var substitutesAway: TextView

    private var menuItem: Menu? = null
    private var isFavourite: Boolean = false
    private lateinit var id: String
    private lateinit var event: Match

    private lateinit var swipeRefresh: SwipeRefreshLayout

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Anko
        swipeRefresh = swipeRefreshLayout {
            scrollView {
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    padding = dip(8)
                    gravity = Gravity.CENTER_VERTICAL

                    linearLayout {
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.HORIZONTAL

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_VERTICAL
                            // Home

                            imageHome = imageView {
                                padding = dip(16)
                            }.lparams(width = 250, height = 250)

                            teamHome = textView {
                                gravity = Gravity.CENTER
                                textSize = 22f
                                text = " Home "
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                setTypeface(null, Typeface.BOLD)
                            }
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_VERTICAL
                                scoreHome = textView {
                                    textSize = 30f
                                    text = " 0 "
                                    textAlignment = right
                                    setTypeface(null, Typeface.BOLD)
                                }
                                textView {
                                    text = " vs "
                                    textSize = 19f
                                    setTypeface(null, Typeface.ITALIC)
                                }
                                scoreAway = textView {
                                    textSize = 30f
                                    text = " 0 "
                                    setTypeface(null, Typeface.BOLD)
                                }
                            }
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_VERTICAL
                            // Away
                            imageAway = imageView {
                                padding = dip(16)
                            }.lparams(width = 250, height = 250)
                            teamAway = textView {
                                gravity = Gravity.CENTER
                                text = " Away "
                                textSize = 22f
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                setTypeface(null, Typeface.BOLD)
                            }

                        }
                    }

                    dateEvent = textView {
                        gravity = Gravity.CENTER
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                    }.lparams(width = matchParent, height = wrapContent)

                    // goals
                    textView {
                        topPadding = dip(4)
                        gravity = Gravity.CENTER
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = " goals "
                        setTypeface(null, Typeface.BOLD)
                    }.lparams(width = matchParent, height = wrapContent)
                    linearLayout {
                        gravity = Gravity.CENTER_HORIZONTAL
                        orientation = LinearLayout.HORIZONTAL

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            // Home
                            lparams(width = 250, height = wrapContent)

                            goalsHome = textView {
                                textSize = 14f
                                gravity = Gravity.CENTER_HORIZONTAL
                            }
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            // Away
                            lparams(width = 250, height = wrapContent)

                            goalsAway = textView {
                                textSize = 14f
                                gravity = Gravity.CENTER_HORIZONTAL
                            }
                        }
                    }

                    // goals keeper
                    textView {
                        topPadding = dip(4)
                        gravity = Gravity.CENTER
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = " goals keeper "
                        setTypeface(null, Typeface.BOLD)
                    }.lparams(width = matchParent, height = wrapContent)
                    linearLayout {
                        gravity = Gravity.CENTER_HORIZONTAL
                        orientation = LinearLayout.HORIZONTAL

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            // Home
                            lparams(width = 250, height = wrapContent)

                            goalskeeperHome = textView {
                                textSize = 14f
                                gravity = Gravity.CENTER_HORIZONTAL
                            }
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            // Away
                            lparams(width = 250, height = wrapContent)

                            goalskeeperAway = textView {
                                textSize = 14f
                                gravity = Gravity.CENTER_HORIZONTAL
                            }
                        }
                    }
                    // defense
                    textView {
                        topPadding = dip(4)
                        gravity = Gravity.CENTER
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = " defense "
                        setTypeface(null, Typeface.BOLD)
                    }.lparams(width = matchParent, height = wrapContent)
                    linearLayout {
                        gravity = Gravity.CENTER_HORIZONTAL
                        orientation = LinearLayout.HORIZONTAL

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            // Home
                            lparams(width = 250, height = wrapContent)

                            defenseHome = textView {
                                textSize = 14f
                                gravity = Gravity.CENTER_HORIZONTAL
                            }
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            // Away
                            lparams(width = 250, height = wrapContent)

                            defenseAway = textView {
                                textSize = 14f
                                gravity = Gravity.CENTER_HORIZONTAL
                            }
                        }
                    }
                    // midfield
                    textView {
                        topPadding = dip(4)
                        gravity = Gravity.CENTER
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = " midfield "
                        setTypeface(null, Typeface.BOLD)
                    }.lparams(width = matchParent, height = wrapContent)
                    linearLayout {
                        gravity = Gravity.CENTER_HORIZONTAL
                        orientation = LinearLayout.HORIZONTAL

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            // Home
                            lparams(width = 250, height = wrapContent)

                            midfieldHome = textView {
                                textSize = 14f
                                gravity = Gravity.CENTER_HORIZONTAL
                            }
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            // Away
                            lparams(width = 250, height = wrapContent)

                            midfieldAway = textView {
                                textSize = 14f
                                gravity = Gravity.CENTER_HORIZONTAL
                            }
                        }
                    }
                    // Forward
                    textView {
                        topPadding = dip(4)
                        gravity = Gravity.CENTER
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = " forward "
                        setTypeface(null, Typeface.BOLD)
                    }.lparams(width = matchParent, height = wrapContent)
                    linearLayout {
                        gravity = Gravity.CENTER_HORIZONTAL
                        orientation = LinearLayout.HORIZONTAL

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            // Home
                            lparams(width = 250, height = wrapContent)

                            forwardHome = textView {
                                textSize = 14f
                                gravity = Gravity.CENTER_HORIZONTAL
                            }
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            // Away
                            lparams(width = 250, height = wrapContent)

                            forwardAway = textView {
                                textSize = 14f
                                gravity = Gravity.CENTER_HORIZONTAL
                            }
                        }
                    }
                    // Substitutes
                    textView {
                        topPadding = dip(4)
                        gravity = Gravity.CENTER
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = " substitutes "
                        setTypeface(null, Typeface.BOLD)
                    }.lparams(width = matchParent, height = wrapContent)
                    linearLayout {
                        gravity = Gravity.CENTER_HORIZONTAL
                        orientation = LinearLayout.HORIZONTAL

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            // Home
                            lparams(width = 250, height = wrapContent)

                            substitutesHome = textView {
                                textSize = 14f
                                gravity = Gravity.CENTER_HORIZONTAL
                            }
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            // Away
                            lparams(width = 250, height = wrapContent)

                            substitutesAway = textView {
                                textSize = 14f
                                gravity = Gravity.CENTER_HORIZONTAL
                            }
                        }
                    }

                }

            }
        }

        val intent = intent
        id = intent.getStringExtra("id")
        nameEvent = intent.getStringExtra("name")

        // Home
        val homeId: String = intent.getStringExtra("homeId")

        // Away
        val awayId: String = intent.getStringExtra("awayId")

        // Request to API
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this,request,gson)
        presenter.getTeamList(id,homeId,awayId)

        favouriteState()

        setTitle(nameEvent)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun favouriteState(){
        database.use{
            val result = select(Favourite.TABLE_FAVOURITE)
                .whereArgs("(EVENT_ID = {id})",
                    "id" to id)
            val favourites = result.parseList(classParser<Favourite>())
            if (!favourites.isEmpty()) isFavourite = true
        }
    }

    private fun setFavorite() {
        if (isFavourite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavourite) removeFromFavourite() else addToFavourite()
                isFavourite = !isFavourite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showEventList(eventsMatch: List<Match>, homeTeam: List<Teams>, awayTeam: List<Teams>) {
        // Image Home
        home.clear()
        home.addAll(homeTeam)
        Picasso.get()
            .load(homeTeam[0].strTeamBadge).into(imageHome)
        // Image Away
        away.clear()
        away.addAll(awayTeam)
        Picasso.get()
            .load(awayTeam[0].strTeamBadge).into(imageAway)

        // Events
        events.clear()
        events.addAll(eventsMatch)

        dateEvent.text = eventsMatch[0].dateEvent

        // Home
        teamHome.text = eventsMatch[0].strHomeTeam
        scoreHome.text = eventsMatch[0].intHomeScore
        goalsHome.text = eventsMatch[0].strHomeGoalDetails
        // Linesup
        goalskeeperHome.text = eventsMatch[0].strHomeLineupGoalkeeper
        defenseHome.text = eventsMatch[0].strHomeLineupDefense
        midfieldHome.text = eventsMatch[0].strHomeLineupMidfield
        forwardHome.text = eventsMatch[0].strHomeLineupForward
        substitutesHome.text = eventsMatch[0].strHomeLineupSubstitutes

        // Away
        teamAway.text = eventsMatch[0].strAwayTeam
        scoreAway.text = eventsMatch[0].intAwayScore
        goalsAway.text = eventsMatch[0].strAwayGoalDetails
        // Linesup
        goalskeeperAway.text = eventsMatch[0].strAwayLineupGoalkeeper
        defenseAway.text = eventsMatch[0].strAwayLineupDefense
        midfieldAway.text = eventsMatch[0].strAwayLineupMidfield
        forwardAway.text = eventsMatch[0].strAwayLineupForward
        substitutesAway.text = eventsMatch[0].strAwayLineupSubstitutes

        event = Match(
            eventsMatch[0].idEvent,
            eventsMatch[0].dateEvent,
            eventsMatch[0].strEvent,
            // home
            eventsMatch[0].idHomeTeam,
            eventsMatch[0].strHomeTeam,
            eventsMatch[0].intHomeScore,
            eventsMatch[0].strHomeGoalDetails,

            eventsMatch[0].strHomeLineupGoalkeeper,
            eventsMatch[0].strHomeLineupDefense,
            eventsMatch[0].strHomeLineupMidfield,
            eventsMatch[0].strHomeLineupForward,
            eventsMatch[0].strHomeLineupSubstitutes,
            // away
            eventsMatch[0].idAwayTeam,
            eventsMatch[0].strAwayTeam,
            eventsMatch[0].intAwayScore,
            eventsMatch[0].strAwayGoalDetails,

            eventsMatch[0].strAwayLineupGoalkeeper,
            eventsMatch[0].strAwayLineupDefense,
            eventsMatch[0].strAwayLineupMidfield,
            eventsMatch[0].strAwayLineupForward,
            eventsMatch[0].strAwayLineupSubstitutes
        )
    }

    private fun addToFavourite(){
        try {
            database.use {
                insert(Favourite.TABLE_FAVOURITE,
                    Favourite.EVENT_ID to event.idEvent,
                    Favourite.EVENT_NAME to event.strEvent,
                    Favourite.EVENT_DATE to event.dateEvent,
                    Favourite.HOME_ID to event.idHomeTeam,
                    Favourite.HOME_TEAM to event.strHomeTeam,
                    Favourite.HOME_SCORE to event.intHomeScore,
                    Favourite.AWAY_ID to event.idAwayTeam,
                    Favourite.AWAY_TEAM to event.strAwayTeam,
                    Favourite.AWAY_SCORE to event.intAwayScore
                )
            }
            swipeRefresh.snackbar("Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavourite(){
        try {
            database.use {
                delete(Favourite.TABLE_FAVOURITE, "(EVENT_ID = {id})",
                    "id" to id)
            }
            swipeRefresh.snackbar( "Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }
}
