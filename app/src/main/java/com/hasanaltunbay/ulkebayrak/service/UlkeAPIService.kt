package com.hasanaltunbay.ulkebayrak.service

import com.hasanaltunbay.ulkebayrak.model.Ulke
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Base64

class UlkeAPIService {

    private val BASE_URL="https://raw.githubusercontent.com/"

    private val api=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(UlkeAPI::class.java)

    fun getData():Single<List<Ulke>>{
        return api.getUlkeler()
    }


}