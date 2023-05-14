package com.portofolio.CovidTracker.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.portofolio.CovidTracker.R
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class HomeFragment : Fragment() {
    var ascending: ImageButton? = null
    var descending: ImageButton? = null
    var backtotop1: ImageButton? = null
    var editsearch: SearchView? = null
    lateinit var mRecyclerView: RecyclerView
    var continentalcovid: ArrayList<ContinentCovid>? = null
    var continentCovidAdapter: ContinentCovidAdapter? = null
    var scrollPosition = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_home, container, false)
        mRecyclerView = root.findViewById(R.id.rvContinent)
        editsearch = root.findViewById(R.id.search)
        ascending = root.findViewById(R.id.sortAsc)
        descending = root.findViewById(R.id.sortDesc)
        backtotop1 = root.findViewById(R.id.buttonTop)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        dataApiContinentCasesSortAsc
        searchContinent
        sortContinent
        backtotop
        return root
    }

    val backtotop: ImageButton?
        get() {
            backtotop1?.setOnClickListener { mRecyclerView.smoothScrollToPosition(0) }
            return backtotop1
        }
    val sortContinent: Unit
        get() {
            descending!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    Toast.makeText(context, "Sort Descending", Toast.LENGTH_SHORT).show()
                    continentalcovid!!.clear()
                    dataApiContinentCasesSortDesc
                }
            })
            ascending!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    Toast.makeText(context, "Sort Ascending", Toast.LENGTH_SHORT).show()
                    continentalcovid!!.clear()
                    dataApiContinentCasesSortAsc
                }
            })
        }
    val searchContinent: Unit
        get() {
            editsearch!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(s: String): Boolean {
                    if (continentCovidAdapter != null) {
                        continentCovidAdapter!!.filter(s)
                    }
                    return false
                }
            })
        }

    private fun ShowRecyclerView() {
        continentCovidAdapter = ContinentCovidAdapter(continentalcovid!!, requireActivity())
        mRecyclerView.adapter = continentCovidAdapter
        val myLayoutManager = mRecyclerView.layoutManager as LinearLayoutManager?
        scrollPosition = myLayoutManager!!.findFirstVisibleItemPosition()
    }

    private val dataApiContinentCasesSortAsc: Unit
        private get() {
            val queue = Volley.newRequestQueue(activity)
            val url = "https://disease.sh/v3/covid-19/continents"
            continentalcovid = ArrayList()
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    Log.e(TAG, "onResponse$response")
                    if (response != null) {
                        try {
                            val jsonArray = JSONArray(response)
                            for (i in 0 until jsonArray.length()) {
                                val data = jsonArray.getJSONObject(i)
                                continentalcovid!!.add(
                                    ContinentCovid(
                                        data.getString("continent"),
                                        data.getString("cases"),
                                        data.getString("deaths"),
                                        data.getString("recovered")
                                    )
                                )
                            }
                            Collections.sort(continentalcovid, object : Comparator<ContinentCovid> {
                                override fun compare(o1: ContinentCovid, o2: ContinentCovid): Int {
                                    return o1.getmContinent().compareTo(o2.getmContinent())
                                }
                            })

                            ShowRecyclerView()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }
            ) { error -> Log.e(TAG, "onResponse$error") }
            queue.cache.clear()
            queue.add(stringRequest)
        }

    //Descending
    private val dataApiContinentCasesSortDesc: Unit
        private get() {
            val queue = Volley.newRequestQueue(activity)
            val url = "https://disease.sh/v3/covid-19/continents"
            continentalcovid = ArrayList()
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    Log.e(TAG, "onResponse$response")
                    if (response != null) {
                        try {
                            val jsonArray = JSONArray(response)
                            for (i in 0 until jsonArray.length()) {
                                val data = jsonArray.getJSONObject(i)
                                continentalcovid!!.add(
                                    ContinentCovid(
                                        data.getString("continent"),
                                        data.getString("cases"),
                                        data.getString("deaths"),
                                        data.getString("recovered")
                                    )
                                )
                            }
                            //Descending
                            Collections.sort(continentalcovid, object : Comparator<ContinentCovid> {
                                override fun compare(o1: ContinentCovid, o2: ContinentCovid): Int {
                                    return o1.getmContinent().compareTo(o2.getmContinent())
                                }
                            })
                            Collections.reverse(continentalcovid)
                            ShowRecyclerView()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }
            ) { error -> Log.e(TAG, "onResponse$error") }
            queue.cache.clear()
            queue.add(stringRequest)
        }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }

}