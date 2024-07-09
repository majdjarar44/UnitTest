package com.example.unittestjunit.room

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unittestjunit.local.ShoppingItem

@Dao
interface ShoppingItemDao {

    @Insert
     fun insert(@NonNull item: ShoppingItem)

//    @Insert
//     fun insertAll(vararg items: ShoppingItem): List<Long>

    @Delete
     fun deleteShoppingItem(@NonNull shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shopping_item")
    fun observeAllShoppingItem(): LiveData<List<ShoppingItem>>

    @Query ("SELECT SUM(price*amount) FROM shopping_item")
    fun observeTotalPrice():LiveData<Float?>
}