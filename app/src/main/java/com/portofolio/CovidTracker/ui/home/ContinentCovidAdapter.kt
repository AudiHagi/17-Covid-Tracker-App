package com.portofolio.CovidTracker.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.portofolio.CovidTracker.R
import com.portofolio.CovidTracker.ui.continentdetail.ContinentCovidDetailActivity
import java.util.*

class ContinentCovidAdapter(var continentalcovid: ArrayList<ContinentCovid>, var context: Context) :
    RecyclerView.Adapter<ContinentCovidAdapter.ViewHolder>() {
    var arrayList: ArrayList<ContinentCovid>

    init {
        arrayList = ArrayList()
        arrayList.addAll(continentalcovid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_continent, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val continentCovid = continentalcovid[position]
        holder.tvContinentNameD.text = continentCovid.getmContinent()
        holder.tvContinentCaseD.text = continentCovid.getmCase()
        holder.tvContinentDeathD.text = continentCovid.getmDeaths()
        holder.tvContinentRecoverD.text = continentCovid.getmRecovered()
        holder.cardView.setOnClickListener {
            Toast.makeText(context, continentCovid.getmContinent(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ContinentCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("continent_name", continentCovid.getmContinent())
            context.startActivity(intent)
        }
        // set event click on country name
        holder.tvContinentNameD.setOnClickListener {
            Toast.makeText(context, continentCovid.getmContinent(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ContinentCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("continent_name", continentCovid.getmContinent())
            context.startActivity(intent)
        }
        holder.tvContinentCaseD.setOnClickListener {
            Toast.makeText(context, continentCovid.getmContinent(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ContinentCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("continent_name", continentCovid.getmContinent())
            context.startActivity(intent)
        }
        holder.tvContinentDeathD.setOnClickListener {
            Toast.makeText(context, continentCovid.getmContinent(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ContinentCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("continent_name", continentCovid.getmContinent())
            context.startActivity(intent)
        }
        holder.tvContinentRecoverD.setOnClickListener {
            Toast.makeText(context, continentCovid.getmContinent(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ContinentCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("continent_name", continentCovid.getmContinent())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return continentalcovid.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvContinentNameD: TextView
        var tvContinentCaseD: TextView
        var tvContinentDeathD: TextView
        var tvContinentRecoverD: TextView
        var cardView: CardView

        init {
            cardView = itemView.findViewById(R.id.cardCont)
            tvContinentNameD = itemView.findViewById(R.id.tvContinentName)
            tvContinentCaseD = itemView.findViewById(R.id.tvContinentCase)
            tvContinentDeathD = itemView.findViewById(R.id.tvContinentDeath)
            tvContinentRecoverD = itemView.findViewById(R.id.tvContinentRecover)
        }
    }

    fun filter(charText: String) {
        var charText = charText
        charText = charText.lowercase(Locale.getDefault())
        continentalcovid.clear()
        if (charText.length == 0) {
            continentalcovid.addAll(arrayList)
        } else {
            for (wp in arrayList) {
                if (wp.getmContinent().lowercase(Locale.getDefault()).contains(charText)) {
                    continentalcovid.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }
}