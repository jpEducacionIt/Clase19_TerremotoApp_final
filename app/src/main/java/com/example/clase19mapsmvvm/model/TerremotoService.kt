package com.example.clase19mapsmvvm.model

import android.util.Log
import com.example.clase19mapsmvvm.model.data.Terremoto
import com.example.clase19mapsmvvm.toTerremoto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TerremotoService() {
    private var listQuakes: MutableList<Terremoto> = mutableListOf()

    suspend fun getTerremoto(): MutableList<Terremoto> {

        val call = getRefrofit().create(ApiService::class.java).getAllQuakesByWeek()
        val response = call.body()

        if (call.isSuccessful) {
            listQuakes = (response?.features?.map { feature ->
                feature.toTerremoto()
            } ?: emptyList()) as MutableList<Terremoto>
        } else {
            Log.i("error", call.errorBody().toString())
        }
        return listQuakes
    }

    private fun getRefrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}