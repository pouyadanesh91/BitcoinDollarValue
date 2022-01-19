package com.danesh.bitcoindollarvalue.repository.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDataService {

    @GET("tobtc")
    fun getBitcoinValue(@Query("currency") currency: String,
    @Query("value")value: Int): Call<Double>
}