package com.theapache64.leancorn.data.remote

import retrofit2.http.GET

interface ApiInterface {
    @GET("https://raw.githubusercontent.com/theapache64/top250/master/top250_min.json")
    suspend fun getTop250(): Top250Response
}