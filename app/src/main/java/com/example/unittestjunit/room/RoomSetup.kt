package com.example.unittestjunit.room

import android.content.Context
import androidx.room.Room


object DatabaseProvider {
    @Volatile
    private var INSTANCE: ShoppingItemDatabase? = null

    fun getDatabase(context: Context): ShoppingItemDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ShoppingItemDatabase::class.java,
                "shopping_item"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}


