package com.example.unittestjunit

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.unittestjunit.local.ShoppingItem
import com.example.unittestjunit.newtwork.Status
import com.example.unittestjunit.repo.FakeShoppingRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShoppingViewModelTest {

    private lateinit var viewModel: ShoppingViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var instantTaskExecutor = MainCoroutineRule()


    @Before
    fun setUp() {
        viewModel = ShoppingViewModel(shoppingRepository = FakeShoppingRepository())
    }

    @Test
    fun `insert Shopping item with empty Amount,return error`() {
        viewModel.validateShoppingItem("test", "", "3.0")
        val value = viewModel.insertShoppingItem.getOrAwaitValueAndroid ()
        Truth.assertThat(value.giveContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert Shopping item with too long Name,return error`(){
        val name = buildString {
            for(i in 1..MAX_NAME_LENGHT+1){
                append(i)
            }
        }
        viewModel.validateShoppingItem(name, "", "3.0")

        val value = viewModel.insertShoppingItem.getOrAwaitValueAndroid ()
        Truth.assertThat(value.giveContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert Shopping item with too long Price,return error`(){
        val price = buildString {
            for(i in 1..MAX_NAME_LENGHT+1){
                append(i)
            }
        }

        viewModel.validateShoppingItem("majd", "", "$price")
        val value = viewModel.insertShoppingItem.getOrAwaitValueAndroid ()
        Truth.assertThat(value.giveContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert Shopping item with too long Amount,return error`(){
        viewModel.validateShoppingItem("majd", "99999999999999", "3.0")
        val value = viewModel.insertShoppingItem.getOrAwaitValueAndroid ()
        Truth.assertThat(value.giveContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert Shopping item with valid input,return success`(){
        viewModel.validateShoppingItem("majd", "15", "3.0")
        val value = viewModel.insertShoppingItem.getOrAwaitValueAndroid ()
        Truth.assertThat(value.giveContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `insert Shopping item with search image,return success`(){
        viewModel.searchImage("flower")
        val value = viewModel.images.getOrAwaitValueAndroid ()
        value.giveContentIfNotHandled()?.data
        Truth.assertThat(value.giveContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}