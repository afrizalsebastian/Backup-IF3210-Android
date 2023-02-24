package com.example.majika.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class CartItem(
    @PrimaryKey val itemName: String,
    val price : Int?,
    val total : Int
)
