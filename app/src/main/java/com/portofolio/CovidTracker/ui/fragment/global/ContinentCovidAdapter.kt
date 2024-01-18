package com.portofolio.CovidTracker.ui.fragment.global

import android.annotation.SuppressLint
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
import com.portofolio.CovidTracker.ui.activity.continentdetail.ContinentCovidDetailActivity
import java.util.*

class ContinentCovidAdapter(
    private var continentalCovid: ArrayList<ContinentCovid>,
    var context: Context
) :
    RecyclerView.Adapter<ContinentCovidAdapter.ViewHolder>() {
    private var arrayList: ArrayList<ContinentCovid> = ArrayList()

    init {
        arrayList.addAll(continentalCovid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_continent, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val continentCovid = continentalCovid[position]
        holder.tvContinentNameD.text = continentCovid.getContinent()
        holder.tvContinentCaseD.text = continentCovid.getCase()
        holder.tvContinentDeathD.text = continentCovid.getDeaths()
        holder.tvContinentRecoverD.text = continentCovid.getRecovered()
        holder.cardView.setOnClickListener {
            Toast.makeText(context, continentCovid.getContinent(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ContinentCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("continent_name", continentCovid.getContinent())
            context.startActivity(intent)
        }
        // set event click on country name
        holder.tvContinentNameD.setOnClickListener {
            Toast.makeText(context, continentCovid.getContinent(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ContinentCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("continent_name", continentCovid.getContinent())
            context.startActivity(intent)
        }
        holder.tvContinentCaseD.setOnClickListener {
            Toast.makeText(context, continentCovid.getContinent(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ContinentCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("continent_name", continentCovid.getContinent())
            context.startActivity(intent)
        }
        holder.tvContinentDeathD.setOnClickListener {
            Toast.makeText(context, continentCovid.getContinent(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ContinentCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("continent_name", continentCovid.getContinent())
            context.startActivity(intent)
        }
        holder.tvContinentRecoverD.setOnClickListener {
            Toast.makeText(context, continentCovid.getContinent(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ContinentCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("continent_name", continentCovid.getContinent())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return continentalCovid.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvContinentNameD: TextView
        var tvContinentCaseD: TextView
        var tvContinentDeathD: TextView
        var tvContinentRecoverD: TextView
        var cardView: CardView

        init {
            cardView = itemView.findViewById(R.id.cardContinent)
            tvContinentNameD = itemView.findViewById(R.id.tvContinentName)
            tvContinentCaseD = itemView.findViewById(R.id.tvContinentCase)
            tvContinentDeathD = itemView.findViewById(R.id.tvContinentDeath)
            tvContinentRecoverD = itemView.findViewById(R.id.tvContinentRecover)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(charText: String) {
        var charText = charText
        charText = charText.lowercase(Locale.getDefault())
        continentalCovid.clear()
        if (charText.isEmpty()) {
            continentalCovid.addAll(arrayList)
        } else {
            for (wp in arrayList) {
                if (wp.getContinent().lowercase(Locale.getDefault()).contains(charText)) {
                    continentalCovid.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }
}