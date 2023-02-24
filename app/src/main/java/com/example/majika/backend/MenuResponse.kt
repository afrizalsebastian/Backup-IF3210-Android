package com.example.majika.backend

data class MenuResponse(
    val data: List<MenuData>,
    val size: Int
)

data class MenuData(
    val name: String,
    val description: String,
    val currency: String,
    val price: Int,
    val sold: Int,
    val type: String,
)