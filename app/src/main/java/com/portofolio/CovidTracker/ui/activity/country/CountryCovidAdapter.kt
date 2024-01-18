package com.portofolio.CovidTracker.ui.activity.country

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.portofolio.CovidTracker.R
import com.portofolio.CovidTracker.ui.activity.countrydetail.CountryCovidDetailActivity
import java.util.*

class CountryCovidAdapter(
    private var countriesCovid: ArrayList<CountryCovid>,
    var context: Context
) :
    RecyclerView.Adapter<CountryCovidAdapter.ViewHolder>() {
    private var arrayList: ArrayList<CountryCovid> = ArrayList()

    init {
        arrayList.addAll(countriesCovid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val countryCovid = countriesCovid[position]
        holder.tvCountryNameD.text = countryCovid.getCountry()
        holder.tvCountryCaseD.text = countryCovid.getCaseC()
        holder.tvCountryDeathD.text = countryCovid.getDeathC()
        holder.tvCountryRecoverD.text = countryCovid.getRecoverC()
        Glide.with(context).load(countryCovid.getFlag()).apply(RequestOptions()).override(240, 160)
            .into(holder.imgFlagCountry)
        holder.cardView.setOnClickListener {
            Toast.makeText(context, countryCovid.getCountry(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, CountryCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("country_name", countryCovid.getCountry())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        // set event click on country name
        holder.tvCountryNameD.setOnClickListener {
            Toast.makeText(context, countryCovid.getCountry(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, CountryCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("country_name", countryCovid.getCountry())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        holder.imgFlagCountry.setOnClickListener {
            Toast.makeText(context, countryCovid.getCountry(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, CountryCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("country_name", countryCovid.getCountry())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        holder.tvCountryCaseD.setOnClickListener {
            Toast.makeText(context, countryCovid.getCountry(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, CountryCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("country_name", countryCovid.getCountry())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        holder.tvCountryDeathD.setOnClickListener {
            Toast.makeText(context, countryCovid.getCountry(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, CountryCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("country_name", countryCovid.getCountry())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        holder.tvCountryRecoverD.setOnClickListener {
            Toast.makeText(context, countryCovid.getCountry(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, CountryCovidDetailActivity::class.java)
            //send parameters to ContinentCovidDetail Activity
            intent.putExtra("country_name", countryCovid.getCountry())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return countriesCovid.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCountryNameD: TextView
        var tvCountryCaseD: TextView
        var tvCountryDeathD: TextView
        var tvCountryRecoverD: TextView
        var imgFlagCountry: ImageView
        var cardView: CardView

        init {
            cardView = itemView.findViewById(R.id.cardCountry)
            tvCountryNameD = itemView.findViewById(R.id.tvCountryName)
            tvCountryCaseD = itemView.findViewById(R.id.tvCountryCase)
            tvCountryDeathD = itemView.findViewById(R.id.tvCountryDeath)
            tvCountryRecoverD = itemView.findViewById(R.id.tvCountryRecover)
            imgFlagCountry = itemView.findViewById(R.id.ivFlagCountry)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(charText: String) {
        var charText = charText
        charText = charText.lowercase(Locale.getDefault())
        countriesCovid.clear()
        if (charText.isEmpty()) {
            countriesCovid.addAll(arrayList)
        } else {
            for (wp in arrayList) {
                if (wp.getCountry().lowercase(Locale.getDefault()).contains(charText)) {
                    countriesCovid.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }
}