package com.zufar.submision3.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class FavouriteHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavouriteEvent.db",null,1){
    companion object {
        private var instance: FavouriteHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): FavouriteHelper {
            if (instance == null) {
                instance = FavouriteHelper(ctx.applicationContext)
            }
            return instance as FavouriteHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Favourite.TABLE_FAVOURITE, true,
            Favourite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favourite.EVENT_ID to TEXT + UNIQUE,
            Favourite.EVENT_NAME to TEXT,
            Favourite.EVENT_DATE to TEXT,
            Favourite.HOME_ID to TEXT,
            Favourite.HOME_TEAM to TEXT,
            Favourite.HOME_SCORE to TEXT,
            Favourite.AWAY_ID to TEXT,
            Favourite.AWAY_TEAM to TEXT,
            Favourite.AWAY_SCORE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Favourite.TABLE_FAVOURITE, true)
    }
}

val Context.database: FavouriteHelper
    get() = FavouriteHelper.getInstance(applicationContext)