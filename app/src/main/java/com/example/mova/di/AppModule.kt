package com.example.mova.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.mova.api.MovaService
import com.example.mova.api.TokenInterceptor
import com.example.mova.local.MovieDAO
import com.example.mova.local.MovieDataBase
import com.example.mova.utils.Constants.API_TOKEN
import com.example.mova.utils.Constants.BASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("mySharedPref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(): TokenInterceptor {
        return TokenInterceptor(API_TOKEN)
    }

    @Provides
    @Singleton
    fun provideOkHttp(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(tokenInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideService(okHttpClient: OkHttpClient): MovaService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
            .create(MovaService::class.java)
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun createRoom(@ApplicationContext context: Context): MovieDataBase {
        return Room.databaseBuilder(context, MovieDataBase::class.java, "movie_db").build()
    }

    @Provides
    @Singleton
    fun provideDao(movieDataBase: MovieDataBase): MovieDAO {
        return movieDataBase.getDao()
    }


}