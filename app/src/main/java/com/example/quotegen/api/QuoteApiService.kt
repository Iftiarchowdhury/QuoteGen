package com.example.quotegen.api

import com.example.quotegen.data.QuoteResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface QuoteApiService {
    @Headers(
        "X-Api-Key: uy3FYRLuelCsppl10Ee5aA==O41HaUR2PEkhUikt"
    )
    @GET("v1/quotes")
    suspend fun getRandomQuote(): List<QuoteResponse>

    companion object {
        private const val BASE_URL = "https://api.api-ninjas.com/"

        fun create(): QuoteApiService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(QuoteApiService::class.java)
        }
    }
} 