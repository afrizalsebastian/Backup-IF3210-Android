package com.example.majika.menu

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.majika.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private val viewModel: MenuViewModel by viewModels()
    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuBinding.inflate(
            inflater,
            container,
            false
        )

        val menuAdapter = MenuAdapter()
        binding.menuResponse.adapter = menuAdapter
        binding.menuResponse.layoutManager = LinearLayoutManager(context)

        viewModel.menuData.observe(viewLifecycleOwner, Observer {
            menuAdapter.setMenus(it)
        })

        var manager = GridLayoutManager(activity, 1)
        val orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            manager = GridLayoutManager(activity, 2)
        }
        binding.menuResponse.layoutManager = manager
        return binding.root
    }
}