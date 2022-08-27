package com.example.android.mockapiresponse

import retrofit2.Response
import retrofit2.http.GET

interface PropertyAPI {

    @GET("/v3/4867fff9-fd81-48b4-b5a8-77484652e7e9")
    suspend fun getProperties(): Response<Property>
}