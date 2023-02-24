package com.example.majika.backend

import retrofit2.Call
import retrofit2.http.GET

interface API {
    @GET("v1/branch")
    fun getBranch(): Call<BranchResponse>
    @GET("v1/menu")
    fun getMenu(): Call<MenuResponse>
}
