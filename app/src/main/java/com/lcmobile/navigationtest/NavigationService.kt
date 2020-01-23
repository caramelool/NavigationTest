package com.lcmobile.navigationtest

import android.util.Log
import com.lcmobile.navigation.Navigation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private var retrofit = Retrofit.Builder()
    .baseUrl("https://5db860f9177b350014ac79b6.mockapi.io/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private interface NavigationService {
    @GET("navigation")
    fun getNavigation(): Call<Navigation>
}

private var service: NavigationService = retrofit.create<NavigationService>(NavigationService::class.java)

fun getNavigation(block: (Navigation) -> Unit) {
    service.getNavigation().enqueue(object : Callback<Navigation> {
        override fun onResponse(call: Call<Navigation>, response: Response<Navigation>) {
            val data = response.body() ?: throw IllegalStateException()
            block(data)
        }

        override fun onFailure(call: Call<Navigation>, t: Throwable) {
            Log.w("NavigationService", t)
        }
    })
}