package com.example.clase19mapsmvvm.model

import com.example.clase19mapsmvvm.Features
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(value = "significant_week.geojson")
    suspend fun getSignificantQuakesByWeek() : Response<Features>

    @GET(value = "significant_month.geojson")
    suspend fun getSignificantQuakesByMonth() : Response<Features>

    @GET(value = "all_week.geojson")
    suspend fun getAllQuakesByWeek() : Response<Features>
}