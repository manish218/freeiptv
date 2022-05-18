package com.spark.channelshome.data.retrofit

import android.content.Context
import com.spark.channelshome.data.model.IPTVChannel
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.File

interface ChannelsRetrofitService {
    @GET("/channels.json")
    suspend fun getAllChannels(): Response<List<IPTVChannel>>

    companion object {
        //TODO: Move to DI
        private var retrofitService: ChannelsRetrofitService? = null

        fun getInstance(applicationContext: Context): ChannelsRetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://howtodoandroid.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient(applicationContext))
                    .build()
                retrofitService = retrofit.create(ChannelsRetrofitService::class.java)
            }
            return retrofitService!!
        }

        private fun getOkHttpClient(applicationContext: Context): OkHttpClient {
            return OkHttpClient.Builder()
                .cache(
                    Cache(
                        File(applicationContext.cacheDir, "http_cache"),
                        100L * 1024 * 1024 // 100 MB
                    )
                )
                .build()
        }
    }
}