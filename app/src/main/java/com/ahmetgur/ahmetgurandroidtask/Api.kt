package com.ahmetgur.ahmetgurandroidtask

import com.ahmetgur.CharacterModels.Simpson
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("?q=simpsons+characters&format=json")
    suspend fun getSimpsons(): Response<Simpson>

    @GET("?q=the+wire+characters&format=json")
    suspend fun getTheWires(): Response<Simpson>
}