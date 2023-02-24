package com.example.majika.branch

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.majika.databinding.FragmentBranchBinding

class BranchFragment : Fragment() {

    private val viewModel: BranchViewModel by viewModels()
    private lateinit var binding: FragmentBranchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBranchBinding.inflate(
            inflater,
            container,
            false
        )

        val branchAdapter = BranchAdapter()
        binding.branchResponse.adapter = branchAdapter
        binding.branchResponse.layoutManager = LinearLayoutManager(context)

        viewModel.branchData.observe(viewLifecycleOwner, Observer {
            branchAdapter.setRestaurants(it)
        })

        var manager = GridLayoutManager(activity, 1)
        val orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            manager = GridLayoutManager(activity, 2)
        }
        binding.branchResponse.layoutManager = manager
        return binding.root
    }
}