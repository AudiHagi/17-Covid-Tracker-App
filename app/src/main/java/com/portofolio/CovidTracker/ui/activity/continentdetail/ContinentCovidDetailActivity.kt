package com.portofolio.CovidTracker.ui.activity.continentdetail

import android.annotation.SuppressLint
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
import com.portofolio.CovidTracker.ui.activity.country.ListCountryActivity
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ContinentCovidDetailActivity : AppCompatActivity() {
    private lateinit var continentNameDetail: TextView
    private lateinit var totalConfirmedDetail: TextView
    private lateinit var totalDeathDetail: TextView
    private lateinit var totalRecoveredDetail: TextView
    private lateinit var lastUpdateDetail: TextView
    private lateinit var buttonCountry: Button
    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continent_covid_detail)
        buttonCountry = findViewById<View>(R.id.btnCountryList) as Button
        continentNameDetail = findViewById<View>(R.id.tvContinentNameDetail) as TextView
        totalConfirmedDetail = findViewById<View>(R.id.tvTotalConfirmedDetail) as TextView
        totalDeathDetail = findViewById<View>(R.id.tvTotalDeathDetail) as TextView
        totalRecoveredDetail = findViewById<View>(R.id.tvTotalRecoveredDetail) as TextView
        pieChart = findViewById<View>(R.id.pieChart) as PieChart
        lastUpdateDetail = findViewById<View>(R.id.tvDateLastUpdated) as TextView
        // get passed parameter
        val intent = intent
        continentNameDetail.text = intent.getStringExtra("continent_name")
        getDetailCovidContinent()
        buttonCountry.setOnClickListener {
            val intentToList = Intent(applicationContext, ListCountryActivity::class.java)
            intentToList.putExtra("continent_name", continentNameDetail.text)
            startActivity(intentToList)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getLastUpdated(millisecond: Long): String? {
        val formatDate = SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millisecond
        return formatDate.format(calendar.time)
    }

    private fun getDetailCovidContinent() {
        val url =
            "https://disease.sh/v3/covid-19/continents/" + continentNameDetail.text.toString()
                .lowercase(
                    Locale.getDefault()
                ).trim { it <= ' ' }
        val queue = Volley.newRequestQueue(applicationContext)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val jsonObject = JSONObject(response)
                    totalConfirmedDetail.text = jsonObject.getString("cases")
                    totalDeathDetail.text = jsonObject.getString("deaths")
                    totalRecoveredDetail.text = jsonObject.getString("recovered")
                    lastUpdateDetail.text = getLastUpdated(jsonObject.getLong("updated"))
                    pieChart.addPieSlice(
                        PieModel(
                            "CASES", totalConfirmedDetail.text.toString().toInt().toFloat(),
                            Color.parseColor("#ffe200")
                        )
                    )
                    pieChart.addPieSlice(
                        PieModel(
                            "DEATHS", totalDeathDetail.text.toString().toInt().toFloat(),
                            Color.parseColor("#EF5350")
                        )
                    )
                    pieChart.addPieSlice(
                        PieModel(
                            "RECOVERED",
                            totalRecoveredDetail.text.toString().toInt().toFloat(),
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