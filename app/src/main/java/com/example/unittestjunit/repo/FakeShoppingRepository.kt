package com.example.unittestjunit.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unittestjunit.local.ShoppingItem
import com.example.unittestjunit.newtwork.Resource
import com.example.unittestjunit.remote.reponses.ImageResponse

class FakeShoppingRepository
   : RepositoryImpl
{

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)

    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrices())

    }

    private fun getTotalPrices(): Float {
        return shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }

    override  fun getTotalPrice(): LiveData<Float?> {
        return observableTotalPrice
    }

    override suspend fun insertItemShopping(item: ShoppingItem) {
        shoppingItems.add(item)
        refreshLiveData()
    }

    override suspend fun deleteItemShopping(item: ShoppingItem) {
        shoppingItems.remove(item)
        refreshLiveData()
    }

    override  fun getAllItem(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override suspend fun getPiaxPayImage(query:String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("", null)
        } else {
            Resource.success(ImageResponse(hits = listOf(), total = 0, totalHits = 0))
        }
    }


}