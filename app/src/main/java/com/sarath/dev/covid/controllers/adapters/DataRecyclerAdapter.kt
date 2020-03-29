package com.sarath.dev.covid.controllers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sarath.dev.covid.R
import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.controllers.network.summary.SummaryCountryResponse
import com.sarath.dev.covid.controllers.utils.Constants
import com.sarath.dev.covid.controllers.utils.Info
import com.squareup.picasso.Picasso

class DataRecyclerAdapter(var dataItems: ArrayList<SummaryCountryResponse>?) :
    RecyclerView.Adapter<DataRecyclerAdapter.DataViewHolder>() {

    private val infoAdapter: DataInfoRecyclerAdapter = DataInfoRecyclerAdapter(null)

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var flagImage: ImageView = itemView.findViewById(R.id.flag_image)
        var countryName: TextView = itemView.findViewById(R.id.country_name)
        var infoRecyclerView: RecyclerView = itemView.findViewById(R.id.info_recycler_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(COVID19.context).inflate(R.layout.list_item_data, parent, false)
        val holder = DataViewHolder(view)
        holder.infoRecyclerView.adapter = infoAdapter
        return holder
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
        holder.countryName.text = data.country

        Picasso.with(COVID19.context).load(Constants.flag("IN")).into(holder.flagImage)

        when {
            infoAdapter.infoItems != null -> {
                infoAdapter.infoItems!!.clear()
            }
            else -> {
                infoAdapter.infoItems = ArrayList()
            }
        }

        infoAdapter.infoItems!!.add(
            Info(
                data.totalConfirmed,
                data.newConfirmed,
                Constants.CONFIRMED
            )
        )

        infoAdapter.infoItems!!.add(
            Info(
                data.totalRecovered,
                data.newRecovered,
                Constants.RECOVERED
            )
        )

        infoAdapter.infoItems!!.add(
            Info(
                data.totalDeaths,
                data.newDeaths,
                Constants.DECEASED
            )
        )
    }
}