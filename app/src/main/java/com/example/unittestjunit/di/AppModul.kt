package com.example.unittestjunit.di

import android.content.Context
import androidx.room.Room
import com.example.unittestjunit.BASE_URL
import com.example.unittestjunit.DATABASE_NAME
import com.example.unittestjunit.newtwork.PixapayApi
import com.example.unittestjunit.repo.RepositoryImpl
import com.example.unittestjunit.repo.ShoppingRepository
import com.example.unittestjunit.room.ShoppingItemDao
import com.example.unittestjunit.room.ShoppingItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()


    @Singleton
    @Provides
    fun provideShoppingDao(database: ShoppingItemDatabase) = database.shoppingItemDao()

    @Singleton
    @Provides
    fun providePixaPayApi(): PixapayApi {
        return Retrofit.Builder().addConverterFactory(
            GsonConverterFactory.create()
        ).baseUrl(BASE_URL).build().create(PixapayApi::class.java)
    }


    @Provides
    @Named("API_KEY")
    fun provideBaseUrl(): String {
        return "44795913-6fcbae63b5b8b2ee59596e0f3"
    }
    @Singleton
    @Provides
    fun provideShoppingRepo(
        @Named("API_KEY") apiKey:String,
        dao: ShoppingItemDao,
        pixapayApi: PixapayApi
    ) = ShoppingRepository(apiKey,dao, pixapayApi) as RepositoryImpl
}