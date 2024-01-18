package com.portofolio.CovidTracker.ui.fragment.global

class ContinentCovid(
    private var mContinent: String,
    private var mCase: String,
    private var mDeaths: String,
    private var mRecovered: String
) {
    fun getContinent(): String {
        return mContinent
    }

    fun getDeaths(): String {
        return mDeaths
    }

    fun getCase(): String {
        return mCase
    }

    fun getRecovered(): String {
        return mRecovered
    }

}