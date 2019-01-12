package com.zufar.submision3.api

import android.net.Uri
import com.zufar.submision3.BuildConfig

object TheSportDBApi {

    fun getLastMatch(): String{
        // https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath("eventspastleague.php")
//            .appendQueryParameter("id", "4328")
//            .build()
//            .toString()
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=4328"
    }

    fun getNextMatch(): String{
        // https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath("eventsnextleague.php")
//            .appendQueryParameter("id", "4328")
//            .build()
//            .toString()
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php?id=4328"
    }

    fun getTeamDetail(id: String?): String {
        //https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=...
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("lookupteam.php")
            .appendQueryParameter("id", id)
            .build()
            .toString()
    }

    fun getEventDetail(id: String?): String {
        // https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id=...
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("lookupevent.php")
            .appendQueryParameter("id", id)
            .build()
            .toString()
    }
}