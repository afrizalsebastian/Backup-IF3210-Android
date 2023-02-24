package com.example.majika.branch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.majika.backend.BranchData
import com.example.majika.backend.BranchResponse
import com.example.majika.backend.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BranchViewModel : ViewModel() {

    private val _branchData = MutableLiveData<List<BranchData>>()
    val branchData: LiveData<List<BranchData>>
        get() = _branchData

    fun getBranchData() {
        RetrofitClient.instace.getBranch().enqueue(object : Callback<BranchResponse> {
            override fun onResponse(
                call: Call<BranchResponse>,
                response: Response<BranchResponse>
            ) {
                Log.i("ResponseCode", "OnSuccess Hit API")
                val data = response.body()!!.data
                _branchData.value = data
            }

            override fun onFailure(call: Call<BranchResponse>, t: Throwable) {
                Log.i("ResponseCode", "OnFailure!!")
            }
        })
    }

    init {
        viewModelScope.launch {
            getBranchData()
        }
    }
}