package com.portofolio.CovidTracker.ui.fragment.global

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

class GlobalFragment : Fragment() {
    private var ascending: ImageButton? = null
    private var descending: ImageButton? = null
    private var backToTopButton: ImageButton? = null
    private var editSearch: SearchView? = null
    private lateinit var myRecyclerView: RecyclerView
    private var continentalCovid: ArrayList<ContinentCovid>? = null
    var continentCovidAdapter: ContinentCovidAdapter? = null
    private var scrollPosition = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = inflater.inflate(R.layout.fragment_global, container, false)
        myRecyclerView = root.findViewById(R.id.rvContinent)
        editSearch = root.findViewById(R.id.search)
        ascending = root.findViewById(R.id.ibSortAsc)
        descending = root.findViewById(R.id.ibSortDesc)
        backToTopButton = root.findViewById(R.id.ibToTop)
        myRecyclerView.layoutManager = LinearLayoutManager(activity)
        continentCasesSortAsc
        searchContinent
        sortContinent
        backToTop
        return root
    }

    private val backToTop: ImageButton?
        get() {
            backToTopButton?.setOnClickListener { myRecyclerView.smoothScrollToPosition(0) }
            return backToTopButton
        }
    private val sortContinent: Unit
        get() {
            descending!!.setOnClickListener {
                Toast.makeText(context, "Sort Descending", Toast.LENGTH_SHORT).show()
                continentalCovid!!.clear()
                continentCasesSortDesc
            }
            ascending!!.setOnClickListener {
                Toast.makeText(context, "Sort Ascending", Toast.LENGTH_SHORT).show()
                continentalCovid!!.clear()
                continentCasesSortAsc
            }
        }
    private val searchContinent: Unit
        get() {
            editSearch!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

    private fun showRecyclerView() {
        continentCovidAdapter = ContinentCovidAdapter(continentalCovid!!, requireActivity())
        myRecyclerView.adapter = continentCovidAdapter
        val myLayoutManager = myRecyclerView.layoutManager as LinearLayoutManager?
        scrollPosition = myLayoutManager!!.findFirstVisibleItemPosition()
    }

    private val continentCasesSortAsc: Unit
        get() {
            val queue = Volley.newRequestQueue(activity)
            val url = "https://disease.sh/v3/covid-19/continents"
            continentalCovid = ArrayList()
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    Log.e(TAG, "onResponse$response")
                    if (response != null) {
                        try {
                            val jsonArray = JSONArray(response)
                            for (i in 0 until jsonArray.length()) {
                                val data = jsonArray.getJSONObject(i)
                                continentalCovid!!.add(
                                    ContinentCovid(
                                        data.getString("continent"),
                                        data.getString("cases"),
                                        data.getString("deaths"),
                                        data.getString("recovered")
                                    )
                                )
                            }
                            continentalCovid?.let {
                                it.sortWith { o1, o2 ->
                                    o1.getContinent().compareTo(o2.getContinent())
                                }
                            }

                            showRecyclerView()
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
    private val continentCasesSortDesc: Unit
        get() {
            val queue = Volley.newRequestQueue(activity)
            val url = "https://disease.sh/v3/covid-19/continents"
            continentalCovid = ArrayList()
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    Log.e(TAG, "onResponse$response")
                    if (response != null) {
                        try {
                            val jsonArray = JSONArray(response)
                            for (i in 0 until jsonArray.length()) {
                                val data = jsonArray.getJSONObject(i)
                                continentalCovid!!.add(
                                    ContinentCovid(
                                        data.getString("continent"),
                                        data.getString("cases"),
                                        data.getString("deaths"),
                                        data.getString("recovered")
                                    )
                                )
                            }
                            //Descending
                            continentalCovid?.let {
                                it.sortWith { o1, o2 ->
                                    o1.getContinent().compareTo(o2.getContinent())
                                }
                            }
                            continentalCovid?.reverse()
                            showRecyclerView()
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
        private val TAG = GlobalFragment::class.java.simpleName
    }

}