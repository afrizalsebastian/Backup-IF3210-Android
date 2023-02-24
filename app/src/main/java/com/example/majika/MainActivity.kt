package com.example.majika

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.example.majika.branch.BranchFragment
import com.example.majika.databinding.ActivityMainBinding
import com.example.majika.room.AppDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    private lateinit var binding : ActivityMainBinding
    private lateinit var appDb : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val navController = findNavController(R.id.nav_fragment)
        bottomNavigationView.setupWithNavController(navController)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        appDb = AppDatabase.getDatabase(this)
//        binding.clearcache.setOnClickListener {
//            GlobalScope.launch(Dispatchers.IO) {
//                appDb.clearAllTables()
//            }
//        }
    }
}