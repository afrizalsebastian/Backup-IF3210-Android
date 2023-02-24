package com.example.majika.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.majika.backend.MenuData
import com.example.majika.backend.MenuResponse
import com.example.majika.backend.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuViewModel : ViewModel() {
    private val _menuData = MutableLiveData<List<MenuData>>()
    val menuData: LiveData<List<MenuData>>
        get() = _menuData

    fun getMenuData() {
        RetrofitClient.instace.getMenu().enqueue(object : Callback<MenuResponse> {
            override fun onResponse(
                call: Call<MenuResponse>,
                response: Response<MenuResponse>
            ) {
                Log.i("ResponseCode", "OnSuccess Hit API")
                val data = response.body()!!.data
                _menuData.value = data
            }

            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                Log.i("ResponseCode", "OnFailure!!")
            }
        })
    }

    init {
        viewModelScope.launch {
            getMenuData()
        }
    }
}