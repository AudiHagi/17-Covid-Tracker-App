package com.portofolio.CovidTracker.ui.continentdetail

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.portofolio.CovidTracker.R
import com.portofolio.CovidTracker.ui.splash.SplashScreenActivity
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ContinentCovidDetailActivity : AppCompatActivity() {
    lateinit var ContinentNameDetail: TextView
    lateinit var TotalConfirmedDetail: TextView
    lateinit var TotalDeathDetail: TextView
    lateinit var TotalRecoveredDetail: TextView
    lateinit var LastUpdateDetail: TextView
    lateinit var ButtonCountry: Button
    lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continent_covid_detail)
        ButtonCountry = findViewById<View>(R.id.btncountry) as Button
        ContinentNameDetail = findViewById<View>(R.id.continentNameDetail) as TextView
        TotalConfirmedDetail = findViewById<View>(R.id.totalConfirmedDetail) as TextView
        TotalDeathDetail = findViewById<View>(R.id.totalDeathDetail) as TextView
        TotalRecoveredDetail = findViewById<View>(R.id.totalRecoveredDetail) as TextView
        pieChart = findViewById<View>(R.id.piechart) as PieChart
        LastUpdateDetail = findViewById<View>(R.id.datelastUpdated) as TextView
        // get passed parameter
        val intent = intent
        ContinentNameDetail.text = intent.getStringExtra("continent_name")
        getDetailCovidContinent()
        ButtonCountry.setOnClickListener {
            val intentToList = Intent(applicationContext, SplashScreenActivity::class.java)
            intentToList.putExtra("continent_name", ContinentNameDetail.text)
            startActivity(intentToList)
        }
    }

    private fun getLastUpdated(milisecond: Long): String? {
        val formatDate = SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milisecond
        return formatDate.format(calendar.time)
    }

    private fun getDetailCovidContinent() {
        val URL =
            "https://disease.sh/v3/covid-19/continents/" + ContinentNameDetail.text.toString()
                .lowercase(
                    Locale.getDefault()
                ).trim { it <= ' ' }
        val queue = Volley.newRequestQueue(applicationContext)
        val stringRequest = StringRequest(
            Request.Method.GET, URL,
            { response ->
                try {
                    val jsonObject = JSONObject(response)
                    TotalConfirmedDetail.text = jsonObject.getString("cases")
                    TotalDeathDetail.text = jsonObject.getString("deaths")
                    TotalRecoveredDetail.text = jsonObject.getString("recovered")
                    LastUpdateDetail.text = getLastUpdated(jsonObject.getLong("updated"))
                    pieChart.addPieSlice(
                        PieModel(
                            "CASES", TotalConfirmedDetail.text.toString().toInt().toFloat(),
                            Color.parseColor("#ffe200")
                        )
                    )
                    pieChart.addPieSlice(
                        PieModel(
                            "DEATHS", TotalDeathDetail.text.toString().toInt().toFloat(),
                            Color.parseColor("#EF5350")
                        )
                    )
                    pieChart.addPieSlice(
                        PieModel(
                            "RECOVERED",
                            TotalRecoveredDetail.text.toString().toInt().toFloat(),
                            Color.parseColor("#66BB6A")
                        )
                    )
                    // To animate the pie chart
                    pieChart.startAnimation()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { error -> Log.d("error response", error.toString()) }
        queue.cache.clear()
        queue.add(stringRequest)
    }
}