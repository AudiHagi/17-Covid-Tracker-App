package com.portofolio.CovidTracker.ui.activity.country

class CountryCovid(
    private var mFlag: String,
    private var mCountry: String,
    private var mCaseC: String,
    private var mDeathC: String,
    private var mRecoverC: String
) {
    fun getCountry(): String {
        return mCountry
    }

    fun getDeathC(): String {
        return mDeathC
    }

    fun getCaseC(): String {
        return mCaseC
    }

    fun getRecoverC(): String {
        return mRecoverC
    }

    fun getFlag(): String {
        return mFlag
    }

}