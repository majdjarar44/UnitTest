package com.example.unittestjunit.repo

import androidx.lifecycle.LiveData
import com.example.unittestjunit.local.ShoppingItem
import com.example.unittestjunit.newtwork.Resource
import com.example.unittestjunit.remote.reponses.ImageResponse

interface RepositoryImpl {

     fun getTotalPrice(): LiveData<Float?>

    suspend fun insertItemShopping(item: ShoppingItem)

    suspend fun deleteItemShopping(item: ShoppingItem)

     fun getAllItem():LiveData<List<ShoppingItem>>

    suspend fun getPiaxPayImage(query:String): Resource<ImageResponse>
}