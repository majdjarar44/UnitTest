package com.example.unittestjunit

import android.app.Application
import android.provider.SyncStateContract.Constants
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unittestjunit.local.ShoppingItem
import com.example.unittestjunit.newtwork.Event
import com.example.unittestjunit.newtwork.Resource
import com.example.unittestjunit.newtwork.Status
import com.example.unittestjunit.remote.reponses.ImageResponse
import com.example.unittestjunit.repo.FakeShoppingRepository
import com.example.unittestjunit.repo.RepositoryImpl
import com.example.unittestjunit.repo.ShoppingRepository
import com.example.unittestjunit.room.DatabaseProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    val shoppingRepository: RepositoryImpl
) : ViewModel() {

    val shoppingItems = shoppingRepository.getAllItem()

    val totalPrice = shoppingRepository.getTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String> = _curImageUrl

    private val _insertShoppingItem = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItem: LiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItem


//    private val shoppingItemDao = DatabaseProvider.getDatabase(application).shoppingItemDao()

    fun insertItem(item: ShoppingItem) {
        viewModelScope.launch {
//            shoppingItemDao.insert(item)
        }
    }

    fun setCurImageUrl(url: String) = viewModelScope.launch {
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(item: ShoppingItem) = viewModelScope.launch {
        shoppingRepository.deleteItemShopping(item)
    }

    fun insertShoppingItemDb(item: ShoppingItem) = viewModelScope.launch {
        shoppingRepository.insertItemShopping(item)
    }

    fun validateShoppingItem(name: String, amount: String, price: String) {
        if (name.isNullOrEmpty() || amount.isNullOrEmpty() || price.isNullOrEmpty()) {
            _insertShoppingItem.postValue(
                Event(
                    Resource.error(
                        "This Field Must not be empty",
                        null
                    )
                )
            )
            return
        }
        if (name.length > MAX_NAME_LENGHT) {
            _insertShoppingItem.postValue(
                Event(
                    Resource.error(
                        "The name of the item must noe exceed ${MAX_NAME_LENGHT} characters",
                        null
                    )
                )
            )
            return
        }

        if (price.length > MAX_PRICE_LENGTH) {
            _insertShoppingItem.postValue(
                Event(
                    Resource.error(
                        "The price of the item must noe exceed ${MAX_PRICE_LENGTH}",
                        null
                    )
                )
            )
            return
        }

        val amount = try {
            amount.toInt()
        } catch (e: Exception) {
            _insertShoppingItem.postValue(
                Event(
                    Resource.error(
                        "Enter valid Amount",
                        null
                    )
                )
            )
            return
        }

        val shoppingItem = ShoppingItem(
            name,
            imageUrl = _curImageUrl.value ?: "",
            amount = amount.toString().toInt(),
            price = price.toFloat()
        )
        insertShoppingItemDb(item = shoppingItem)
        setCurImageUrl("")
        _insertShoppingItem.postValue(Event(Resource.success(shoppingItem)))
    }

    fun searchImage(imageQuery: String) {
        if (imageQuery.isEmpty()) {
            return
        }
        _images.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = shoppingRepository.getPiaxPayImage(imageQuery)
            _images.postValue(Event(response))

        }
    }
}