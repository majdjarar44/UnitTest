package com.example.unittestjunit

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.unittestjunit.auth.ResourceCompare
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResourceCompareTest {

    lateinit var resourceCompare: ResourceCompare

    /*
        1...here more slowly and when i need to use context
        2... @Before this function call when run each test case
     */


    @Before
    fun setup(){
        resourceCompare = ResourceCompare()
    }

    @Test
    fun StringResourceSameAsGivenString_isTrue() {
//        val resourceCompare = ResourceCompare()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceCompare.compareString(
            context,
            R.string.app_name,
            "Unit Test Junit"
        )//"Unit Test")
        assertThat(result).isTrue()
    }

    @Test
    fun StringResourceDifferantAsGivenString_isFalse() {
//        val resourceCompare = ResourceCompare()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceCompare.compareString(context, R.string.app_name, "hello")
        assertThat(result).isFalse()
    }
}