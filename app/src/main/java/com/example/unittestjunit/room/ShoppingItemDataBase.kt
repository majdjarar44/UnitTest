package com.example.unittestjunit.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unittestjunit.local.ShoppingItem

@Database(version = 3,entities = [ShoppingItem::class],  exportSchema = false)
abstract class ShoppingItemDatabase : RoomDatabase() {
    abstract fun shoppingItemDao(): ShoppingItemDao
}