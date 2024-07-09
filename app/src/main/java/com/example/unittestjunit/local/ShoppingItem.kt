package com.example.unittestjunit.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_item")
data class ShoppingItem(
    @ColumnInfo(name = "name")  val name: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "price")  val price: Float,
    @ColumnInfo(name = "amount")val amount: Int,
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
)


