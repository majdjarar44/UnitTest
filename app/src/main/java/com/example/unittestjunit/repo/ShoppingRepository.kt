package com.example.unittestjunit.repo

import androidx.lifecycle.LiveData
import com.example.unittestjunit.newtwork.PixapayApi
import com.example.unittestjunit.local.ShoppingItem
import com.example.unittestjunit.newtwork.Resource
import com.example.unittestjunit.remote.reponses.ImageResponse
import com.example.unittestjunit.room.ShoppingItemDao
import javax.inject.Inject

class ShoppingRepository @Inject constructor
    (
    val apiKey:String,
    val dao: ShoppingItemDao,
    val pixapayInterface: PixapayApi
) : RepositoryImpl
{

    override  fun getTotalPrice(): LiveData<Float?> {
        return dao.observeTotalPrice()
    }

    override suspend fun insertItemShopping(item: ShoppingItem) {
        dao.insert(item)
    }

    override suspend fun deleteItemShopping(item: ShoppingItem) {
        dao.deleteShoppingItem(item)
    }

    override  fun getAllItem():  LiveData<List<ShoppingItem>> {
        return dao.observeAllShoppingItem()
    }

    override suspend fun getPiaxPayImage(query:String): Resource<ImageResponse> {
       return try {
            val result = pixapayInterface.searchForImage(query, apiKey)
            if (result.isSuccessful) {
                result.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(message = "An Unknown Error occured", data = null)
            } else {
                Resource.error(message = "An Unknown Error occured", data = null)
            }

        } catch (e: Exception) {
            Resource.error(message = "Couldn't reach the server", data = null)
        }

    }
}