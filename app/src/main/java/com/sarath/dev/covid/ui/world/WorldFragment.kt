package com.sarath.dev.covid.ui.world

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.sarath.dev.covid.R

class WorldFragment : Fragment() {
    private lateinit var worldViewModel: WorldViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        worldViewModel =
            ViewModelProviders.of(this).get(WorldViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_world, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.world_recycler_view)
        worldViewModel.setUpRecyclerView(recyclerView)
        worldViewModel.fetchData()
        return root
    }
}