package com.portofolio.CovidTracker.ui.home

class ContinentCovid(
    var mContinent: String,
    var mCase: String,
    var mDeaths: String,
    var mRecovered: String
) {
    fun getmContinent(): String {
        return mContinent
    }

    fun setmContinent(mContinent: String) {
        this.mContinent = mContinent
    }

    fun getmDeaths(): String {
        return mDeaths
    }

    fun setmDeaths(mDeaths: String) {
        this.mDeaths = mDeaths
    }

    fun getmCase(): String {
        return mCase
    }

    fun setmCase(mCase: String) {
        this.mCase = mCase
    }

    fun getmRecovered(): String {
        return mRecovered
    }

    fun setmRecovered(mRecovered: String) {
        this.mRecovered = mRecovered
    }
}