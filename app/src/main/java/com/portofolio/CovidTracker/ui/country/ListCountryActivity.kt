package com.portofolio.CovidTracker.ui.country

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
import java.util.*

class ListCountryActivity : AppCompatActivity() {
    lateinit var ascendingcountry: ImageButton
    lateinit var descendingcountry: ImageButton
    lateinit var backtotop: ImageButton
    lateinit var editsearchcountry: SearchView
    lateinit var CountryNameTitle: TextView
    lateinit var NamaCountinent: String
    lateinit var mRecyclerView: RecyclerView
    lateinit var countriescovid: ArrayList<CountryCovid>
    lateinit var countryCovidAdapter: CountryCovidAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_country)
        CountryNameTitle = findViewById<View>(R.id.titleCountry) as TextView
        editsearchcountry = findViewById<View>(R.id.searchCountry) as SearchView
        ascendingcountry = findViewById<View>(R.id.sortAscCountry) as ImageButton
        descendingcountry = findViewById<View>(R.id.sortDescCountry) as ImageButton
        backtotop = findViewById<View>(R.id.buttonTop) as ImageButton
        mRecyclerView = findViewById(R.id.rvCountry)
        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        val intent2 = intent
        NamaCountinent = intent2.getStringExtra("continent_name").toString()
        CountryNameTitle.text = intent2.getStringExtra("continent_name")
        getListCountryAsc()
        getSortCountry()
        getSearchCountry()
        getBacktotop()
    }

    private fun getBacktotop() {
        backtotop.setOnClickListener(View.OnClickListener {
            mRecyclerView.smoothScrollToPosition(0)
        })
    }

    private fun getSearchCountry() {
        editsearchcountry.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                if (countryCovidAdapter != null) {
                    countryCovidAdapter.filter(s)
                }
                return false
            }
        })
    }

    private fun getSortCountry() {
        descendingcountry.setOnClickListener(View.OnClickListener {
            Toast.makeText(applicationContext, "Sort Descending", Toast.LENGTH_SHORT).show()
            countriescovid.clear()
            getListCountryDesc()
        })
        ascendingcountry.setOnClickListener {
            Toast.makeText(applicationContext, "Sort Ascending", Toast.LENGTH_SHORT).show()
            countriescovid.clear()
            getListCountryAsc()
        }
    }

    private fun ShowRecyclerView() {
        countryCovidAdapter = CountryCovidAdapter(countriescovid, applicationContext)
        mRecyclerView.adapter = countryCovidAdapter
    }

    private fun getListCountryAsc() {
        val url = "https://disease.sh/v3/covid-19/countries"
        val queue = Volley.newRequestQueue(applicationContext)
        countriescovid = ArrayList()
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val data = jsonArray.getJSONObject(i)
                        val countryinfo = data.getJSONObject("countryInfo")
                        if (data.getString("continent") == NamaCountinent.toString()
                                .trim { it <= ' ' }
                        ) {
                            countriescovid.add(
                                CountryCovid(
                                    countryinfo.getString("flag"),
                                    data.getString("country"),
                                    data.getString("cases"),
                                    data.getString("deaths"),
                                    data.getString("recovered")
                                )
                            )
                        }
                    }
                    ShowRecyclerView()
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
        countriescovid = ArrayList()
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val data = jsonArray.getJSONObject(i)
                        val countryinfo = data.getJSONObject("countryInfo")
                        if (data.getString("continent") == NamaCountinent.toString()
                                .trim { it <= ' ' }
                        ) {
                            countriescovid.add(
                                CountryCovid(
                                    countryinfo.getString("flag"),
                                    data.getString("country"),
                                    data.getString("cases"),
                                    data.getString("deaths"),
                                    data.getString("recovered")
                                )
                            )
                        }
                    }
                    Collections.reverse(countriescovid)
                    ShowRecyclerView()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { }
        queue.cache.clear()
        queue.add(stringRequest)
    }
}