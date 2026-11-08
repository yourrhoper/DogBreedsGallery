package com.example.dogbreedsgallery.data.remote // настройка Retrofit, Moshi и OkHttp

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.thedogapi.com/v1/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply { // позволяет видеть информацию о запросах в Logcat
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val okHttpClient = OkHttpClient.Builder() // выполнение HTTP-запросов
        .addInterceptor(loggingInterceptor)
        .build()

    private val moshi = Moshi.Builder() // преобразует JSON в Kotlin-объекты
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder() // создаёт реализацию интерфейса DogApiService
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val api: DogApiService = retrofit.create(DogApiService::class.java)
}
