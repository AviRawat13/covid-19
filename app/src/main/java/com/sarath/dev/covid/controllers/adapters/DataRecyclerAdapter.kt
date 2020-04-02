package com.sarath.dev.covid.controllers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sarath.dev.covid.R
import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.MainActivity
import com.sarath.dev.covid.controllers.network.summary.SummaryCountryResponse
import com.sarath.dev.covid.controllers.utils.Constants
import com.sarath.dev.covid.controllers.utils.Info
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class DataRecyclerAdapter(var dataItems: ArrayList<SummaryCountryResponse>?) :
    RecyclerView.Adapter<DataRecyclerAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var flagImage = itemView.findViewById<ImageView>(R.id.flag_image)!!
        var countryName: TextView = itemView.findViewById(R.id.country_name)
        var infoRecyclerView: RecyclerView = itemView.findViewById(R.id.info_recycler_view)
        var infoAdapter: DataInfoRecyclerAdapter = DataInfoRecyclerAdapter(null)

        init {
            infoRecyclerView.adapter = infoAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(COVID19.context).inflate(R.layout.list_item_data, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if(dataItems != null) {
            dataItems!!.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val data = dataItems!![position]
        holder.countryName.text = data.country!!.split("(")[0]

        if (!data.flagURL.isNullOrEmpty()) Picasso.with(COVID19.context).load(data.flagURL).into(holder.flagImage)

        when {
            holder.infoAdapter.infoItems != null -> {
                holder.infoAdapter.infoItems!!.clear()
            }
            else -> {
                holder.infoAdapter.infoItems = ArrayList()
            }
        }

        holder.infoAdapter.infoItems!!.add(
            Info(
                data.totalConfirmed,
                data.newConfirmed,
                Constants.CONFIRMED
            )
        )

        holder.infoAdapter.infoItems!!.add(
            Info(
                data.totalRecovered,
                data.newRecovered,
                Constants.RECOVERED
            )
        )

        holder.infoAdapter.infoItems!!.add(
            Info(
                data.totalDeaths,
                data.newDeaths,
                Constants.DECEASED
            )
        )

        holder.infoAdapter.notifyDataSetChanged()
    }
}