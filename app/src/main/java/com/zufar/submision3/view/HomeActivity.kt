package com.zufar.submision3.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zufar.submision3.R
import com.zufar.submision3.R.id.*
import com.zufar.submision3.db.Favourite
import com.zufar.submision3.view.match.LastMatchFragment
import com.zufar.submision3.view.match.NextMatchFragment
import com.zufar.submision3.view.favourites.FavouriteMatchFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener {item ->
            when(item.itemId) {
                past_match -> {
                    loadPastFragment(savedInstanceState)
                }
                next_match -> {
                    loadNextFragment(savedInstanceState)
                }
                favourites_match -> {
                    loadFavouritesFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = past_match
    }

    private fun loadPastFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, LastMatchFragment(), LastMatchFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadNextFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, NextMatchFragment(), NextMatchFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavouritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, FavouriteMatchFragment(), FavouriteMatchFragment::class.java.simpleName)
                .commit()
        }
    }
}
