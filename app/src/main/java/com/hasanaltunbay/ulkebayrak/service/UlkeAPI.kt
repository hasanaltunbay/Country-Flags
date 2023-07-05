package com.hasanaltunbay.ulkebayrak.service

import com.hasanaltunbay.ulkebayrak.model.Ulke
import io.reactivex.Single
import retrofit2.http.GET

interface UlkeAPI {
    //https://raw.githubusercontent.com/
    // atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getUlkeler():Single<List<Ulke>>





}