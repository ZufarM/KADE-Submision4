package com.zufar.submision3.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.zufar.submision3.model.Match
import com.zufar.submision3.R
import org.jetbrains.anko.*

class MatchAdapter(private val matches: List<Match>,
                   private val listener: (Match) -> Unit)
    : RecyclerView.Adapter<ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(EventUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(matches[position], listener)
    }

    override fun getItemCount(): Int = matches.size
}

class EventUI : AnkoComponent<ViewGroup>{
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

class ViewHolder(val  view: View) : RecyclerView.ViewHolder(view) {

    private val dateEvent: TextView = view.find(R.id.date_event)
    private val strHomeTeam: TextView = view.find(R.id.home_team)
    private val intHomeScore: TextView = view.find(R.id.home_score)
    private val strAwayTeam: TextView = view.find(R.id.away_team)
    private val intAwayScore: TextView = view.find(R.id.away_score)

    fun bindItem(events: Match, listener: (Match) -> Unit){
        // Date
        dateEvent.text = events.dateEvent
        // Home
        strHomeTeam.text = events.strHomeTeam
        intHomeScore.text = events.intHomeScore
        // Away
        strAwayTeam.text = events.strAwayTeam
        intAwayScore.text = events.intAwayScore
        Log.e("Match", strAwayTeam.toString() + "vs" +strHomeTeam.toString())
        view.setOnClickListener { listener(events) }
    }
}