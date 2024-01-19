package com.portofolio.CovidTracker.ui.activity.country

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.portofolio.CovidTracker.R
import org.json.JSONArray
import org.json.JSONException

class ListCountryActivity : AppCompatActivity() {
    private lateinit var ascendingCountry: ImageButton
    private lateinit var descendingCountry: ImageButton
    private lateinit var backToTop: ImageButton
    private lateinit var editSearchCountry: SearchView
    private lateinit var countryNameTitle: TextView
    private lateinit var nameContinent: String
    private lateinit var myRecyclerView: RecyclerView
    private lateinit var countriesCovid: ArrayList<CountryCovid>
    lateinit var countryCovidAdapter: CountryCovidAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_country)
        countryNameTitle = findViewById<View>(R.id.tv_title_country) as TextView
        editSearchCountry = findViewById<View>(R.id.search_country) as SearchView
        ascendingCountry = findViewById<View>(R.id.ib_sort_asc_country) as ImageButton
        descendingCountry = findViewById<View>(R.id.ib_sort_desc_country) as ImageButton
        backToTop = findViewById<View>(R.id.ib_to_top) as ImageButton
        myRecyclerView = findViewById(R.id.rv_country)
        myRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        val intent2 = intent
        nameContinent = intent2.getStringExtra("continent_name").toString()
        countryNameTitle.text = intent2.getStringExtra("continent_name")
        getListCountryAsc()
        getSortCountry()
        getSearchCountry()
        getBackToTop()
    }

    private fun getBackToTop() {
        backToTop.setOnClickListener {
            myRecyclerView.smoothScrollToPosition(0)
        }
    }

    private fun getSearchCountry() {
        editSearchCountry.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                countryCovidAdapter.filter(s)
                return false
            }
        })
    }

    private fun getSortCountry() {
        descendingCountry.setOnClickListener {
            Toast.makeText(applicationContext, "Sort Descending", Toast.LENGTH_SHORT).show()
            countriesCovid.clear()
            getListCountryDesc()
        }
        ascendingCountry.setOnClickListener {
            Toast.makeText(applicationContext, "Sort Ascending", Toast.LENGTH_SHORT).show()
            countriesCovid.clear()
            getListCountryAsc()
        }
    }

    private fun showRecyclerView() {
        countryCovidAdapter = CountryCovidAdapter(countriesCovid, applicationContext)
        myRecyclerView.adapter = countryCovidAdapter
    }

    private fun getListCountryAsc() {
        val url = "https://disease.sh/v3/covid-19/countries"
        val queue = Volley.newRequestQueue(applicationContext)
        countriesCovid = ArrayList()
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val data = jsonArray.getJSONObject(i)
                        val countryInfo = data.getJSONObject("countryInfo")
                        if (data.getString("continent") == nameContinent
                                .trim { it <= ' ' }
                        ) {
                            countriesCovid.add(
                                CountryCovid(
                                    countryInfo.getString("flag"),
                                    data.getString("country"),
                                    data.getString("cases"),
                                    data.getString("deaths"),
                                    data.getString("recovered")
                                )
                            )
                        }
                    }
                    showRecyclerView()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { }
        queue.cache.clear()
        queue.add(stringRequest)
    }

    private fun getListCountryDesc() {
        val url = "https://disease.sh/v3/covid-19/countries"
        val queue = Volley.newRequestQueue(applicationContext)
        countriesCovid = ArrayList()
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val data = jsonArray.getJSONObject(i)
                        val countryInfo = data.getJSONObject("countryInfo")
                        if (data.getString("continent") == nameContinent
                                .trim { it <= ' ' }
                        ) {
                            countriesCovid.add(
                                CountryCovid(
                                    countryInfo.getString("flag"),
                                    data.getString("country"),
                                    data.getString("cases"),
                                    data.getString("deaths"),
                                    data.getString("recovered")
                                )
                            )
                        }
                    }
                    countriesCovid.reverse()
                    showRecyclerView()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { }
        queue.cache.clear()
        queue.add(stringRequest)
    }
}