package com.sarath.dev.covid.ui.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.R
import com.sarath.dev.covid.controllers.utils.ToastsUtil
import com.sarath.dev.covid.database.COVIDRoomDatabase
import com.sarath.dev.covid.database.entity.LocalEntity

class LocalFragment : Fragment() {

    private lateinit var localViewModel: LocalViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        localViewModel =
            ViewModelProviders.of(this).get(LocalViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_local, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.local_recycler_view)
        localViewModel.setUpRecyclerView(recyclerView)
        fetchData()
        return root
    }

    private fun fetchData() {
        COVIDRoomDatabase.getDatabase(COVID19.context!!)
            .localDao()
            .getLocalData().observe(this,
                Observer {
                    setViews(it[0])
                })

    }

    private fun setViews(entity: LocalEntity) {
        ToastsUtil.d(entity.country)
    }
}