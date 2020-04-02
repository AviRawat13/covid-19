package com.sarath.dev.covid.ui.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.controllers.adapters.DataRecyclerAdapter
import com.sarath.dev.covid.database.COVIDRoomDatabase

class LocalViewModel : ViewModel() {
    private var dataAdapter: DataRecyclerAdapter? = null

    fun setUpRecyclerView(recyclerView: RecyclerView) {
        dataAdapter = DataRecyclerAdapter(null)
        recyclerView.adapter = dataAdapter
    }

    fun fetchData() {

    }
}