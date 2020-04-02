package com.sarath.dev.covid.ui.world

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.R
import com.sarath.dev.covid.controllers.utils.Constants

class WorldFragment : Fragment() {
    private lateinit var worldViewModel: WorldViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        worldViewModel =
            ViewModelProviders.of(this).get(WorldViewModel::class.java)

        activity?.setTheme(R.style.AppTheme)
        val root = inflater.inflate(R.layout.fragment_world, container, false)
        val refreshLayout = root.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        val recyclerView = root.findViewById<RecyclerView>(R.id.world_recycler_view)

        worldViewModel.setUpRefreshLayoutCallbacks(refreshLayout)
        worldViewModel.setUpRecyclerView(recyclerView)
        worldViewModel.fetchData()
        return root
    }
}