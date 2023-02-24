package com.example.majika.menu

import android.content.Context
import android.hardware.SensorManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
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

class MenuFragment : Fragment(), SensorEventListener {
    private val viewModel: MenuViewModel by viewModels()
    private lateinit var binding: FragmentMenuBinding
    private lateinit var sensormanager: SensorManager
    private lateinit var tempsensor: Sensor
    private var isTempSensorAvailable = false

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

        sensormanager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        if (sensormanager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            tempsensor = sensormanager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
            isTempSensorAvailable = true
        } else {
            binding.temperature.text = "-"
            isTempSensorAvailable = false
        }
        return binding.root
    }

    override fun onSensorChanged(event: SensorEvent) {
        binding.temperature.text = (event.values[0].toInt()).toString() + "°C"
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onResume() {
        super.onResume()
        if(isTempSensorAvailable){
            sensormanager.registerListener(this, tempsensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        if(isTempSensorAvailable){
            sensormanager.unregisterListener(this)
        }
    }
}