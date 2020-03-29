package com.sarath.dev.covid.ui.local

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
        return root
    }
}