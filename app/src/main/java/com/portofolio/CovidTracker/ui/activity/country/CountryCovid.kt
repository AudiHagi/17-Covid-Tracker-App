package com.portofolio.CovidTracker.ui.activity.country

class CountryCovid(
    var mFlag: String,
    var mCountry: String,
    var mCaseC: String,
    var mDeathC: String,
    var mRecoverC: String
) {
    fun getmCountry(): String {
        return mCountry
    }

    fun setmCountry(mCountry: String) {
        this.mCountry = mCountry
    }

    fun getmDeathC(): String {
        return mDeathC
    }

    fun setmDeathC(mDeathC: String) {
        this.mDeathC = mDeathC
    }

    fun getmCaseC(): String {
        return mCaseC
    }

    fun setmCaseC(mCaseC: String) {
        this.mCaseC = mCaseC
    }

    fun getmRecoverC(): String {
        return mRecoverC
    }

    fun setmRecoverC(mRecoverC: String) {
        this.mRecoverC = mRecoverC
    }

    fun getmFlag(): String {
        return mFlag
    }

    fun setmFlag(mFlag: String) {
        this.mFlag = mFlag
    }

}