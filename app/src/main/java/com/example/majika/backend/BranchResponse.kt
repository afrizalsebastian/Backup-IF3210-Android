package com.example.majika.backend

data class BranchResponse(
    val data: List<BranchData>,
    val size: Int
)

data class BranchData(
    val name: String,
    val popular_food: String,
    val address: String,
    val contact_person: String,
    val phone_number: String,
    val longitude: Double,
    val latitude: Double
)

