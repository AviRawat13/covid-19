package com.sarath.dev.covid.controllers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.R
import com.sarath.dev.covid.controllers.utils.Constants
import com.sarath.dev.covid.controllers.utils.Info

class DataInfoRecyclerAdapter(var infoItems: ArrayList<Info>?) :
    RecyclerView.Adapter<DataInfoRecyclerAdapter.DataInfoViewHolder>() {

    class DataInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parentLayout: RelativeLayout = itemView.findViewById(R.id.parent_layout)
        var totalCasesTextView: TextView = itemView.findViewById(R.id.total_cases)
        var newCasesTextView: TextView = itemView.findViewById(R.id.new_cases)
        var title: TextView = itemView.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataInfoViewHolder {
        val view = LayoutInflater.from(COVID19.context!!).inflate(R.layout.list_item_info, parent, false)
        return DataInfoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (infoItems != null) {
            infoItems!!.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: DataInfoViewHolder, position: Int) {
        val info = infoItems!![position]
        when (info.type) {
            Constants.DECEASED -> {
                holder.title.text = info.type
            }
            else -> {
                holder.title.text = COVID19.context!!.getString(R.string.x_cases, info.type)
            }
        }

        setTextColor(holder, info)
        holder.totalCasesTextView.text = info.totalCases.toString()
        holder.newCasesTextView.text = info.newCases.toString()
    }

    private fun setTextColor(holder: DataInfoViewHolder, info: Info) {
        when (info.type) {
            Constants.CONFIRMED -> {
                holder.newCasesTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_red_16dp, 0, 0, 0)
                holder.totalCasesTextView.setTextColor(ContextCompat.getColor(COVID19.context!!, android.R.color.holo_red_dark))
                holder.newCasesTextView.setTextColor(ContextCompat.getColor(COVID19.context!!, android.R.color.holo_red_dark))
                holder.title.setTextColor(ContextCompat.getColor(COVID19.context!!, android.R.color.holo_red_dark))
            }
            Constants.RECOVERED -> {
                holder.newCasesTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_green_16dp, 0, 0, 0)
                holder.totalCasesTextView.setTextColor(ContextCompat.getColor(COVID19.context!!, android.R.color.holo_green_dark))
                holder.newCasesTextView.setTextColor(ContextCompat.getColor(COVID19.context!!, android.R.color.holo_green_dark))
                holder.title.setTextColor(ContextCompat.getColor(COVID19.context!!, android.R.color.holo_green_dark))
            }
            Constants.DECEASED -> {
                holder.newCasesTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_red_16dp, 0, 0, 0)
                holder.totalCasesTextView.setTextColor(ContextCompat.getColor(COVID19.context!!, android.R.color.black))
                holder.newCasesTextView.setTextColor(ContextCompat.getColor(COVID19.context!!, android.R.color.black))
                holder.title.setTextColor(ContextCompat.getColor(COVID19.context!!, android.R.color.black))
            }
        }
    }


}