package com.example.majika.room

import androidx.room.*

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(vararg items: CartItem)

    @Delete
    suspend fun deleteItems(vararg items: CartItem)

    @Update
    suspend fun updateItems(vararg items: CartItem)

    @Query("SELECT * FROM item")
    suspend fun loadAllItems(): Array<CartItem>
}