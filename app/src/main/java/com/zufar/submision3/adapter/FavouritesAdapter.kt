package com.zufar.submision3.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.widget.TextView
import com.zufar.submision3.R
import com.zufar.submision3.db.Favourite
import org.jetbrains.anko.*

class FavouritesAdapter(private val favourite: List<Favourite>, private val listener: (Favourite) -> Unit)
    : RecyclerView.Adapter<FavouritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        return FavouritesViewHolder(FavUI().createView(AnkoContext.create(parent.context,parent)))
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        holder.bindItem(favourite[position], listener)
    }

    override fun getItemCount(): Int = favourite.size
}

class FavUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                padding = dip(16)
                gravity = Gravity.CENTER

                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    padding = dip(8)
                    gravity = Gravity.CENTER_VERTICAL

                    textView {
                        id = R.id.date_event
                        gravity = Gravity.CENTER
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                    }.lparams(width = matchParent, height = wrapContent)


                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        lparams(width = matchParent, height = wrapContent)
                        gravity = Gravity.CENTER
                        // Home
                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_VERTICAL

                            textView {
                                gravity = Gravity.RIGHT
                                id = R.id.home_team
                                textSize = 19f
                                text = " Home "
                            }
                        }.lparams(width = 300, height = wrapContent)
                        // Score Home
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_VERTICAL
                            textView {
                                id = R.id.home_score
                                gravity = Gravity.CENTER
                                textSize = 20f
                                leftPadding = dip(16)
                                text = " 0 "
                                setTypeface(null, Typeface.BOLD)
                            }

                            textView {
                                gravity = Gravity.CENTER
                                text = " vs "
                                setTypeface(null, Typeface.ITALIC)
                            }
                            // Score Away
                            textView {
                                id = R.id.away_score
                                gravity = Gravity.CENTER
                                textSize = 20f
                                rightPadding = dip(16)
                                text = " 0 "
                                setTypeface(null, Typeface.BOLD)
                            }
                        }
                        // Away
                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            textView {
                                id = R.id.away_team
                                gravity = Gravity.LEFT
                                text = " Away "
                                textSize = 19f
                            }
                        }.lparams(width = 300, height = wrapContent)
                    }
                }
            }
        }
    }
}

class FavouritesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val dateEvent: TextView = view.find(R.id.date_event)
    private val strHomeTeam: TextView = view.find(R.id.home_team)
    private val intHomeScore: TextView = view.find(R.id.home_score)
    private val strAwayTeam: TextView = view.find(R.id.away_team)
    private val intAwayScore: TextView = view.find(R.id.away_score)

    fun bindItem(favourite: Favourite, listener: (Favourite) -> Unit) {
        // Date
        dateEvent.text = favourite.eventDate
        // Home
        strHomeTeam.text = favourite.homeTeam
        intHomeScore.text = favourite.homeScore
        // Away
        strAwayTeam.text = favourite.awayTeam
        intAwayScore.text = favourite.awayScore

        view.setOnClickListener { listener(favourite) }
    }
}
